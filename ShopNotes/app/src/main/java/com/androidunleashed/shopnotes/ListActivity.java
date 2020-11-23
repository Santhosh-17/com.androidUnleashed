package com.androidunleashed.shopnotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidunleashed.shopnotes.Adapter.RecyclerViewAdapter;
import com.androidunleashed.shopnotes.Data.DataBaseHandler;
import com.androidunleashed.shopnotes.Model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> items;
    private DataBaseHandler dataBaseHandler;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button SaveButton;
    private TextInputEditText ItemName, ItemQty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_note);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("  Shop Notes");

        recyclerView = findViewById(R.id.recyclerView);

        dataBaseHandler = new DataBaseHandler(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = dataBaseHandler.getAllItems();
        List<Item> i = show(cursor);

//        for(Item item : i){
//            Log.d("Debug", "onCreate: Id: "+item.getId());
//            Log.d("Debug", "onCreate: Item: "+item.getItemName());
//            Log.d("Debug", "onCreate: Qty: "+item.getItemQty());
//            Log.d("Debug", "onCreate: Date: "+item.getDate());
//        }

        recyclerViewAdapter = new RecyclerViewAdapter(this,i);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        FloatingActionButton fab1 = findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpDialogue(view);
            }
        });

    }


    private void createPopUpDialogue(final View v1) {

        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);

        ItemName = view.findViewById(R.id.itemName);
        ItemQty = view.findViewById(R.id.itemQuantity);

        SaveButton = view.findViewById(R.id.saveButton);


        builder.setView(view);
        dialog = builder.create(); //creating dialogue object
        dialog.show();

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = ItemName.getText().toString();
                String qty = ItemQty.getText().toString();

                if( !name.isEmpty() && !qty.isEmpty() ){

                    long r = saveItem(name,qty);
                    if(r == -1){

                        dialog.dismiss();
                        Snackbar.make(v1, "Item not Saved ", Snackbar.LENGTH_LONG)
                                .show();
                    }else{

                        dialog.dismiss();

                        Toast.makeText(ListActivity.this,"Item Added",Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(ListActivity.this,ListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }

                }else{
                    Snackbar.make(v, "Empty Fields are not allowed!", Snackbar.LENGTH_SHORT)
                            .show();
                }

            }
        });


    }

    private long saveItem(String n,String q) {

        Item item = new Item();

        item.setId(1);
        item.setItemName(n);
        item.setItemQty(Integer.parseInt(q));
        item.setDate("null");

        long r = dataBaseHandler.insert(item);

        return r;
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
