package com.androidunleashed.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.androidunleashed.contactmanager.data.DatabaseHandler;
import com.androidunleashed.contactmanager.model.Contacts;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> contactArray;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        contactArray = new ArrayList<>();

        DatabaseHandler db = new DatabaseHandler(MainActivity.this);


//        db.addContact(new Contacts("sandy","7346572647"));
//        db.addContact(new Contacts("vj","989898989"));
//        db.addContact(new Contacts("praveen","4565789321"));
//        db.addContact(new Contacts("saran","123445689"));
//        db.addContact(new Contacts("yogesh","0987645678"));
//        db.addContact(new Contacts("krish","345678232"));
//        db.addContact(new Contacts("pranav","8765458722"));
//        db.addContact(new Contacts("gokul","5678987656"));
//        db.addContact(new Contacts("gold","123456723"));
//        db.addContact(new Contacts("gopi","876345678"));
//        db.addContact(new Contacts("raja","987653456"));
//        db.addContact(new Contacts("navas","7652345687"));

//        CREATE CONTACTS OBJECT
//        Contacts sandy = new Contacts();
//        sandy.setName("sandy");
//        sandy.setPhoneNumber("98798787");
//
//        db.addContact(sandy);



        List<Contacts> contactsList = db.getAllContacta();
        for(Contacts c : contactsList){
            contactArray.add(c.getName());
        }

        // CREATE ARRAY ADAPTER
        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                contactArray
        );

        //ADD TO LISTVIEW
        listView.setAdapter(arrayAdapter);

//        Contacts cm = db.getContact(1);
//        t1.append("\nGot Contacts:\n"+"Id: "+cm.getId()+"\n"+"Name: "+cm.getName()+"\nPhone: "+cm.getPhoneNumber()+"\n\n");
//
//        Contacts cm1 = db.getContact(2);
//        cm1.setName("santhosh");
//        cm1.setPhoneNumber("0000000000");
//        int rw = db.update(cm1);
//
//        List<Contacts> contactsList1 = db.getAllContacta();
//        t1.append("\n\nAfter Update:\n");
//        for(Contacts c : contactsList1){
//            // Log.d("Main", "onCreate: "+c.getId()+" "+c.getName());
//            t1.append(c.getId()+". "+c.getName()+" "+c.getPhoneNumber()+"\n");
//        }
//
//        DELETE A CONTACT
//        db.deleteContact(2);
//
//        List<Contacts> contactsList1 = db.getAllContacta();
//        t1.append("\n\nAfter delete:\n");
//        for(Contacts c : contactsList1){
//            // Log.d("Main", "onCreate: "+c.getId()+" "+c.getName());
//            t1.append(c.getId()+". "+c.getName()+" "+c.getPhoneNumber()+"\n");
//        }
//
//      GET COUNT
//        t1.append("No.of Contacts:\n"+db.getCount());



    }
}
