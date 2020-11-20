package com.androidunleashed.shelper.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.androidunleashed.shelper.Util.Constants;
import com.androidunleashed.shelper.model.Item;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context context;

    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_SHOP = "CREATE TABLE "+Constants.TABLE_NAME+" ( "
                + Constants.KEY_ID + "INTEGER PRIMARY KEY , "
                + Constants.KEY_ITEM + "TEXT , "
                + Constants.KEY_QTY + "INTEGER , "
                + Constants.KEY_SIZE + "INTEGER , "
                + Constants.KEY_COLOR + "TEXT , "
                + Constants.KEY_DATE + "LONG ); ";

        db.execSQL(CREATE_SHOP);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String upgrade = "DROP TABLE IF EXISTS "+Constants.TABLE_NAME;

        db.execSQL(upgrade);

        onCreate(db);

    }

    public void addItem(Item item){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.KEY_ITEM,item.getiName());
        contentValues.put(Constants.KEY_QTY,item.getiQuantity());
        contentValues.put(Constants.KEY_SIZE,item.getiSize());
        contentValues.put(Constants.KEY_COLOR,item.getiSize());
        contentValues.put(Constants.KEY_DATE,java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME,null,contentValues);


        Log.d("debug", "Item Added ");

    }

    public Item getItem(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                Constants.KEY_ITEM,
                Constants.KEY_QTY,
                Constants.KEY_SIZE,
                Constants.KEY_COLOR,
                Constants.KEY_DATE},
                Constants.KEY_ID+"=?",
                new String[]{String.valueOf(id)},null,null,null);

        Item item = new Item();

        if (cursor!=null){
            cursor.moveToFirst();

            item.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
            item.setiName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM)));
            item.setiQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY)));
            item.setiSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_SIZE)));
            item.setiColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
//            item.setItemAddedDate(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE)));

            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(
                    new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE)))
                    .getTime()
            );

            item.setItemAddedDate(formattedDate);

        }

        return item;

    }

    public List<Item> getAllItem(){

        SQLiteDatabase db = this.getReadableDatabase();

        List<Item> allList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                        Constants.KEY_ITEM,
                        Constants.KEY_QTY,
                        Constants.KEY_SIZE,
                        Constants.KEY_COLOR,
                        Constants.KEY_DATE},
                null,null,null,null,
                Constants.KEY_DATE+" DESC");

        if (cursor.moveToFirst()){

            do {

                Item item = new Item();

                item.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
                item.setiName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM)));
                item.setiQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY)));
                item.setiSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_SIZE)));
                item.setiColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
//            item.setItemAddedDate(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(
                        new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE)))
                                .getTime()
                );

                item.setItemAddedDate(formattedDate);

                allList.add(item);

            }while (cursor.moveToNext());
        }

        return allList;
    }

    public int updateItem(Item item){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.KEY_ITEM,item.getiName());
        contentValues.put(Constants.KEY_QTY,item.getiQuantity());
        contentValues.put(Constants.KEY_SIZE,item.getiSize());
        contentValues.put(Constants.KEY_COLOR,item.getiSize());
        contentValues.put(Constants.KEY_DATE,java.lang.System.currentTimeMillis());

        return db.update(Constants.TABLE_NAME,contentValues,
                Constants.KEY_ID+ " =?",
                new String[]{ String.valueOf(item.getId())});

    }

    public void deleteItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.TABLE_NAME,
                Constants.KEY_ID+"=?",
                new String[]{String.valueOf(id)});

        db.close();
    }

    public int getItemsCount(){
        SQLiteDatabase db = this.getWritableDatabase();

        String cQuery = "SELECT * FROM "+ Constants.TABLE_NAME;
        Cursor cursor = db.rawQuery(cQuery,null);

        return cursor.getCount();

    }


}
