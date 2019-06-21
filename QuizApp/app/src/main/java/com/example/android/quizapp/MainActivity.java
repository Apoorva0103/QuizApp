package com.example.android.quizapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView questionNumberTextView;
    TextView questionTextView;
    TextView currentScore;
    ImageView imageView;
    RadioGroup radioGroup;
    Button buttonSubmit;

    int questionCounter = 1;
    int scoreCounter = 0;

    HashMap<Integer, QuestionData> questionDataMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionNumberTextView = findViewById(R.id.heading);
        questionTextView = findViewById(R.id.question);
        imageView = findViewById(R.id.correct_or_wrong_iv);
        currentScore = findViewById(R.id.curr_score);

        radioGroup = findViewById(R.id.radio_group);

        buttonSubmit = findViewById(R.id.submit_button);
        buttonSubmit.setOnClickListener(this);

        populateQuestionsMap();
        setQuestionOnView();
    }

    @Override
    public void onClick(View view) {
        if (radioGroup.getCheckedRadioButtonId() != -1
                && questionCounter <= questionDataMap.size() && buttonSubmit.isEnabled()) {
            buttonSubmit.setEnabled(false);
            verifyAnswer();
        }
    }

    private void verifyAnswer() {
        RadioButton selected = findViewById(radioGroup.getCheckedRadioButtonId());
        int selectedOptionPosition = radioGroup.indexOfChild(selected);
        imageView.setVisibility(View.VISIBLE);
        questionCounter++;
        if (questionCounter <= 6) {
            if (radioGroup.getTag().equals(selectedOptionPosition)) {
                imageView.setImageResource(R.drawable.correct);
                scoreCounter++;
            } else {
                imageView.setImageResource(R.drawable.wrong);
            }

            if (questionCounter <= questionDataMap.size()) {
                setNewQuestionAfterDelay();
            }

            currentScore.setText(getResources()
                    .getString(R.string.current_score, scoreCounter));
        } else {
            Intent bonusQuestionIntent = new Intent(this, BonusQuestionActivity.class);
            bonusQuestionIntent.putExtra("ScoreCounter", scoreCounter);
            startActivity(bonusQuestionIntent);
        }
    }

    private void setNewQuestionAfterDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setQuestionOnView();
            }
        }, 1000);
    }

    private void setQuestionOnView() {
        QuestionData questionInfoForView = questionDataMap.get(questionCounter);
        questionNumberTextView.setText(
                getResources()
                        .getString(R.string.question_heading,
                                questionInfoForView.getQuestionNumber()));
        questionTextView.setText(questionInfoForView.getQuestion());
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            ((RadioButton) radioGroup.getChildAt(i)).setText(questionInfoForView.getOptions()[i]);
        }
        radioGroup.setTag(questionInfoForView.getCorrectOption());
        radioGroup.clearCheck();
        imageView.setVisibility(View.GONE);
        buttonSubmit.setEnabled(true);
    }

    private void populateQuestionsMap() {
        List<String> data = readFileFromRawDirectory(R.raw.quiz);
        for (String eachQuestion : data) {
            String[] eachQuestionInfoString = eachQuestion.split(",");
            QuestionData questionData = createQuestionsDataObject(eachQuestionInfoString);
            questionDataMap.put(questionData.getQuestionNumber(), questionData);
        }
    }

    private QuestionData createQuestionsDataObject(String[] questionsInfo) {
        QuestionData questionDataObj = new QuestionData();
        questionDataObj.setQuestionNumber(Integer.parseInt(questionsInfo[0]));
        questionDataObj.setQuestion(questionsInfo[1]);
        questionDataObj.setOptions(questionsInfo[2].split("-"));
        questionDataObj.setCorrectOption(Integer.parseInt(questionsInfo[3]));
        return questionDataObj;
    }

    private List<String> readFileFromRawDirectory(int resourceId) {
        List<String> listOfQuestionsAndOptions = new ArrayList<>();
        InputStream iStream = getResources().openRawResource(resourceId);
        InputStreamReader iStreamReader = new InputStreamReader(iStream);
        BufferedReader buffReader = new BufferedReader(iStreamReader);
        try {
            String eachLine;
            do {
                eachLine = buffReader.readLine();
                if (eachLine != null) {
                    listOfQuestionsAndOptions.add(eachLine);
                }
            } while (eachLine != null);
            buffReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return listOfQuestionsAndOptions;
    }

}
