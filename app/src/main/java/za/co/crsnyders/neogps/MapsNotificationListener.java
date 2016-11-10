package za.co.crsnyders.neogps;

import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.os.*;
import android.content.*;
import android.util.*;
import android.widget.*;
import java.util.*;
import android.app.*;
import java.lang.reflect.*;
import android.text.*;
import java.text.*;

/**
 * Created by csnyders on 2016/06/30.
 * done by me
 */
public class MapsNotificationListener  extends NotificationListenerService
{
	
    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {
        if(sbn.getPackageName().equals("com.google.android.apps.maps")){
			System.out.println(this.getText(sbn.getNotification()));
		}
		
        super.onNotificationPosted(sbn, rankingMap);
    }

    @Override
    public void onNotificationRankingUpdate(RankingMap rankingMap) {
        System.out.println("updated");
		super.onNotificationRankingUpdate(rankingMap);
    }
	
	public List<String> getText(Notification notification)
	{
		// We have to extract the information from the view
		RemoteViews        views = notification.bigContentView;
		if (views == null) views = notification.contentView;
		if (views == null) return null;

		// Use reflection to examine the m_actions member of the given RemoteViews object.
		// It's not pretty, but it works.
		List<String> text = new ArrayList<String>();
		try
		{
			Field field = views.getClass().getDeclaredField("mActions");
			field.setAccessible(true);

			@SuppressWarnings("unchecked")
				ArrayList<Parcelable> actions = (ArrayList<Parcelable>) field.get(views);

			// Find the setText() and setTime() reflection actions
			for (Parcelable p : actions)
			{
				Parcel parcel = Parcel.obtain();
				p.writeToParcel(parcel, 0);
				parcel.setDataPosition(0);

				// The tag tells which type of action it is (2 is ReflectionAction, from the source)
				int tag = parcel.readInt();
				if (tag != 2) continue;

				// View ID
				parcel.readInt();

				String methodName = parcel.readString();
				if (methodName == null) continue;

				// Save strings
				else if (methodName.equals("setText"))
				{
					// Parameter type (10 = Character Sequence)
					parcel.readInt();

					// Store the actual string
					String t = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel).toString().trim();
					text.add(t);
				}

				// Save times. Comment this section out if the notification time isn't important
				else if (methodName.equals("setTime"))
				{
					// Parameter type (5 = Long)
					parcel.readInt();

					String t = new SimpleDateFormat("h:mm a").format(new Date(parcel.readLong()));
					text.add(t);
				}

				parcel.recycle();
			}
		}

		// It's not usually good style to do this, but then again, neither is the use of reflection...
		catch (Exception e)
		{
			Log.e("NotificationClassifier", e.toString());
		}

		return text;
	}
	
	}
