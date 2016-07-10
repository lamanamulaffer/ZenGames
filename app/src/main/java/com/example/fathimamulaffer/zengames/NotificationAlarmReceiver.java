package com.example.fathimamulaffer.zengames;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Fires the notification
 */
public class NotificationAlarmReceiver extends BroadcastReceiver {
    SharedPreference sp;
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context,"noti",Toast.LENGTH_SHORT).show();
        sp = new SharedPreference();
        //once experiment is over - there's no need to send notifications
        if (!(sp.getDayCount(context) > 21)){
            Intent resultIntent = new Intent(context,MainActivity.class);
            resultIntent.putExtra("Origin","NotificationAlarmReceiver"); //to track from where the main activity was called
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pi = PendingIntent.getActivity(context, 100, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder nbuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_profile)
                            .setContentTitle("Zen Games Notification")
                            .setContentText("Please remember to play the games today");
            nbuilder.setContentIntent(pi);
            nbuilder.setAutoCancel(true);

            NotificationManager manager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);

            manager.notify(1,nbuilder.build());
        }

    }
}
