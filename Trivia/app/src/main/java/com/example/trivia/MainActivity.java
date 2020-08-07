package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.Prefs;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;
import com.example.trivia.model.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questionTextview;
    private TextView currentScore,highScore,msg;
    private TextView questionCounterTextview;
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;
    private int Scores = 0;
    private Score s;
    private Prefs p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        s = new Score();
        p = new Prefs(MainActivity.this);

        currentScore = findViewById(R.id.score);
        highScore = findViewById(R.id.highScore);
        nextButton = findViewById(R.id.next);
        prevButton = findViewById(R.id.pre);
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        questionCounterTextview = findViewById(R.id.counter);
        questionTextview = findViewById(R.id.textField);
        msg = findViewById(R.id.msg);

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);

        currentScore.setText("Current Score: "+s.getScore());
        highScore.setText("Highest Score: "+p.getHighScore());

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
                msg.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                msg.setText(" ");
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                    updateQuestion();
                }
                break;
            case R.id.next:
                goNext();
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

            fadeView();
            addScore();
            toastMessageId = R.string.crt;
            msg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_circle_black_24dp, 0, 0, 0);
            msg.setText(" Correct");
        } else {
            shakeAnimation();
            subScore();
            toastMessageId = R.string.inCrt;
            msg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_incorrect, 0, 0, 0);
            msg.setText(" Incorrect");
        }
       // Toast.makeText(MainActivity.this, toastMessageId, Toast.LENGTH_SHORT).show();
    }

    private void subScore() {
        Scores -= 100;
        if(Scores > 0){
            s.setScore(Scores);
            currentScore.setText("Current Score: "+s.getScore());
        }
        else {
            Scores = 0;
            s.setScore(Scores);
            currentScore.setText("Current Score: "+s.getScore());
        }
    }

    private void addScore() {
        Scores += 100;
        s.setScore(Scores);
        currentScore.setText("Current Score: "+s.getScore());
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionTextview.setText(question);
        questionCounterTextview.setText(currentQuestionIndex + " / " + questionList.size()); // 0 / 234

    }


    private void fadeView() {
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);

        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
                questionTextview.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                questionTextview.setTextColor(Color.BLACK);
                goNext();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
                questionTextview.setTextColor(Color.WHITE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                questionTextview.setTextColor(Color.BLACK);
                goNext();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    protected void onPause() {
        p.saveHighScore(s.getScore());
      //  prefs.setState(currentQuestionIndex);
        super.onPause();
    }

    public void goNext(){
        currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
        updateQuestion();
        msg.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        msg.setText(" ");
    }
}