package com.androidunleashed.shopnotes;

import android.os.Bundle;

import com.androidunleashed.shopnotes.Data.DataBaseHandler;
import com.androidunleashed.shopnotes.Model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

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
                        dialog.dismiss();
                        Snackbar.make(v1, "Item Saved ", Snackbar.LENGTH_LONG)
                                .show();
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
