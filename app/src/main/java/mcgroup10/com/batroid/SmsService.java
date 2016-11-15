package mcgroup10.com.batroid;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SmsService extends Service {

    public SmsService() {
    }

    Context mContext;
    public SmsService(Context mContext) {
        this.mContext = mContext;
    }

    public void sendSMS(String phoneNumber) {
        String message = "I am in a Do Not Disturb Zone. Will call you back as soon as possible. Thanks";
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(mContext, "Auto-Reply Sent!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(mContext, "Auto-Reply failed, please try again later!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}