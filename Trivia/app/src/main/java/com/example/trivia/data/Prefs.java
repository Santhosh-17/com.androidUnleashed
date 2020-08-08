package com.example.trivia.data;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {
    SharedPreferences sharedPreferences;
    public Prefs(Activity activity){
        this.sharedPreferences = activity.getPreferences(activity.MODE_PRIVATE);
    }
    public void saveHighScore(int Score){
        int lastScore = sharedPreferences.getInt("highScore",0);
        if(Score > lastScore){
            sharedPreferences.edit().putInt("highScore",Score).apply();
        }
    }
    public int getHighScore() {
        return sharedPreferences.getInt("highScore", 0);
    }

    public void setState(int index){
        sharedPreferences.edit().putInt("index_state",index).apply();
    }

    public int getState(){
        return sharedPreferences.getInt("index_state",0);
    }
}
