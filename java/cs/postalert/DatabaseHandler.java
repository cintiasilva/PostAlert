package cs.postalert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper{
    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "myData";
    // Contacts table name
    private static final String TABLE_DATA = "records";

    //Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_MESSAGE = "_message";
//    private static final String KEY_TIME = "_time";
    //   private static final String KEY_DATE = "_date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MESSAGE + " TEXT,)";
//                + KEY_TIME + " TEXT," + KEY_DATE + " TEXT" + ")";
        db.execSQL(CREATE_DATA_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        // Create tables again
        onCreate(db);
    }

    // Adding new content
    void addData(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_MESSAGE, data.getMessage()); // Message
//        values.put(KEY_TIME, data.getTime()); // Contact Phone
//        values.put(KEY_DATE, data.getDate());

        // Inserting Row
        db.insert(TABLE_DATA, null, values);
        db.close(); // Closing database connection
    }

    // Getting All conten
    public List<Data> getAllData() {
        List<Data> dataList = new ArrayList<Data>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Data data = new Data();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setMessage(cursor.getString(1));
                //               data.setTime(cursor.getString(2));
//                data.setDate(cursor.getString(3));
                // Adding content to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }
        // return list content
        return dataList;
    }
    //delete data from database
    public void deleteData(int id) {
        String idd = String.valueOf(id);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATA, KEY_ID + " = ?",
                new String[]{idd});
        db.close();
    }
}