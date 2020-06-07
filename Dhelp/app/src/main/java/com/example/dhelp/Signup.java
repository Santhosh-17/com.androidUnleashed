package com.example.dhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    ProgressBar progressBar;
    Button sign,cancel;
    FirebaseAuth auth;
    EditText username,mail,pwd,cpwd;
    FirebaseFirestore fstore;
    String name;
    String maddress,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressBar = findViewById(R.id.progressbar);
        username = findViewById(R.id.username);
        mail= findViewById(R.id.mail);
        pwd = findViewById(R.id.passwd);
        cpwd = findViewById(R.id.cpasswd);
        sign = findViewById(R.id.signup);
        cancel = findViewById(R.id.Cancel);


        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this,MainActivity.class));
                finish();
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = username.getText().toString();
                maddress = mail.getText().toString();
                password = pwd.getText().toString();
                String cpassword = cpwd.getText().toString();

                if(!Patterns.EMAIL_ADDRESS.matcher(maddress).matches()){
                    mail.setError("Please enter a valid email");
                    mail.requestFocus();
                    return;
                }
                if(password.length()<6){
                    pwd.setError("Minimum Length of password should be 6");
                    pwd.requestFocus();
                    return;
                }

                if(maddress.isEmpty()) {
                    mail.setError("Email is required");
                    mail.requestFocus();
                    return;
                }else if(password.isEmpty()){
                    pwd.setError("Password is required");
                    pwd.requestFocus();
                    return;
                }else if(name.isEmpty()){
                    username.setError("UserName is required");
                    username.requestFocus();
                    return;
                }else if(cpassword.isEmpty()){
                    cpwd.setError("Please enter the password again");
                    cpwd.requestFocus();
                    return;
                }else if(!password.equals(cpassword))
                {
                    Toast.makeText(Signup.this, "InCorrect Passwords", Toast.LENGTH_SHORT).show();
                }else
                {
                    signUp();



                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Signup.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    private void signUp() {

        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(maddress,password).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    FirebaseUser user = auth.getCurrentUser();

                    if(user.isEmailVerified()){
                        Toast.makeText(Signup.this, "Registration Successful!.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Signup.this,DashBoard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }else{
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(Signup.this,"Verification Mail has sent",Toast.LENGTH_SHORT).show();

                            }
                        });

                        String temp = " ";

                        DocumentReference documentReference = fstore.collection("Users").document(user.getUid()).collection("Documents").document("Profile Details");
                        Map<String,Object> user1 = new HashMap<>();
                        user1.put("Fname",name);
                        user1.put("mail",maddress);
                        user1.put("phone", temp);
                        user1.put("age", temp);
                        user1.put("dob", temp);
                        user1.put("address", temp);
                        documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Debug","User is added");
                            }
                        });
                        Intent intent = new Intent(Signup.this,verify_mail.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }




                } else {
                    Log.w("Debug", "signInWithEmail:failure", task.getException());
                    Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    pwd.setText("");
                    cpwd.setText("");
                }
            }
        });
    }
}
