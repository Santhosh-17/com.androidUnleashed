package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questionTextview;
    private TextView questionCounterTextview;
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;
  /*  SoundPool soundpool;
    public static int MAX_STREAMS = 4;
    public static int SOUND_PRIORITY = 1;
    public static int SOUND_QUALITY = 100;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.next);
        prevButton = findViewById(R.id.pre);
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        questionCounterTextview = findViewById(R.id.counter);
        questionTextview = findViewById(R.id.textField);

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);


        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                questionTextview.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                questionCounterTextview.setText(currentQuestionIndex + " / " + questionArrayList.size()); // 0 / 234
                Log.d("Inside", "processFinished: " + questionArrayList);

            }
        });

        // Log.d("Main", "onCreate: " + questionList);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pre:
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                    updateQuestion();
                }
                break;
            case R.id.next:
                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                updateQuestion();
                break;
            case R.id.trueButton:
                checkAnswer(true);
                updateQuestion();
                break;
            case R.id.falseButton:
                checkAnswer(false);
                updateQuestion();
                break;
        }

    }

    private void checkAnswer(boolean userChooseCorrect) {
        boolean answerIsTrue = questionList.get(currentQuestionIndex).isAnswerTrue();
        int toastMessageId = 0;
        if (userChooseCorrect == answerIsTrue) {

           // fadeView();
            toastMessageId = R.string.crt;
        } else {
           // shakeAnimation();
            toastMessageId = R.string.inCrt;
        }
        Toast.makeText(MainActivity.this, toastMessageId,
                Toast.LENGTH_SHORT)
                .show();
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionTextview.setText(question);
        questionCounterTextview.setText(currentQuestionIndex + " / " + questionList.size()); // 0 / 234

    }
}