package com.example.fathimamulaffer.zengames;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.security.Timestamp;

/* This activity opens up when you click the profile button
* Handles setting of notification alarm + graph */
public class ProfileActivity extends AppCompatActivity
    implements View.OnClickListener{
    private TextView dayCount3;
    private SharedPreference sp;
    private TextView uid;
    private TextView picktime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sp = new SharedPreference();
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar3);
        setSupportActionBar(my_toolbar);
        dayCount3 = (TextView) findViewById(R.id.daycount2);
        dayCount3.setText("Day " +(sp.getDayCount(this))+"/21");
        uid = (TextView) findViewById(R.id.uid);
        uid.setText(sp.getUserID(this));
        picktime = (TextView) findViewById(R.id.noti_time);
        picktime.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.noti_time:
                //notification time has been selected
                DialogFragment newF = new TimePickerFragment();
                newF.show(getSupportFragmentManager(),"TimePicker");
        }
    }
}
