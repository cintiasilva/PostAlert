package cs.postalert;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Fyah on 5/18/2017.
 */

public class Notification extends AppCompatActivity {

    //NOTIFICATION
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
///METHOD THAT NEEDS TO BE CALLED
    public void sendNotification(View view){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Post Alert")
                .setContentText("You've got mail")
                .setSound(defaultSoundUri);


        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(pendingIntent);
        int notificationId = 101;
        NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notifyMgr.notify(notificationId, builder.build());

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(
                        android.R.drawable.sym_action_chat,
                        "Open", pendingIntent).build();
        builder.addAction(action);
    }
}
