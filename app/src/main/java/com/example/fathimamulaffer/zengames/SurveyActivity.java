package com.example.fathimamulaffer.zengames;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//Saves survey to log file
//sends broadcast
public class SurveyActivity extends AppCompatActivity{
    private String survey_action = "com.example.fathimamulaffer.zengames.Survey_DONE";
    TextView survey_done;
    RadioGroup q1,q2,q3;
    EditText q4;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        context = this;
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(my_toolbar);
        //initialize elements
        q1 = (RadioGroup)findViewById(R.id.q1);
        q2 = (RadioGroup)findViewById(R.id.q2);
        q3 = (RadioGroup)findViewById(R.id.q3);
        q4 = (EditText) findViewById(R.id.q4);
        survey_done = (TextView) findViewById(R.id.survey_done);
        //save survey -> send broadcast -> go to main activity
        survey_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSurvey();
                Intent i = new Intent(survey_action);
                sendBroadcast(i);
                Intent ii = new Intent(context,MainActivity.class);
                startActivity(ii);
            }
        });

    }

    //When survey is completed - we log the details
    public void saveSurvey(){
        int q1id = q1.getCheckedRadioButtonId(); //if not selected - then returns -1
        int q2id = q2.getCheckedRadioButtonId();
        int q3id = q3.getCheckedRadioButtonId();
        String q1event, q2event, q3event;
        if (q1id != -1){//user has checked some option
            RadioButton q1selected = (RadioButton) findViewById(q1id);
            q1event = "Survey q1: " + q1selected.getText();
        }else {q1event = "Survey q1: No answer selected";}
        if (q2id != -1){//user has checked some option
            RadioButton q2selected = (RadioButton) findViewById(q2id);
            q2event = "Survey q2: " + q2selected.getText();
        }else {q2event = "Survey q2: No answer selected";}
        if (q3id != -1){//user has checked some option
            RadioButton q3selected = (RadioButton) findViewById(q3id);
            q3event = "Survey q3: " + q3selected.getText();
        }else {q3event = "Survey q3: No answer selected";}
        String q4event = "Survey q4: "+ q4.getText().toString();
        Long time = (Long) System.currentTimeMillis();
        Logging log = new Logging(this);
        log.writeLog(q1event,time);
        log.writeLog(q2event,time);
        log.writeLog(q3event,time);
        log.writeLog(q4event,time);
    }

}
