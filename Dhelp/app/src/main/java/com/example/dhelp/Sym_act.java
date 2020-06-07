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

public class Sym_act extends AppCompatActivity {

    EditText an,cn,ag,ca,w,wh,wha,what;
    Button s;
    FirebaseAuth auth;
    FirebaseFirestore fstore;
    FirebaseUser user;
    String name,num,age,address,wt,wht,whas,whats;
    boolean check = false;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Sym_act.this,DashBoard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sym_act);



        an = findViewById(R.id.an5);
        cn = findViewById(R.id.cn5);
        ag = findViewById(R.id.ag5);
        ca = findViewById(R.id.ca5);
        w = findViewById(R.id.w5);
        wh = findViewById(R.id.wh5);
        wha = findViewById(R.id.wha1);
        what = findViewById(R.id.what1);

        s = findViewById(R.id.s5);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();

        if(check){
            Intent intent = new Intent(Sym_act.this,Applied.class);
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
                wt = w.getText().toString();
                wht = wh.getText().toString();
                whas = wha.getText().toString();
                whats = what.getText().toString();

                DocumentReference documentReference = fstore.collection("Users").document(user.getUid()).collection("Documents").document("Symptoms");
                Map<String,Object> user1 = new HashMap<>();
                user1.put("applicant",name);
                user1.put("number",num);
                user1.put("age",age);
                user1.put("address",address);
                user1.put("types",wt);
                user1.put("days",wht);
                user1.put("tabletsName",whas);
                user1.put("hospitalName",whats);

                documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Debug","User is added");
                        Toast.makeText(Sym_act.this,"Applied!",Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(Sym_act.this,Applied.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
    }

}
