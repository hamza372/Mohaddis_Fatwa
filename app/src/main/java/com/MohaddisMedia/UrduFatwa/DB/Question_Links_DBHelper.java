package com.MohaddisMedia.UrduFatwa.DB;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Question_Links_DBHelper extends SQLiteAssetHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UrduFatwa";

    public Question_Links_DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


}