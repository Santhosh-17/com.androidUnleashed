package com.androidunleashed.shelper;

import android.graphics.Color;
import android.os.Bundle;

import com.androidunleashed.shelper.data.DatabaseHandler;
import com.androidunleashed.shelper.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button SaveButton;
    private EditText ItemName,Quantity,Colour,Size;
    private DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(this);

//        List<Item> itemsList = databaseHandler.getAllItem();
//        for (Item i : itemsList){
//            Log.d("Main", "onCreate: "+i.getiName());
//        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                createPopUpDialogue(view);

            }
        });
    }

    private void createPopUpDialogue(final View v1) {

        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);

        ItemName = view.findViewById(R.id.babyItem);
        Quantity = view.findViewById(R.id.itemQuantity);
        Colour = view.findViewById(R.id.ItemColor);
        Size = view.findViewById(R.id.ItemSize);
        SaveButton = view.findViewById(R.id.saveButton);


        builder.setView(view);
        dialog = builder.create(); //creating dialogue object
        dialog.show();

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ItemName.getText().toString().isEmpty() && !Quantity.getText().toString().isEmpty() ){

                    saveItem(v);
                    dialog.dismiss();
                    Snackbar.make(v1, "Item Saved", Snackbar.LENGTH_SHORT)
                            .show();

                }else{
                    Snackbar.make(v, "Empty Fields are not allowed!", Snackbar.LENGTH_SHORT)
                            .show();
                }

            }
        });


    }

    private void saveItem(View v) {

        Item item = new Item();
        int nSize = 0;
        String nColor = "Black";


        String newItem = ItemName.getText().toString().trim();
        int nQty = Integer.parseInt(Quantity.getText().toString().trim());


        if(Size.getText().toString().trim().isEmpty() &&
        Colour.getText().toString().trim().isEmpty()){
            nSize = 0;
            nColor = "Black";
        }else
        {
            nSize = Integer.parseInt(Size.getText().toString().trim());
            nColor = Colour.getText().toString().trim();
        }

        item.setiName(newItem);
        item.setiQuantity(nQty);
        item.setiSize(nSize);
        item.setiColor(nColor);

        databaseHandler.addItem(item);
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
