package com.example.sharedrefs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button submit;
    private TextView textView;
    private EditText editText;

    private static final String MESSAGE_ID = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editText.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences(MESSAGE_ID,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("message",msg);
                editor.apply();

                Toast.makeText(MainActivity.this,R.string.msg,Toast.LENGTH_SHORT).show();
            }
        });

        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID,MODE_PRIVATE);
        String value = getSharedPrefs.getString("message","Nothing Yet");
        textView.setText(value);
    }
}
