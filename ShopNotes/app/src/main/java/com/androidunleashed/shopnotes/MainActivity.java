package com.androidunleashed.shopnotes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.androidunleashed.shopnotes.Data.DataBaseHandler;
import com.androidunleashed.shopnotes.Model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button SaveButton;
    DataBaseHandler dataBaseHandler;
    TextInputEditText ItemName, ItemQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataBaseHandler = new DataBaseHandler(this);

        Cursor cursor = dataBaseHandler.getAllItems();
        List<Item> i = show(cursor);

        for(Item item : i){
            Log.d("Debug", "onCreate: Id: "+item.getId());
            Log.d("Debug", "onCreate: Item: "+item.getItemName());
            Log.d("Debug", "onCreate: Qty: "+item.getItemQty());
            Log.d("Debug", "onCreate: Date: "+item.getDate());
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpDialogue(view);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
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

                if( !name.isEmpty() || !qty.isEmpty() ){

                    long r = saveItem(v,name,qty);
                    if(r == -1){

                        dialog.dismiss();
                        Snackbar.make(v1, "Item not Saved ", Snackbar.LENGTH_LONG)
                                .show();
                    }else{

                        Snackbar.make(v, "Item Saved "+r, Snackbar.LENGTH_LONG)
                                .show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                dialog.dismiss();

                                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                                startActivity(intent);

                            }
                        },1200);



                    }

                }else{
                    Snackbar.make(v, "Empty Fields are not allowed!", Snackbar.LENGTH_SHORT)
                            .show();
                }

            }
        });


    }

    private long saveItem(View v,String n,String q) {

        Item item = new Item();

        item.setId(1);
        item.setItemName(n);
        item.setItemQty(Integer.parseInt(q));
        item.setDate("null");

        long r = dataBaseHandler.insert(item);

        return r;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
