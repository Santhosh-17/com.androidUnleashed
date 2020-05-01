package com.example.showname;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    Button b1,b2;
    TextView tv;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        tv = findViewById(R.id.textView);
        et = findViewById(R.id.editText);

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                String temp;
                temp = et.getText().toString();
                if(temp.equals(""))
                {
                    tv.setText("Please enter your name in above box and Click the button");
                }else{
                    tv.setText("Hello "+temp+"! nice to meet you. This is my first android application!");
                 //   tv.setText("Thanks thala ... For Everything");
                }

            }

        });

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                et.setText("");
                tv.setText("");
            }

        });

    }
}
