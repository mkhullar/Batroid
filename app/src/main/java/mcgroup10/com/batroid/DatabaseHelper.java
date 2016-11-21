package mcgroup10.com.batroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Batroid.db";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        //Table_Name="Records";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    //Insert geofences in table
    public boolean insertData(String t_name, String name, String latitude, String longitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("LATITUDE", latitude);
        contentValues.put("LONGITUDE", longitude);
        long result = db.insert(t_name, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //Method to create table in database
    public void createTable (String t_name){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DROP TABLE IF EXISTS " + t_name);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + t_name + " ( NAME VARCHAR2(10), LATITUDE VARCHAR2(10), LONGITUDE VARCHAR2(10))");
    }

    //Method to retrieve geofences
    public Cursor getAllData (String t_name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + t_name, null);
        return res;
    }

    public int getRowCount(String t_name) {
        String countQuery = "SELECT  * FROM " + t_name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    //Method to retrieve table names from downloaded db file
    /*public Cursor getLastTable (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res_last = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' and name != 'android_metadata' ORDER BY name DESC LIMIT 1", null);
        return res_last;
    }*/

}
