package com.androidunleashed.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class FullDetails extends AppCompatActivity {

    private TextView tname,tphn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_details);

        tname = findViewById(R.id.nameText);
        tphn = findViewById(R.id.phnText);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String name = bundle.getString("cname");
            String phone = bundle.getString("phn");

            tname.setText(name);
            tphn.setText(phone);

        }

    }
}
