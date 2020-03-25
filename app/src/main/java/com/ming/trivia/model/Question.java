package com.ming.trivia.model;

public class Question {
    private String questionStr;
    private boolean correctAns;

    public Question() {
    }

    public Question(String questionStr, boolean correctAns) {
        this.questionStr = questionStr;
        this.correctAns = correctAns;
    }


    public String getQuestionStr() {
        return questionStr;
    }

    public void setQuestionStr(String questionStr) {
        this.questionStr = questionStr;
    }

    public boolean isCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(boolean correctAns) {
        this.correctAns = correctAns;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionStr='" + questionStr + '\'' +
                ", correctAns=" + correctAns +
                '}';
    }
}
