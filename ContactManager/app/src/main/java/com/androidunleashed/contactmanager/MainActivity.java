package com.androidunleashed.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.androidunleashed.contactmanager.data.DatabaseHandler;
import com.androidunleashed.contactmanager.model.Contacts;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.t1);

        DatabaseHandler db = new DatabaseHandler(MainActivity.this);

        //CREATE CONTACTS OBJECT
        Contacts sandy = new Contacts();
        sandy.setName("sandy");
        sandy.setPhoneNumber("4564564566");

        db.addContact(sandy);

        List<Contacts> contactsList = db.getAllContacta();

        for(Contacts c : contactsList){
           // Log.d("Main", "onCreate: "+c.getId()+" "+c.getName());
            t1.append(c.getId()+". "+c.getName()+" "+c.getPhoneNumber()+"\n");
        }

    }
}
