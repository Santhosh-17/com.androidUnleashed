package com.example.dhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Personal extends AppCompatActivity {

    EditText an,cn,ag,ca;
    Button s;
    FirebaseAuth auth;
    FirebaseFirestore fstore;
    FirebaseUser user;
    String name,num,age,address,wt,wht;
    boolean check = false;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Personal.this,DashBoard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);


        an = findViewById(R.id.an3);
        cn = findViewById(R.id.cn3);
        ag = findViewById(R.id.ag3);
        ca = findViewById(R.id.ca3);

        s = findViewById(R.id.s3);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();


        if(check){
            Intent intent = new Intent(Personal.this,Applied.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check = true;

                name = an.getText().toString();
                num = cn.getText().toString();
                age = ag.getText().toString();
                address = ca.getText().toString();


                DocumentReference documentReference = fstore.collection("Users").document(user.getUid()).collection("Documents").document("Personal Pass");
                Map<String,Object> user1 = new HashMap<>();
                user1.put("applicant",name);
                user1.put("number",num);
                user1.put("age",age);
                user1.put("address",address);
                documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Debug","User is added");
                        Toast.makeText(Personal.this,"Applied!",Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(Personal.this,Applied.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
    }

}
