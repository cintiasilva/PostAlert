package cs.postalert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver{

     public void onReceive(Context context, Intent intent) {
        //get the SMS message passed in
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String messages = "";
        String number = "+353838467192";

        if (bundle != null){
            //retrieve the SMS message received
            Object[] smsExtra = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[smsExtra.length];

            for (int i=0; i<msgs.length; i++){
                SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);

                //take out content from sms
                String body = sms.getMessageBody().toString();
                String address = sms.getOriginatingAddress();
                messages = body;

                putSmsToDatabase(messages,context);
                //—display the new SMS message—
                Toast.makeText(context, messages, Toast.LENGTH_LONG).show();
            }
        }
     }
     private void putSmsToDatabase(String sms, final Context context ){
        DatabaseHandler db = new DatabaseHandler(context);
        db.addData(new Data(sms));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.listviewUpdater(context);
            }
        },100);
    }
}
