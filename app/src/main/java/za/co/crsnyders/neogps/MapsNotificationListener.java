package za.co.crsnyders.neogps;

import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.os.*;
import android.content.*;
import android.util.*;
import android.widget.*;

/**
 * Created by csnyders on 2016/06/30.
 * done by me
 */
public class MapsNotificationListener  extends NotificationListenerService
{

	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		System.out.println("listener service started");
	}

	
	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO: Implement this method
		return super.onBind(intent);
	}
	
	
    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {
        
		RemoteViews extras = sbn.getNotification().headsUpContentView;
		for(String key : extras.){
			System.out.println();
			Log.d("noti",key+" : "+extras.get( key));
		}
		String text = extras.getCharSequence("android.text").toString();
		System.out.println(text);
		
        super.onNotificationPosted(sbn, rankingMap);
    }

    @Override
    public void onNotificationRankingUpdate(RankingMap rankingMap) {
        System.out.println("updated");
		super.onNotificationRankingUpdate(rankingMap);
    }

	@Override
	public void onNotificationRemoved(StatusBarNotification sbn)
	{
	    System.out.println("removed");
		// TODO: Implement this method
		super.onNotificationRemoved(sbn);
	}
	
	
}
