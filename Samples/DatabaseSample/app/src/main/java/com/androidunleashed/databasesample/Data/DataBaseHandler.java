package com.androidunleashed.databasesample.Data;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.androidunleashed.databasesample.Model.Item;
import com.androidunleashed.databasesample.Util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {


    public DataBaseHandler( Context context) {
        super(context, Constants.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create = "CREATE TABLE "+ Constants.TABLE_NAME + " ( "+ Constants.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.NAME+" TEXT, "
                + Constants.QTY +" INTEGER, "
                + Constants.COLOR +" TEXT, "
                + Constants.SIZE + " INTEGER, "
                + Constants.DATE + " LONG ); ";

        db.execSQL(Create);


    }

    public long insert(Item item){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME,item.getItemName());
        contentValues.put(Constants.QTY,item.getItemQty());
        contentValues.put(Constants.COLOR,item.getItemColor());
        contentValues.put(Constants.SIZE,item.getItemSize());
        contentValues.put(Constants.DATE,java.lang.System.currentTimeMillis());

        long r = db.insert(Constants.TABLE_NAME,null,contentValues);

        return r;
    }


    public Cursor getAllItems(){
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor result = db.rawQuery("SELECT * FROM "+Constants.TABLE_NAME,null);
        Cursor result = db.query(Constants.TABLE_NAME,
                new String[]{Constants.ID,
                        Constants.NAME,
                        Constants.QTY,
                        Constants.SIZE,
                        Constants.COLOR,
                        Constants.DATE}, null ,null,null,null,Constants.DATE+" DESC");
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String up = "DROP TABLE IF EXISTS "+Constants.TABLE_NAME;

        db.execSQL(up);

        onCreate(db);
    }
}
