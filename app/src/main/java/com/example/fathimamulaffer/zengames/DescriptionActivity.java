package com.example.fathimamulaffer.zengames;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
* Activity show cases game descriptions */
public class DescriptionActivity extends AppCompatActivity
implements OnFragmentListener{

    private TextView dayCount2;
    private SharedPreference sp;
    private Button btn;
    //This maintains a ref to what the current fragment being used is
    private Integer curFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        sp = new SharedPreference();
        //Initializing elements in the main activity layout file
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(my_toolbar);
        dayCount2 = (TextView) findViewById(R.id.daycount2);
        dayCount2.setText("Day " +(sp.getDayCount(this))+"/21");
        curFragment = 0; //def value
        /***** Testing the intent filters for disabling ***/
        /** Button press - acts like an event that tells us that the shared pref game done is done**/
        btn = (Button) findViewById(R.id.b);
        //depending on the current fragment
        //sends intent to receiver w/ approp filter
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curFragment == 1){
                    Intent i = new Intent("com.example.fathimamulaffer.zengames.CO_DONE");
                    sendBroadcast(i);
                }
                else if (curFragment == 2){
                    Intent i = new Intent("com.example.fathimamulaffer.zengames.GI_DONE");
                    sendBroadcast(i);
                }
                else if (curFragment == 3){
                    Intent i = new Intent("com.example.fathimamulaffer.zengames.CW_DONE");
                    sendBroadcast(i);
                }
            }
        });
        /**********************************/
        //keep reference to activity context
        MyApplication myApplication = (MyApplication) this.getApplicationContext();
        myApplication.descriptionActivity = this;
        //get deets from intent
        Intent intent = getIntent();
        String option = intent.getStringExtra("option");
        selectFragment(option);

    }
    //This is called by MyReceiver. disables start option for the current fragment view
    public void disableFragment(){
        FragmentManager fm = getSupportFragmentManager();
        if (curFragment == 1){
            ChillOutFragment f = (ChillOutFragment) fm.findFragmentById(R.id.fragment_container2);
            f.disableStart();
        }
        else if (curFragment == 2){
            GIFragment f = (GIFragment) fm.findFragmentById(R.id.fragment_container2);
            f.disableStart();
        }
        else if (curFragment == 3){
            CWFragment f = (CWFragment) fm.findFragmentById(R.id.fragment_container2);
            f.disableStart();
        }

    }

    //Depending on what option was sent with the calling intent (from main activity)  displays the appropriate fragment
    public void selectFragment(String option){
        Fragment fragment = null;
        Class fragmentClass;
        if ("CO".equals(option)){
            curFragment = 1;
            fragmentClass = ChillOutFragment.class;

        }
        else if ("GI".equals(option)){
            curFragment = 2;
            fragmentClass = GIFragment.class;
        }
        else if ("CW".equals(option)){
            curFragment = 3;
            fragmentClass = CWFragment.class;
        }
        else{
            //def
            Log.i("option","else sttmt");
            fragmentClass = ChillOutFragment.class;
        }
        try{
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container2,fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    //if start button in fragment is selected - then it sends a msg to this (its parent activity)
    @Override
    public void onFragmentMsg(String TAG) {
        Logging log = new Logging(this);
        Long time = (Long) System.currentTimeMillis();
        if (TAG == "CO Started"){
            String event = "chilloutStarted";
            log.writeLog(event,time);
            Intent intent = getPackageManager().getLaunchIntentForPackage(getResources().getString(R.string.game1_package_name));
            startActivity(intent);
        }
        else if (TAG == "GI Started"){
            String event = "guidedimageryStarted";
            log.writeLog(event,time);
            Intent intent = getPackageManager().getLaunchIntentForPackage(getResources().getString(R.string.game2_package_name));
            startActivity(intent);
        }
        else if (TAG == "CW Started"){
            String event = "colorwordStarted";
            log.writeLog(event,time);
            Intent intent = getPackageManager().getLaunchIntentForPackage(getResources().getString(R.string.game3_package_name));
            startActivity(intent);
        }


    }


}
