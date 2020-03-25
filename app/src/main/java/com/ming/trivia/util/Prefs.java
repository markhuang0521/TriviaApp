package com.ming.trivia.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences sp;
//    private SharedPreferences.Editor editor;

    public Prefs(Activity activity) {
        sp = activity.getPreferences(Context.MODE_PRIVATE);
//        editor = sp.edit();
//        editor.apply();


    }

    public void setcurScore(int score) {
        sp.edit().putInt("cur_score", score).apply();
    }

    public int getcurScore() {
        return sp.getInt("cur_score", 0);

    }

    public void setAnswerStat(int correct, int incorrect) {
        sp.edit().putInt("correct_answer", correct).apply();
        sp.edit().putInt("incorrect_answer", incorrect).apply();
    }


    public void setBestScore(int score) {
        int curBestScore = sp.getInt("best_score", 0);
        sp.edit().putInt("best_score", Math.max(score, curBestScore)).apply();
    }

    public int getBestScore() {
        return sp.getInt("best_score", 0);

    }

    public void setQuestionNo(int idx) {
        sp.edit().putInt("question_index", idx).apply();

    }

    public int getQuestionNo() {
        return sp.getInt("question_index", 0);

    }


}
