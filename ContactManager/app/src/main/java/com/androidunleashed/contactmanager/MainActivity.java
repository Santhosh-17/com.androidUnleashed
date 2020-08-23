package com.androidunleashed.contactmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.androidunleashed.contactmanager.adapter.RecyclerViewAdapter;
import com.androidunleashed.contactmanager.data.DatabaseHandler;
import com.androidunleashed.contactmanager.model.Contacts;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private ArrayList<Contacts> contactArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactArray = new ArrayList<>();

        DatabaseHandler db = new DatabaseHandler(MainActivity.this);

        List<Contacts> contactsList = db.getAllContacta();
        for(Contacts c : contactsList){
            contactArray.add(c);
        }


        //SETUP ADAPTER
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,contactArray);

        recyclerView.setAdapter(recyclerViewAdapter);


    }
}
