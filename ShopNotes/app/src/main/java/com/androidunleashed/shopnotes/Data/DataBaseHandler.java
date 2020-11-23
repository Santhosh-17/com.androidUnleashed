package com.androidunleashed.shopnotes.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.androidunleashed.shopnotes.Model.Item;
import com.androidunleashed.shopnotes.Util.Constants;

public class DataBaseHandler extends SQLiteOpenHelper {

    public DataBaseHandler( Context context) {
        super(context, Constants.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String Create = "CREATE TABLE "+ Constants.TABLE_NAME + " ( "
                + Constants.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.NAME+" TEXT, "
                + Constants.QTY +" INTEGER, "
                + Constants.DATE + " LONG ); ";

        db.execSQL(Create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String up = "DROP TABLE IF EXISTS "+Constants.TABLE_NAME;

        db.execSQL(up);

        onCreate(db);

    }


    public long insert(Item item){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME,item.getItemName());
        contentValues.put(Constants.QTY,item.getItemQty());
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
                        Constants.DATE}, null ,null,null,null,Constants.DATE+" DESC");
        return result;
    }


    public int updateItem(Item item) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME,item.getItemName());
        contentValues.put(Constants.QTY,item.getItemQty());
        contentValues.put(Constants.DATE,java.lang.System.currentTimeMillis());



        return db.update(Constants.TABLE_NAME, contentValues,
                Constants.ID + "=?",
                new String[]{String.valueOf(item.getId())});


    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME,
                Constants.ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();

    }

    public int getItemsCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();

    }


}

