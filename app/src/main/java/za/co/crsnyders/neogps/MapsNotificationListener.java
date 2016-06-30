package za.co.crsnyders.neogps;

import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/**
 * Created by csnyders on 2016/06/30.
 * done by me
 */
public class MapsNotificationListener  extends NotificationListenerService{
    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {
        System.out.println(sbn);
        super.onNotificationPosted(sbn, rankingMap);
    }

    @Override
    public void onNotificationRankingUpdate(RankingMap rankingMap) {
        super.onNotificationRankingUpdate(rankingMap);
    }
}
