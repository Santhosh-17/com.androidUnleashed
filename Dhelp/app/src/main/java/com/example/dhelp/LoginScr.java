package com.example.dhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScr extends AppCompatActivity {

    ProgressBar progressBar;
    Button login;
    TextView dha;
    EditText username , password;
    FirebaseAuth auth;



    @Override
    protected void onStart() {
        super.onStart();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(LoginScr.this,DashBoard.class));
            finish();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_scr);

        progressBar = findViewById(R.id.progressbar1);
        username = findViewById(R.id.usernamelg);
        password = findViewById(R.id.passwdlg);
        login = findViewById(R.id.loginlg);
        dha = findViewById(R.id.dha);

        auth = FirebaseAuth.getInstance();



        dha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScr.this,Signup.class);
                startActivity(intent);
                finish();
            }
        });





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = username.getText().toString();
                String pwd = password.getText().toString();

                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    username.setError("Please enter a valid email");
                    username.requestFocus();
                    return;
                }
                if(password.length()<6){
                    password.setError("Minimum Length of password should be 6");
                    password.requestFocus();
                    return;
                }

                if(mail.isEmpty()) {
                    username.setError("Email is required");
                    username.requestFocus();
                    return;
                }else if(pwd.isEmpty()){
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(mail,pwd);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginScr.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    private void loginUser(String mail, String pwd) {

        auth.signInWithEmailAndPassword(mail, pwd).addOnCompleteListener(LoginScr.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                  //  Toast.makeText(LoginScr.this, "Login Successful!.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginScr.this,DashBoard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Log.w("Debug", "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginScr.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    password.setText("");
                }
            }
        });




    }
}
