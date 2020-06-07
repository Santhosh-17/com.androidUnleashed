package com.example.dhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class verify_mail extends AppCompatActivity {

    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mail);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();



        if(user.isEmailVerified()){
            Toast.makeText(verify_mail.this, "Registration Successful!.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(verify_mail.this,DashBoard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        FirebaseAuth.getInstance().signOut();


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(verify_mail.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
