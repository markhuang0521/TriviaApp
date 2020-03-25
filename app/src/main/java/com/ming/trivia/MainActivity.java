package com.ming.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ming.trivia.data.QuestionAsyncResponse;
import com.ming.trivia.data.QuestionBank;
import com.ming.trivia.model.Question;
import com.ming.trivia.util.Prefs;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String SP_ID = "save_score";
    private Prefs sp;

    private CardView questionCard;
    private TextView questionStr;
    private TextView questionNo;
    private TextView correctCount;
    private TextView incorrectCount;
    private TextView textBestScore;
    private TextView textCurScore;
    private Button btnTrue;
    private Button btnFalse;
    private Button btnSave;

    private ImageButton btnPrev;
    private ImageButton btnNext;
    private int curQuestionIdx = 0;
    private int answeredCorrect;
    private int answeredIncorrect;
    private int curScore;
    private int bestScore;

    private List<Question> questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        sp.getBestScore();
//        sp.getQuestionNo();

//        field initalized
        questionCard = findViewById(R.id.cardView_question);
        questionStr = findViewById(R.id.text_questionStr);
        questionNo = findViewById(R.id.text_questionNum);
        correctCount = findViewById(R.id.text_correct_count);
        incorrectCount = findViewById(R.id.text_incorrect_count);
        textCurScore = findViewById(R.id.text_cur_score);
        textBestScore = findViewById(R.id.text_best_score);


        btnFalse = findViewById(R.id.btn_false);
        btnNext = findViewById(R.id.btn_next);
        btnPrev = findViewById(R.id.btn_prev);
        btnTrue = findViewById(R.id.btn_true);
        btnSave = findViewById(R.id.btn_save);


        // onclick functions
        btnTrue.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnFalse.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        questionList = new QuestionBank().getQuestions(new QuestionAsyncResponse() {
            @Override
            public void processfinished(ArrayList<Question> list) {
                //loading score;
                questionStr.setText(list.get(curQuestionIdx).getQuestionStr());
                questionNo.setText(getString(R.string.question_no, curQuestionIdx, questionList.size()));

            }
        });
        //SharePrefences user data loaded
        sp = new Prefs(MainActivity.this);
        loadScore();


    }


    @Override
    protected void onPause() {
        super.onPause();
        saveScore();

    }


    @Override
    protected void onResume() {
        super.onResume();

        resumeScore();
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        sp.setcurScore(0);
//        sp.setQuestionNo(0);
//    }

    //overriding the onclick for different button
    @Override
    //getting the view to grab the btn
    public void onClick(View v) {
        switch (v.getId()) { // make sure to use get id()
            case R.id.btn_true:
                checkAns(true);

                break;
            case R.id.btn_false:
                checkAns(false);
                break;
            case R.id.btn_prev:
                curQuestionIdx--;
                slideRightAnim();
                updateQuestion();
                break;
            case R.id.btn_next:
                curQuestionIdx++;
                slideLeftAnim();
                updateQuestion();
                break;
            case R.id.btn_save:
                saveScore();
                break;
        }
    }

    private void saveScore() {
//        SharedPreferences sharedPreferences = getSharedPreferences(SP_ID, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("best_score", Math.max(curScore, bestScore));
////        editor.putInt("current_score", curScore);
////        editor.putInt("incorrect_answer", answeredIncorrect);
//        editor.putInt("question_index", curQuestionIdx);
//        editor.apply();
        sp.setQuestionNo(curQuestionIdx);
        sp.setcurScore(curScore);
        sp.setBestScore(curScore);
        Toast.makeText(MainActivity.this, "scores are saved!!", Toast.LENGTH_SHORT).show();


    }

    private void resumeScore() {
        curScore = sp.getcurScore();
        curQuestionIdx = sp.getQuestionNo();
        textCurScore.setText(MessageFormat.format("current score: {0}", curScore));
//        curQuestionIdx = sp.getQuestionNo();
        questionNo.setText(getString(R.string.question_no, curQuestionIdx, questionList.size()));


    }

    private void loadScore() {

        bestScore = sp.getBestScore();
        textCurScore.setText(MessageFormat.format("current score: {0}", curScore));

        textBestScore.setText(MessageFormat.format("best score: {0}", sp.getBestScore()));

//        correctCount.setText(String.valueOf(answeredCorrect));
//        incorrectCount.setText(String.valueOf(answeredIncorrect));

    }


    //update question next/prev
    private void updateQuestion() {
        if (curQuestionIdx >= 0) {
            curQuestionIdx = curQuestionIdx % questionList.size();

            questionNo.setText(getString(R.string.question_no, curQuestionIdx, questionList.size()));
            questionStr.setText(questionList.get(curQuestionIdx).getQuestionStr());

        }

    }

    private void checkAns(boolean userAns) {
        boolean corrAns = questionList.get(curQuestionIdx).isCorrectAns();
        if (corrAns == userAns) {
            fadeAnim();
            answeredCorrect++;
            curScore += 100;
            correctCount.setText(String.valueOf(answeredCorrect));
            Toast.makeText(MainActivity.this, "You are Correct!!", Toast.LENGTH_SHORT).show();
        } else {
            shakeAnim();
            answeredIncorrect++;
            curScore = curScore - 100 <= 0 ? 0 : curScore - 100;
            incorrectCount.setText(String.valueOf(answeredIncorrect));
            Toast.makeText(MainActivity.this, "You are Wrong!!", Toast.LENGTH_SHORT).show();

        }
        textCurScore.setText(MessageFormat.format("current score: {0}", curScore));
    }

    private void shakeAnim() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
        questionCard.startAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                questionCard.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                questionCard.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void fadeAnim() {
//        full code method
//        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
//        alphaAnimation.setDuration(300);
//        alphaAnimation.setRepeatMode(Animation.REVERSE);

//        animation in xml
        Animation scale = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale);
        Animation blink = AnimationUtils.loadAnimation(MainActivity.this, R.anim.blink);

        questionCard.startAnimation(blink);

        blink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                questionCard.setCardBackgroundColor(Color.GREEN);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                questionCard.setCardBackgroundColor(Color.WHITE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });


    }

    private void slideRightAnim() {
        Animation slide = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_right);
        questionCard.startAnimation(slide);

    }

    private void slideLeftAnim() {
        Animation slide = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_left);
        questionCard.startAnimation(slide);

    }
}
