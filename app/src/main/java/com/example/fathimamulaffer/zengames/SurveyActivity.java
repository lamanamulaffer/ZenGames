package com.example.fathimamulaffer.zengames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(my_toolbar);
    }
}
