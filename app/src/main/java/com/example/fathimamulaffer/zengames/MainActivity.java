package com.example.fathimamulaffer.zengames;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/*
**  This Activity handles switching between main fragment views (FragmentOne, FragmentTwo, FragmentThree)
        *   FragmentOne - only chillout.
        *   FragmentTwo - chillout&guidedImagery.
        *   FragmentThree - chillout, colorword,survery
 *  Fragment switch happens when setDayAlarm is fired.
 *  This sends a broadcast that is recvd by MyReceiver which then handles the fragment view change
 *  Fires notification alarm
 * */
public class MainActivity extends AppCompatActivity
        implements OnFragmentListener, View.OnClickListener{

    private SharedPreference sp;
    //day count text view
    TextView dayCount;
    //Handles event once day alarm is fired - in here we change the fragment views
    MyReceiver myReceiver;
    IntentFilter intentFilter1, intentFilter2, intentFilter3, intentFilter4, intentFilter5;
    //keeps track of which fragment is in use
    private Integer curFragment;
    //keeps track of which option is selected in the cur fragment
    private String selected;

    //request code for notification alarm
    public final int NOTIFICATION_TIMER = 11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        sp = new SharedPreference();
        Integer initVal = sp.getInitialize(this);
        //first time access
        if (initVal == 0){
            Intent intent = new Intent(this, Initialization.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        //returning access
        else{
            setContentView(R.layout.activity_main_fab);
            //Initializing elements in the main activity layout file
            Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
            setSupportActionBar(my_toolbar);
            dayCount = (TextView) findViewById(R.id.daycount);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(this);
            curFragment = 0; //def value
            selected = "";
            //keep reference to activity context
            MyApplication myApplication = (MyApplication) this.getApplicationContext();
            myApplication.mainActivity = this;
            //create instance of MyReceiver + Intent Filter
            myReceiver = new MyReceiver();
            //register receiver for 2 diff filters
            //1 is for daycount change. 2 is for co_done/gi_done/cw_done
            intentFilter1 = new IntentFilter("com.example.fathimamulaffer.zengames.USER_ACTION");
            intentFilter2 = new IntentFilter("com.example.fathimamulaffer.zengames.CO_DONE");
            intentFilter3 = new IntentFilter("com.example.fathimamulaffer.zengames.GI_DONE");
            intentFilter4 = new IntentFilter("com.example.fathimamulaffer.zengames.CW_DONE");
            intentFilter5 = new IntentFilter("com.example.fathimamulaffer.zengames.Survey_DONE");
            dayCount.setText("Day " + sp.getDayCount(this) + "/21");
            //Alarm that fires every day to change day count
            // which then triggers change in fragment view [Frag1, Frag2 or Frag3]
            setDayAlarm();
            setNotificationAlarm(NOTIFICATION_TIMER);
            selectFragment();
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        Intent i = getIntent();
        String k = i.getStringExtra("Origin");
        if ("NotificationAlarmReceiver".equals(k)){//we r here bcuz of noti click
            Logging logging = new Logging(this);
            String event = "Notification Click";
            Long time = (Long) System.currentTimeMillis();
            logging.writeLog(event,time);
        }
    }
    //this is necessary in order to get pending intent to work for notification
    //used for logging
    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);
    }
    //This is called by MyReceiver.
    // Depending on what option in fragment selected - disables this option
    public void disableOption(String option){
        FragmentManager fm = getSupportFragmentManager();
        if (curFragment == 1){//frag 1 view
            if ("CO".equals(option)){
                FragmentOne f = (FragmentOne)fm.findFragmentById(R.id.fragment_container);
                f.disableChillout();
            }
        }
        else if (curFragment == 2){//frag2 view
            FragmentTwo f = (FragmentTwo)fm.findFragmentById(R.id.fragment_container);
            if ("CO".equals(option)){//chillout option is selected
                f.disableChillout();
            }
            else if ("GI".equals(option)){
                f.disableGI();
            }
        }
        else if (curFragment == 3){
            FragmentThree f = (FragmentThree)fm.findFragmentById(R.id.fragment_container);
            if ("CO".equals(option)){
                f.disableChillout();
            }
            else if ("CW".equals(option)){
                f.disableCW();
            }
            else if ("Survey".equals(option)){ //u can use survey also
                f.disablesurvey();
            }
        }
    }

    //chooses which fragment to showcase depending on the day count
    //3rd day of the week [where week begins from experiment start day] is chillout + guided imagery
    //7th day of the week is chillout + colour word + survey
    //rest of the days - only chillout
    public void selectFragment(){
        Fragment fragment = null;
        Class fragmentClass;
        Integer dayCount = sp.getDayCount(this);
        if ((dayCount % 7) == 0){
            curFragment = 3;
            fragmentClass = FragmentThree.class;
        }
        else if ((dayCount % 7) == 3){
            curFragment = 2;
            fragmentClass = FragmentTwo.class;
        }
        else{
            curFragment = 1;
            fragmentClass = FragmentOne.class;
        }
        try{
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //setting up fragment details
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    //set an alarm that fires an intent every minute.
    //setup time: 1:11 am
    //alarm calls AlarmReceiver
    private void setDayAlarm(){
        Intent alarmIntent = new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //set alarm - def time: 2016/07 @ 1:11am
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE,11);
        //set alarm for every 3 minutes - actually has to be for every day
        manager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                1000*60*3,pendingIntent);
    }

    //set an alarm that fires an intent every 2 mins for notification
    public void setNotificationAlarm(Integer reqcode){
        SharedPreference sp = new SharedPreference();
        Integer[] time = sp.getNotiTime(this);

        Intent alarmIntent = new Intent(this, NotificationAlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this,reqcode,alarmIntent,0);

        cancelAlarmIfExists(reqcode,alarmIntent);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, time[0]);
        calendar.set(Calendar.MINUTE, time[1]);
        //set for every 1/2 min
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000*30,pi);
    }

    //cancels alarm if it exists
    private void cancelAlarmIfExists(Integer reqcode, Intent i){
        try{
            PendingIntent pi = PendingIntent.getBroadcast(this,reqcode,i,0);
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            am.cancel(pi);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //for every fragment view [FragmentOne : only chillout], [FragmentTwo : chillout + guided Imagery], [FragmentThree : chillout + cw + survey]
    //checks which play button is selected and & calls description activity with the approp. msg
    //description activity showcases the different description fragments based on the recvd msg
    @Override
    public void onFragmentMsg(String TAG) {
        Logging log = new Logging(this);
        Long time = (Long) System.currentTimeMillis();
        if (TAG == "CO Selected"){
            selected = "CO";
            String event = "chilloutSelected";
            log.writeLog(event,time);
            Intent intent = new Intent(this, DescriptionActivity.class);
            intent.putExtra("option","CO");
            startActivity(intent);
        }
        else if (TAG == "GI Selected"){
            selected = "GI";
            String event = "guidedimagerySelected";
            log.writeLog(event,time);
            //here GI option is selected.
            //start intent to desc activity + tag to tell which fragment to display
            Intent intent = new Intent(this, DescriptionActivity.class);
            intent.putExtra("option","GI");
            startActivity(intent);
        }
        else if (TAG == "CW Selected"){
            selected = "CW";
            String event = "colourwordSelected";
            log.writeLog(event,time);
            Intent intent = new Intent(this, DescriptionActivity.class);
            intent.putExtra("option","CW");
            startActivity(intent);
        }
        else if (TAG == "Survey Selected"){
            selected = "Survey";
            String event = "surveySelected";
            log.writeLog(event,time);
            //show survey here
            Intent intent = new Intent(this, SurveyActivity.class);
            startActivity(intent);
        }
    }

    //Handles profile button click
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Logging log = new Logging(this);
                Long time = (Long) System.currentTimeMillis();
                String event = "profileButtonPressed";                log.writeLog(event,time);
                Intent ii = new Intent(this,ProfileActivity.class);
                startActivity(ii);
                break;
        }
    }
}
