package mcgroup10.com.batroid;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by murali on 13/11/16.
 */

public class Birthdays extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private static final String TAG = "Birthdays";

    Button sendBtn;
    TextView textDetail;
    //EditText txtphoneNo;
    EditText txtphoneNo;
    EditText txtMessage;
    String phoneNo;
    String message;

    ArrayList<String> contactName = new ArrayList<String>();
    ArrayList<String> contactNumber = new ArrayList<String>();
    StringBuffer sb = new StringBuffer();

    //@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthdays);
        textDetail = (TextView) findViewById(R.id.textView1);
        sendBtn = (Button) findViewById(R.id.btnSendSMS);
        //checkReceivePermission();
        startYourWork();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMSMessage();
            }
        });

    }

    /*
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void checkReceivePermission() {
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.RECEIVE_SMS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.RECEIVE_SMS},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        startYourWork();
    }
    */

   /* @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkReceivePermission() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.SEND_SMS))
            permissionsNeeded.add("GPS");
        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))
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
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }
        startYourWork();
    }

    /*private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Birthdays.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    startYourWork();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "RECEIVE_SMS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
*/

    private Cursor getContactsBirthdays() {
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Event.CONTACT_ID,
                ContactsContract.CommonDataKinds.Event.START_DATE
        };
        String where =
                ContactsContract.Data.MIMETYPE + "= ? AND " +
                        ContactsContract.CommonDataKinds.Event.TYPE + "=" +
                        ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY;

        String[] selectionArgs = new String[]{
                ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
        };
        String sortOrder = null;
        ContentResolver cr = getContentResolver();
        return cr.query(uri, projection, where, selectionArgs, sortOrder);
        //return managedQuery
    }

    protected void startYourWork() {
        //sb.append("successful");

        //textDetail.setText(sb);
        Cursor cursor = getContactsBirthdays();
        int bDayColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE);
        int nameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        String number;
        while (cursor.moveToNext()) {
            String bDay = cursor.getString(bDayColumn);
            String name = cursor.getString(nameColumn);


            Date date = new Date();
            String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            //Log.d(TAG, "today's date: " + modifiedDate);

            if (modifiedDate.equals(bDay)) {
                number = getPhoneNumber(this, name);
                Log.d(TAG, "Name: " + name);
                Log.d(TAG, "Birthday: " + bDay);
                Log.d(TAG, "Number: " + number);
                Log.i(TAG, "........................");
                contactName.add(name);
                contactNumber.add(number);
            }
        }
        Log.i(TAG, "*************************");
        Log.d(TAG, "contactName: " + contactName);
        Log.d(TAG, "contactNumber: " + contactNumber);
        Log.i(TAG, "*************************");
        sb.append("~~~Today's Birthday~~~ \n");
        for (int i = 0; i < contactName.size(); i++) {
            sb.append("\n" + contactName.get(i) + "\n" + contactNumber.get(i) + "\n");
        }
        textDetail.setText(sb);



        //String toPhoneNumber = toPhoneNumberET.getText().toString();
        //String smsMessage = smsMessageET.getText().toString();
        /*
        String toPhoneNumber = "4804170094";
        String smsMessage = "Happy Birthday!";
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        */
    }

    public String getPhoneNumber(Context context, String name) {
        String ret = null;
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like'%" + name + "%'";
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, selection, null, null);
        if (c.moveToFirst()) {
            ret = c.getString(0);
        }
        c.close();
        if (ret == null)
            ret = "Unsaved";
        return ret;
    }

    protected void sendSMSMessage() {
        //String toPhoneNumber = "4804170094";
        String smsMessage = "Happy Birthday!";


        try {
            SmsManager smsManager = SmsManager.getDefault();
            //smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);
            //Toast.makeText(getApplicationContext(), "SMS sent.",Toast.LENGTH_LONG).show();
            for (int i = 0; i < contactNumber.size(); i++) {
                smsManager.sendTextMessage(contactNumber.get(i), null, smsMessage, null, null);
                Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }
}
