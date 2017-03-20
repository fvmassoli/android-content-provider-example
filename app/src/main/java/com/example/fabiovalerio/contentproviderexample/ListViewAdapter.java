package com.example.fabiovalerio.contentproviderexample;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.fabiovalerio.contentproviderexample.model.Constants.NAME;

public class ListViewAdapter extends CursorAdapter {

    @BindView(R.id.text)
    TextView mTextView;

    public ListViewAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ButterKnife.bind(this, view);
        mTextView.setText(cursor.getString(cursor.getColumnIndex(NAME)));
    }
}
