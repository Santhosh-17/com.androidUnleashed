package com.androidunleashed.contactmanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.androidunleashed.contactmanager.R;
import com.androidunleashed.contactmanager.util.Util;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // to create a table
        // CREATE TABLE CONTACTS (ID INT,NAME TEXT,PHONENO TEXT);
        
        // ; HERE
        
        String CREATE_TABLE = "CREATE TABLE "+Util.DATABASE_NAME+" ( "+ Util.KEY_ID+" INTEGER PRIMARY KEY, "+Util.KEY_NAME+" TEXT, "+Util.KEY_PHONENO +" TEXT ); " ;
        db.execSQL(CREATE_TABLE);
        

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //droping table

        String DROP_TABLE = String.valueOf(R.string.drop_table);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});

        onCreate(db);

    }
}
