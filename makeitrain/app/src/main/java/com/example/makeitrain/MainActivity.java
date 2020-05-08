package com.example.makeitrain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    Button mir;
    TextView dollar;
    //ConstraintLayout cl ;
    int temp = 0;
    int money = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mir = findViewById(R.id.mir);
        dollar = findViewById(R.id.dollar);


      //  cl = findViewById(R.id.cl1);

        mir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //temp++;
                NumberFormat nf = NumberFormat.getCurrencyInstance();
                money = money + 1000;
                dollar.setText(""+nf.format(money));

                if(money==20000)
                {
                    dollar.setTextColor(getResources().getColor(R.color.mc));
                }
                if(money == 30000)
                {
                    dollar.setTextColor(Color.RED);
                }


                /*if(temp == 1)
                {
                    cl.setBackgroundColor(Color.RED);
                }
                else if(temp == 2)
                {
                    cl.setBackgroundColor(Color.BLUE);
                }
                else if (temp == 3)
                {
                    cl.setBackgroundColor(Color.CYAN);
                }
                else if(temp == 4)
                {
                    cl.setBackgroundColor(Color.YELLOW);
                }
                else if(temp == 5)
                {
                    cl.setBackgroundColor(Color.WHITE);
                }
                else
                {
                    cl.setBackgroundColor(Color.DKGRAY);
                    temp = 0;
                }*/

            }
        });
    }

    public void showTag(View v)
    {
        Toast.makeText(getApplicationContext(),R.string.mir,Toast.LENGTH_LONG).show();
    }
}
