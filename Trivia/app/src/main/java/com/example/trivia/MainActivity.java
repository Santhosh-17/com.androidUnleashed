package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView questionTextView , counterText;
    Button trueButton, falseButton;
    ImageButton nextButton , preButton;
    List<Question> questionList;
    int i= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.textField);
        counterText = findViewById(R.id.counter);
        nextButton = findViewById(R.id.next);
        preButton = findViewById(R.id.pre);
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);

        counterText.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        preButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);

        questionList = new QuestionBank().getQuestion(new AnswerListAsyncResponse(){
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                questionTextView.setText(questionArrayList.get(i).getAnswer());
                counterText.setText(i+" / "+questionList.size());
            }
        });
    }

    public void updateQuestions(){
        questionTextView.setText(questionList.get(i).getAnswer());
        counterText.setText(i+" / "+questionList.size());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next: i = (i+1) % questionList.size();
                            updateQuestions();
                            break;
            case R.id.pre: if(i>0){
                              i = (i-1) % questionList.size();
                              updateQuestions();
                           }
                           break;
            case R.id.trueButton: checkAnswers(true);
                                  break;
            case R.id.falseButton: checkAnswers(false);
                                   break;
        }
    }

    public void checkAnswers(boolean checkAnswer){
        boolean check = questionList.get(i).isAnswerTrue();
        int toastId = 0;
        if(checkAnswer == check){
            toastId = R.string.crt;
        }else{
            toastId = R.string.inCrt;
        }

        Toast.makeText(MainActivity.this,toastId,Toast.LENGTH_SHORT).show();

    }



}
