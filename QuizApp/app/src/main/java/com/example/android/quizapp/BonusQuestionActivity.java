package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class BonusQuestionActivity extends AppCompatActivity implements View.OnClickListener {

    EditText answerEt;
    Button submitButton;
    int scoreCounter;
    ImageView cheeringImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bonus_question);
        scoreCounter = getIntent().getIntExtra("ScoreCounter", 0);
        answerEt = findViewById(R.id.bonus_answer_space);
        submitButton = findViewById(R.id.submit_button_bonus);
        cheeringImage = findViewById(R.id.correct_or_wrong_iv);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /**
         * Code to Hide keyboard as soon as the button is clicked
         */
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(answerEt.getWindowToken(), 0);
        /**
         * Code to hide keyboard end
         */

        String textFromET = answerEt.getText() != null ? answerEt.getText().toString() : "";
        String correctAnswer = getResources().getString(R.string.ans_to_bonus_question);

        if(textFromET.equalsIgnoreCase(correctAnswer)) {
            cheeringImage.setImageResource(R.drawable.correct);
            scoreCounter++;
        } else {
            cheeringImage.setImageResource(R.drawable.wrong);
        }
        Toast.makeText(this, getString(R.string.current_score, scoreCounter), Toast.LENGTH_SHORT).show();
        setNewQuestionAfterDelay();
    }

    private void setNewQuestionAfterDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setSurvey();
            }
        }, 1000);
    }

    private void setSurvey() {
        Intent surveyIntent = new Intent(this, SurveyActivity.class);
        startActivity(surveyIntent);
    }
}
