package com.example.fabiovalerio.contentproviderexample.model;


import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.fabiovalerio.contentproviderexample.model.Constants.NAME;
import static com.example.fabiovalerio.contentproviderexample.model.Constants.TABLE;
import static com.example.fabiovalerio.contentproviderexample.model.Constants.TABLE_NAME;
import static com.example.fabiovalerio.contentproviderexample.model.Constants.TEXT_DATA_TYPE;

public class Model {

    public static final String CONTENT_AUTHORITY = "com.example.fabiovalerio.contentproviderexample.model_provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class Contract implements BaseColumns {

        public static final Uri TABLE_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE).build();

        public static final String TABLE_CREATION_QUERY = "CREATE TABLE " + TABLE_NAME +
                " (" +
                Contract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME         + TEXT_DATA_TYPE +
                ")";
    }

}
