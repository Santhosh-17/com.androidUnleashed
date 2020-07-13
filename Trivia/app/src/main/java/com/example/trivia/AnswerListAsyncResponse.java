package com.example.trivia;

import com.example.trivia.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {

    public void processFinished(ArrayList<Question> questionArrayList);
}
