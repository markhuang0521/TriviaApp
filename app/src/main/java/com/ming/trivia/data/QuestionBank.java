package com.ming.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ming.trivia.controller.AppController;
import com.ming.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    private ArrayList<Question> questionArrayList = new ArrayList<>();
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";


    public List<Question> getQuestions(final QuestionAsyncResponse calllback) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // create question obj and insert jsonarr idx0, and idx1
                        for (int i = 0; i < response.length(); i++) {
                            Question question = new Question();
                            try {
                                question.setQuestionStr(response.getJSONArray(i).getString(0));
                                question.setCorrectAns(response.getJSONArray(i).getBoolean(1));
                                questionArrayList.add(question);

//                                Log.d("jsonArr", "onResponse: "
//                                        + question.getQuestionStr() + " " + question.isCorrectAns());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (null != calllback) {
                            calllback.processfinished(questionArrayList);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return questionArrayList;
    }


}
