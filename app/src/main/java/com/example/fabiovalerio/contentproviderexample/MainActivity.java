package com.example.fabiovalerio.contentproviderexample;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.provider.BaseColumns._ID;
import static com.example.fabiovalerio.contentproviderexample.model.Constants.NAME;
import static com.example.fabiovalerio.contentproviderexample.model.Model.Contract.TABLE_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    @BindView(R.id.list)
    ListView mListView;
    @BindView(R.id.edit)
    EditText mEditText;

    ListViewAdapter mListViewAdapter;

    private final int LOADER_ID = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CursorAdapter cursorAdapter = (CursorAdapter) mListView.getAdapter();
                Cursor cursor = cursorAdapter.getCursor();
                cursor.moveToPosition(position);
                String txt = cursor.getString(cursor.getColumnIndex(NAME));
                Uri uri = TABLE_URI.buildUpon().appendPath(txt).build();
                getContentResolver().delete(uri, null, null);
                return true;
            }
        });
    }

    @OnClick(R.id.btn)
    public void saveButtonClicked(View view){
        String text = mEditText.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, text);
        getContentResolver().insert(TABLE_URI, contentValues);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {_ID,
                NAME};
        return new CursorLoader(this,
                TABLE_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null)
            mListViewAdapter = new ListViewAdapter(this, data);
        mListView.setAdapter(mListViewAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(mListViewAdapter != null)
            mListViewAdapter.swapCursor(null);
    }
}
