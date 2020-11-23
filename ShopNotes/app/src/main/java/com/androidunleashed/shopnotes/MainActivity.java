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
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button SaveButton;
    private DataBaseHandler dataBaseHandler;
    private TextInputEditText ItemName, ItemQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataBaseHandler = new DataBaseHandler(this);

        byPassActivity();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpDialogue(view);
            }
        });
    }

    public void byPassActivity() {
        if (dataBaseHandler.getItemsCount() > 0) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
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

                    long r = saveItem(v,name,qty);
                    if(r == -1){

                        dialog.dismiss();
                        Snackbar.make(v1, "Item not Saved ", Snackbar.LENGTH_LONG)
                                .show();
                    }else{

                        Toast.makeText(MainActivity.this,"Item Added",Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                dialog.dismiss();

                                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

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


}
