package com.example.fabiovalerio.contentproviderexample.model;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.fabiovalerio.contentproviderexample.model.Constants.DROP_TABLE_IF_EXISTS;
import static com.example.fabiovalerio.contentproviderexample.model.Constants.TABLE_NAME;
import static com.example.fabiovalerio.contentproviderexample.model.Model.Contract.TABLE_CREATION_QUERY;

public class DbHelper extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "test_db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATION_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_NAME);
        onCreate(db);
    }
}
