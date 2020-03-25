package com.ming.trivia.data;

import com.ming.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

public interface QuestionAsyncResponse {
    void processfinished(ArrayList<Question> list);
}
