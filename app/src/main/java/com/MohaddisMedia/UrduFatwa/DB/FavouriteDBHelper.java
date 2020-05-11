package com.MohaddisMedia.UrduFatwa.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.MohaddisMedia.UrduFatwa.DBHelper;

public class FavouriteDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyDB";

    public FavouriteDBHelper( Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }
    static FavouriteDBHelper sInstance;
    public static synchronized FavouriteDBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new FavouriteDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbstring = "CREATE TABLE IF NOT EXISTS "+
                DBHelper.FavouriteEntry.TABLE_NAME +"("+
                DBHelper.FavouriteEntry.FATWA_ID+" INTEGER  PRIMARY KEY,"+ DBHelper.FavouriteEntry.FATWA_NO+" INTEGER,"+ DBHelper.FavouriteEntry.VIEW_COUNT+" INTEGER,"+ DBHelper.FavouriteEntry.QUESTION+" TEXT,"+ DBHelper.FavouriteEntry.ANSWER+" TEXT,"+ DBHelper.FavouriteEntry.CREATION_DATE+" TEXT)";
        Log.d("tryString",dbstring);
        db.execSQL(dbstring);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addFatwa(int fatwaId,int fatwano,String question,String answer,String creationDate,int viewCount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.FavouriteEntry.QUESTION, question);
        values.put(DBHelper.FavouriteEntry.ANSWER, answer);
        values.put(DBHelper.FavouriteEntry.FATWA_ID, fatwaId);
        values.put(DBHelper.FavouriteEntry.VIEW_COUNT, viewCount);
        values.put(DBHelper.FavouriteEntry.CREATION_DATE, creationDate);
        values.put(DBHelper.FavouriteEntry.FATWA_NO, fatwano);

        // Inserting Row
        long i = db.insert(DBHelper.FavouriteEntry.TABLE_NAME, null, values);
        Log.d("tryFatwa",i+"");
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // Deleting single contact
    public void deleteFatwa(int fatwa_Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBHelper.FavouriteEntry.TABLE_NAME, DBHelper.FavouriteEntry.FATWA_ID + " = ?",
                new String[] { String.valueOf(fatwa_Id) });
        db.close();
    }

    // code to get the single contact
    public boolean isFatwaFavourite(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DBHelper.FavouriteEntry.TABLE_NAME, new String[] {DBHelper.FavouriteEntry.FATWA_ID,
                }, DBHelper.FavouriteEntry.FATWA_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

        }else{
            return false;
        }

        Log.d("tryFatwa",cursor.getCount()+"");
        if(cursor.getCount()>0)
        {
            Log.d("tryFatwa",cursor.getInt(cursor.getColumnIndex(DBHelper.FavouriteEntry.FATWA_ID))+"");
            return  true;
        }

        return  false;
    }



}
