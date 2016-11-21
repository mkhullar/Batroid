package mcgroup10.com.batroid;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Created by abhishekzambre on 19/11/16.
 */

public class Settings extends AppCompatActivity {

    //Declaration of variables used for Database access
    DatabaseHelper myDB;
    String table_name = "geofence_records";
    boolean dbCreated = false;
    String dbFilePath;

    int i=0;
    ListView lv;
    Model[] modelItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_settings);


        myDB = new DatabaseHelper(this);

        lv = (ListView) findViewById(R.id.listView1);
        int profile_counts = myDB.getRowCount(table_name);

        modelItems = new Model[profile_counts];
        Cursor res = myDB.getAllData(table_name);
        while (res.moveToNext()) {
            //values[i] = res.getLong(1);
            modelItems[i] = new Model(res.getString(0), 0);
            i++;
        }
        res.close();

        CustomAdapter adapter = new CustomAdapter(this, modelItems);
        lv.setAdapter(adapter);

        //getValuesFromDB();
    }

    public void getValuesFromDB(){
        Cursor res = myDB.getAllData(table_name);
        while (res.moveToNext()) {
            //values[i] = res.getLong(1);
        }
        res.close();

    }


}
