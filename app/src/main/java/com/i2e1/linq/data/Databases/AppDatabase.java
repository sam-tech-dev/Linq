package com.i2e1.linq.data.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.i2e1.linq.Utils.UtilFunctions;
import com.i2e1.linq.data.Pojo.Result;

import java.util.List;

public class AppDatabase extends SQLiteOpenHelper {


    public static final int database_version = 1;
    public static AppDatabase dbInstance;
    Context context;

//    Constructor
    public AppDatabase(Context context) {
        super(context, "linqApp.db", null, database_version);
        this.context = context;
    }

    /**
     * function to get singleton of database
     * @param context Context to create database object
     * @return AppDatabase Object for database operations
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new AppDatabase(context.getApplicationContext());
        }
        return dbInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql_query_persons = "create table tab_persons(id integer primary key autoincrement," +
                "title varchar(10)," +
                "firstName varchar(50)," +
                "lastName varchar(50)," +
                "email varchar(100)," +
                "dobDate varchar(10)," +
                "age int," +
                "phoneNumber varchar(15)," +
                "pictureMediumUrl text," +
                "pictureLargeUrl text," +
                "pictureThumbnailUrl text," +
                "pictureImageData MEDIUMTEXT);";

        /**
         * creating database table
         */
        sqLiteDatabase.execSQL(sql_query_persons);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    /**
     * function to insert list of persons which is fetch from server
     * @param listOfPersons list of persons
     */
    public void insertListOfPersons(List<Result> listOfPersons) {

        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();

        for (int i = 0; i < listOfPersons.size(); i++) {
            Result person = listOfPersons.get(i);

            ContentValues cv = new ContentValues();
            cv.put("title", person.getName().getTitle());
            cv.put("firstName", person.getName().getFirst());
            cv.put("lastName", person.getName().getLast());
            cv.put("email", person.getEmail());
            cv.put("dobDate",person.getDob().getDate());
            cv.put("age", person.getDob().getAge());
            cv.put("phoneNumber", UtilFunctions.getPhoneNumberFormat(person.getPhone()));
            cv.put("pictureMediumUrl", person.getPicture().getMedium());
            cv.put("pictureLargeUrl", person.getPicture().getLarge());
            cv.put("pictureThumbnailUrl", person.getPicture().getThumbnail());
            cv.put("pictureImageData", "");
            try {
                writableDatabase.insertOrThrow("tab_persons", null, cv);
            } catch (SQLiteException sqlitex) {
                Log.d("az", "exception in persons insertion " + sqlitex.toString());
            }
        }
        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();
    }


    /**
     * Function to get list of person in cursor
     * @return database Cursor
     */
    public Cursor getPersonList() {
        Cursor cr = null;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            String sql_query = "select * from tab_persons;";
            cr = writableDatabase.rawQuery(sql_query, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.d("az", " exception in fetching persons" + e.toString());
        }
        return cr;
    }


    /**
     * function to  check whether  persons data is in local databse or not ?
     * @return Boolean true- database is empty
     */
    public Boolean isTableEmpty() {
        Cursor cr = null;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            String sql_query = "select * from tab_persons;";
            cr = writableDatabase.rawQuery(sql_query, null);
            if (cr.getCount() > 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            return  true;
        } catch (NullPointerException e){
            e.printStackTrace();
            return  true;
        }
    }


    /**
     * function to update profile pic data  string in database after downloading them
     * @param mediumImageUrl image url to download
     * @param imageData image data in string format
     */
    public void updateProfileImages(String mediumImageUrl, String imageData) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            String sql_query = "UPDATE tab_persons SET pictureImageData = '" + imageData + "'  where pictureMediumUrl ='" + mediumImageUrl + "';";
            writableDatabase.execSQL(sql_query);
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.d("az task ", "exception update profile image :" + e.toString());
        }
    }


}
