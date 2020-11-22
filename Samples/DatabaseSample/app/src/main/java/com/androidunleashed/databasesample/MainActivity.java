package com.androidunleashed.databasesample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidunleashed.databasesample.Data.DataBaseHandler;
import com.androidunleashed.databasesample.Model.Item;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView t;
    Button b;
    DataBaseHandler dataBaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHandler = new DataBaseHandler(this);
        t = findViewById(R.id.t1);
        b = findViewById(R.id.b1);

//        Item item = new Item();
//
//        item.setId(1);
//        item.setItemName("Carrot");
//        item.setItemQty(2);
//        item.setItemColor("Orange");
//        item.setItemSize(2);
//        item.setDate("null");
//
//        long r = dataBaseHandler.insert(item);

//        t.setText("Row Added "+r );

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = dataBaseHandler.getAllItems();
                if(cursor.getCount() == 0){
                    t.append("Error");
                    return;
                }else {
                    StringBuffer stringBuffer = new StringBuffer();
                    while (cursor.moveToNext()){
                        stringBuffer.append("ID: "+cursor.getString(0)+"\n");
                        stringBuffer.append("Item Name: "+cursor.getString(1)+"\n");
                        stringBuffer.append("Item Qty: "+cursor.getString(2)+"\n");
                        stringBuffer.append("Item Color: "+cursor.getString(3)+"\n");
                        stringBuffer.append("Item Size: "+cursor.getString(4)+"\n");
//                        stringBuffer.append("Date: "+cursor.getString(5)+"\n\n");

                        DateFormat dateFormat = DateFormat.getDateInstance();
                        String formattedDate = dateFormat.format(
                                new Date(cursor.getLong(5)).getTime());

                        stringBuffer.append("Date: "+formattedDate+"\n\n");
                    }

                    String temp = stringBuffer.toString();
                    t.append("\n\n"+temp);

                }

            }
        });



    }
}
