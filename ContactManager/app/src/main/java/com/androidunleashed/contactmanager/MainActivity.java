package com.androidunleashed.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.androidunleashed.contactmanager.data.DatabaseHandler;
import com.androidunleashed.contactmanager.model.Contacts;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DatabaseHandler db = new DatabaseHandler(MainActivity.this);

        //CREATE CONTACTS OBJECT
        Contacts nivu = new Contacts();
        nivu.setName("Nivas");
        nivu.setPhoneNumber("9879879876");

        db.addContact(nivu);

        List<Contacts> contactsList = db.getAllContacta();

        for(Contacts c : contactsList){
            Log.d("Main", "onCreate: "+c.getId()+" "+c.getName());
        }

    }
}
