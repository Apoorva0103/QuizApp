package com.example.android.quizapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button submitButton;
    private AppCompatCheckBox checkBox1;
    private AppCompatCheckBox checkBox2;
    private AppCompatCheckBox checkBox3;
    private AppCompatCheckBox checkBox4;
    private AppCompatCheckBox checkBox5;
    private AppCompatCheckBox checkBox6;
    private AppCompatCheckBox checkBox7;
    private AppCompatCheckBox checkBox8;
    private Set<String> checkedOptions = new HashSet<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_survey);
        submitButton = findViewById(R.id.submit_button_survey);
        submitButton.setOnClickListener(this);
        checkBox1 = findViewById(R.id.checkbox1);
        checkBox1.setOnCheckedChangeListener(this);
        checkBox2 = findViewById(R.id.checkbox2);
        checkBox2.setOnCheckedChangeListener(this);
        checkBox3 = findViewById(R.id.checkbox3);
        checkBox3.setOnCheckedChangeListener(this);
        checkBox4 = findViewById(R.id.checkbox4);
        checkBox4.setOnCheckedChangeListener(this);
        checkBox5 = findViewById(R.id.checkbox5);
        checkBox5.setOnCheckedChangeListener(this);
        checkBox6 = findViewById(R.id.checkbox6);
        checkBox6.setOnCheckedChangeListener(this);
        checkBox7 = findViewById(R.id.checkbox7);
        checkBox7.setOnCheckedChangeListener(this);
        checkBox8 = findViewById(R.id.checkbox8);
        checkBox8.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        String optionsSelected = "";

        int i = 0;
        for (String selection : checkedOptions) {
            if (i==0) {
                optionsSelected = optionsSelected.concat(selection);
            } else if (i==checkedOptions.size()-1) {
                optionsSelected = optionsSelected.concat(" and ");
                optionsSelected = optionsSelected.concat(selection);
            } else {
                optionsSelected = optionsSelected.concat(", ");
                optionsSelected = optionsSelected.concat(selection);
            }
            i++;
        }

        String choicesString = getResources().getString(R.string.categories_selected, optionsSelected);
        Toast.makeText(this, choicesString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            checkedOptions.add(buttonView.getText().toString());
        } else {
            checkedOptions.remove(buttonView.getText().toString());
        }
    }
}
