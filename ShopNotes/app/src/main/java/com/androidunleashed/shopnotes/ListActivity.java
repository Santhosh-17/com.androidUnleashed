package com.androidunleashed.shopnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.androidunleashed.shopnotes.Adapter.RecyclerViewAdapter;
import com.androidunleashed.shopnotes.Data.DataBaseHandler;
import com.androidunleashed.shopnotes.Model.Item;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> items;
    private DataBaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);

        dataBaseHandler = new DataBaseHandler(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = dataBaseHandler.getAllItems();
        List<Item> i = show(cursor);
//
//        for(Item item : i){
//            Log.d("Debug", "onCreate: Id: "+item.getId());
//            Log.d("Debug", "onCreate: Item: "+item.getItemName());
//            Log.d("Debug", "onCreate: Qty: "+item.getItemQty());
//            Log.d("Debug", "onCreate: Date: "+item.getDate());
//        }

        recyclerViewAdapter = new RecyclerViewAdapter(this,i);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

    }

    private List<Item> show(Cursor cursor) {

        List<Item> allItems = new ArrayList<>();

        if(cursor.getCount() == 0){
            Log.d("Debug", "show: Error");
            return allItems;

        }else {

//            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext()){
//                stringBuffer.append("ID: "+cursor.getString(0)+"\n");
//                stringBuffer.append("Item Name: "+cursor.getString(1)+"\n");
//                stringBuffer.append("Item Qty: "+cursor.getString(2)+"\n");

//                        stringBuffer.append("Date: "+cursor.getString(5)+"\n\n");

//                DateFormat dateFormat = DateFormat.getDateInstance();
//                String formattedDate = dateFormat.format(
//                        new Date(cursor.getLong(3)).getTime());
//
//                stringBuffer.append("Date: "+formattedDate+"\n\n");
                Item item = new Item();

                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setItemName(cursor.getString(1));
                item.setItemQty(Integer.parseInt(cursor.getString(2)));
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(
                        new Date(cursor.getLong(3)).getTime());
                item.setDate(formattedDate);

                allItems.add(item);

            }
//            String temp = stringBuffer.toString();
//            Log.d("Debug", "show: "+temp);
        }

        return allItems;

    }
}
