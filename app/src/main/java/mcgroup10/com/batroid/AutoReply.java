package mcgroup10.com.batroid;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by murali on 13/11/16.
 */

public class AutoReply extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS_AUTO = 124;
    private static final String TAG = "AutoReply";

    StringBuffer sb = new StringBuffer();
    TextView textDetail;

    //@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoreply);
        textDetail = (TextView) findViewById(R.id.textView1);
        //checkReceivePermission();
        startYourWork();
    }


    /*@RequiresApi(api = Build.VERSION_CODES.M)
    private void checkReceivePermission() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.SEND_SMS))
            permissionsNeeded.add("GPS");
        if (!addPermission(permissionsList, Manifest.permission.RECEIVE_SMS))
            permissionsNeeded.add("Read Contacts");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
            permissionsNeeded.add("Write Contacts");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS_AUTO);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS_AUTO);
            return;
        }
        startYourWork();
    }*/

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(AutoReply.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

   /* @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }*/

    protected void startYourWork() {
        sb.append("Auto-Reply mode is on!");

        textDetail.setText(sb);
        //MainActivity.methodtocall();
    }

    public static void methodtocall(String contactNumber){

        Log.i(TAG, "****************************");
        Log.d(TAG, "number: " + contactNumber);
        Log.i(TAG, "****************************");

        String smsMessage = "AutoReply: Thanks for your message. I am in a meeting. I will text you later!";



        try {
            SmsManager smsManager = SmsManager.getDefault();
            //smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);
            //Toast.makeText(getApplicationContext(), "SMS sent.",Toast.LENGTH_LONG).show();
            //for (int i = 0; i < contactNumber.size(); i++) {
            smsManager.sendTextMessage(contactNumber, null, smsMessage, null, null);
            //Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
            Log.i(TAG, "****************************");
            Log.d(TAG, "Autoreply sent to:" + contactNumber);
            Log.i(TAG, "****************************");
            //}
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "Sending SMS failed.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }


}
