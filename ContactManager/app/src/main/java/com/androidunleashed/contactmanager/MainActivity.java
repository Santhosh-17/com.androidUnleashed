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
        sandy.setPhoneNumber("98798787");

    //    db.addContact(sandy);

        List<Contacts> contactsList = db.getAllContacta();
        t1.append("Saved Contacts:\n");
        for(Contacts c : contactsList){
           // Log.d("Main", "onCreate: "+c.getId()+" "+c.getName());
            t1.append(c.getId()+". "+c.getName()+" "+c.getPhoneNumber()+"\n");
        }

        Contacts cm = db.getContact(3);
        t1.append("\nGot Contacts:\n"+"Id: "+cm.getId()+"\n"+"Name: "+cm.getName()+"\nPhone: "+cm.getPhoneNumber());

      /*  Contacts cm1 = db.getContact(2);
        cm1.setName("santhosh");
        cm1.setPhoneNumber("0000000000");
        int rw = db.update(cm1);

        List<Contacts> contactsList1 = db.getAllContacta();
        t1.append("\n\nAfter Update:\n");
        for(Contacts c : contactsList1){
            // Log.d("Main", "onCreate: "+c.getId()+" "+c.getName());
            t1.append(c.getId()+". "+c.getName()+" "+c.getPhoneNumber()+"\n");
        } */


      //DELETE A CONTACT
        db.deleteContact(3);

        List<Contacts> contactsList1 = db.getAllContacta();
        t1.append("\n\nAfter delete:\n");
        for(Contacts c : contactsList1){
            // Log.d("Main", "onCreate: "+c.getId()+" "+c.getName());
            t1.append(c.getId()+". "+c.getName()+" "+c.getPhoneNumber()+"\n");
        }


    }
}
