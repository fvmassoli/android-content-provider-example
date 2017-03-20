package com.example.fabiovalerio.contentproviderexample.model;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.example.fabiovalerio.contentproviderexample.model.Constants.NAME;
import static com.example.fabiovalerio.contentproviderexample.model.Constants.TABLE;
import static com.example.fabiovalerio.contentproviderexample.model.Constants.TABLE_NAME;
import static com.example.fabiovalerio.contentproviderexample.model.Constants.TABLE_ROW;
import static com.example.fabiovalerio.contentproviderexample.model.Constants.TABLE_SPECIFIC_ROW_URI_MATCHER_ID;
import static com.example.fabiovalerio.contentproviderexample.model.Constants.TABLE_URI_MATCHER_ID;
import static com.example.fabiovalerio.contentproviderexample.model.Model.Contract.TABLE_URI;

public class Provider extends ContentProvider {

    private static UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher(){

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Model.CONTENT_AUTHORITY;
        uriMatcher.addURI(authority, TABLE, TABLE_URI_MATCHER_ID);
        uriMatcher.addURI(authority, TABLE_ROW, TABLE_SPECIFIC_ROW_URI_MATCHER_ID);
        return uriMatcher;
    }

    private DbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor = null;
        SQLiteDatabase sqLiteDatabase = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match){
            case TABLE_URI_MATCHER_ID:
                cursor = sqLiteDatabase.query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder,
                        null
                );
                break;
            case TABLE_SPECIFIC_ROW_URI_MATCHER_ID:

                break;
            default:
                throw new IllegalArgumentException("Uri is not valid");
        }

        if(cursor != null)
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        Uri returnUri = null;
        SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match){
            case TABLE_URI_MATCHER_ID:
                sqLiteDatabase.insert(TABLE_NAME,
                        null,
                        values);
                returnUri = TABLE_URI;
                Log.d("dqwd", "inserted");
                break;
            case TABLE_SPECIFIC_ROW_URI_MATCHER_ID:

                break;
            default:
                throw new IllegalArgumentException("Uri is not valid");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsDelete = 0;
        switch (match){
            case TABLE_URI_MATCHER_ID:

                break;
            case TABLE_SPECIFIC_ROW_URI_MATCHER_ID:

                String txt = uri.getLastPathSegment();

                String select = NAME + "=?";
                String[] selectArgs = new String[]{txt};

                rowsDelete = sqLiteDatabase.delete(
                        TABLE_NAME,
                        select,
                        selectArgs
                );
                break;
            default:
                throw new IllegalArgumentException("Uri is not valid");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDelete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        int match = sUriMatcher.match(uri);
        switch (match){
            case TABLE_URI_MATCHER_ID:

                break;
            case TABLE_SPECIFIC_ROW_URI_MATCHER_ID:

                break;
            default:
                throw new IllegalArgumentException("Uri is not valid");
        }

        return 0;
    }
}
