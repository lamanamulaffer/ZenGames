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
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
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
    private String[] workstack;
    Context context;
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
            //Log.i("TEST","1.1");
            setNotificationAlarm(NOTIFICATION_TIMER);
            context = this;
            //Log.i("TEST","1.2");
            //selectFragment();
            selectF();
            //Log.i("TEST","1.3");
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
        Log.i("INHERE","in here");
        setIntent(intent);
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

    //updates rollover, work stack, cur fragment, sets up relevant frag
    public void selectF(){
        String f1,f2,f3,f4,f5,f6;
        f1 = "CO"; f2="GI,CW1,CO,CW2"; f3="GI,CW1,CO,S";f4="CW,CO,S";f5="CO,S";f6="S";
        Log.i("TEST","2.1");
        Integer day = sp.getDayCount(this);
        Integer rollover = sp.getRollOver(this);
        String[] workstack = sp.getWorkStack(this);
        String ws = TextUtils.join(",",workstack);
        Fragment fragment = null;
        Class fragmentClass;
        fragmentClass = getFragmentByWorkStack();
        //fragmentClass = F6.class;
        /*
        Log.i("TESTING","-------------");
        Log.i("TESTING","DAY: " + day);
        Log.i("TESTING","CUR FRAG" + sp.getCurFragment(this));
        Log.i("TESTING","WS: "+ Arrays.toString(sp.getWorkStack(this)));
        Log.i("TESTING","ROLLOVER" + rollover);
        Log.i("TESTING","---------------");*/
        try{
            //Log.i("TEST","2.3");
            fragment = (Fragment) fragmentClass.newInstance();
            //Log.i("TEST","2.4");
        } catch (Exception e) {
            //Log.i("TEST","2.5");
            e.printStackTrace();
        }
        //setting up fragment details
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
    private Class getFragmentByWorkStack(){
        String f1,f2,f3,f4,f5,f6,f0;
        f0="";f1 = "CO"; f2="GI,CW1,CO,CW2"; f3="GI,CW1,CO,S";f4="CW,CO,S";f5="CO,S";f6="S";
        Integer day = sp.getDayCount(this);
        Integer rollover = sp.getRollOver(this);
        String[] workstack = sp.getWorkStack(this);
        String ws = TextUtils.join(",",workstack);
        Class fragmentClass=null;
        if (ws.equals(f1)){
            fragmentClass = getFragmentByDay();
        }//work stack remains the same
        else if (ws.equals(f2)){
            if (rollover <3){
                fragmentClass=F2.class;sp.setRollOver(this,rollover+1);sp.setCurFragment(this,2);
            }
            else {fragmentClass = getFragmentByDay();}
        }
        else if (ws.equals(f3)){
            if (rollover<3){
                fragmentClass=F3.class;sp.setRollOver(this,rollover+1);sp.setCurFragment(this,3);
            }
            else{fragmentClass = getFragmentByDay();}
        }
        else if (ws.equals(f4)){
            if (rollover<3){
                fragmentClass=F4.class;sp.setRollOver(this,rollover+1);sp.setCurFragment(this,4);
            }
            else{fragmentClass = getFragmentByDay();}
        }
        else if (ws.equals(f5)){
            if(rollover<3){
               fragmentClass = F5.class;sp.setRollOver(this,rollover+1);sp.setCurFragment(this,5);
            }
            else{fragmentClass = getFragmentByDay();}
        }
        else if (ws.equals(f6)){
            if(rollover<3){
                fragmentClass = F6.class;sp.setRollOver(this,rollover+1);sp.setCurFragment(this,6);
            }
            else{fragmentClass = getFragmentByDay();}
        }
        else if (ws.equals(f0)){fragmentClass=getFragmentByDay();
        }
        return fragmentClass;
    }
    private Class getFragmentByDay(){
        String f1,f2,f3,f4,f5,f6;
        f1 = "CO"; f2="GI,CW1,CO,CW2"; f3="GI,CW1,CO,S";f4="CW,CO,S";f5="CO,S";f6="S";
        Integer day = sp.getDayCount(this);
        Integer rollover = sp.getRollOver(this);
        String[] workstack = sp.getWorkStack(this);
        Class fragmentClass;
        if (day==0){fragmentClass = F0.class;sp.setWorkStack(this,"".split(","));sp.setRollOver(this,0);sp.setCurFragment(this,0);}
        else if (day==1){fragmentClass = F2.class; sp.setWorkStack(this,f2.split(","));sp.setRollOver(this,0);sp.setCurFragment(this,2);}
        else if ((day==7) || (day==14) || (day==21)){fragmentClass = F3.class;sp.setWorkStack(this,f3.split(","));sp.setRollOver(this,0);sp.setCurFragment(this,3);}
        else if (day > 21){fragmentClass = F7.class;sp.setWorkStack(this,"".split(","));sp.setRollOver(this,0);sp.setCurFragment(this,7);}//this will give an empty work stack
        else{fragmentClass = F1.class;sp.setWorkStack(this,f1.split(","));sp.setRollOver(this,0);sp.setCurFragment(this,1);}
        return fragmentClass;
    }

    public void commF(String code) {
        FragmentManager fm = getSupportFragmentManager();
        if (sp.getCurFragment(this) == 1) {
            F1 f = (F1) fm.findFragmentById(R.id.fragment_container);
            if ("CO".equals(code)) {
                f.disableCO();
            }
        } else if (sp.getCurFragment(this) == 2) {
            F2 f = (F2) fm.findFragmentById(R.id.fragment_container);
            if ("GI".equals(code)) {
                f.disableGI();
            } else if ("CO".equals(code)) {
                f.disableCO();
            } else if ("CW".equals(code)) {
                if (sp.getCWNum(this) == 0) {
                    f.disableCW1();
                    sp.setCWNum(this, 1);
                } else if (sp.getCWNum(this) == 1) {
                    f.disableCW2();
                    sp.setCWNum(this, 0);
                }
            }
        } else if (sp.getCurFragment(this) == 3) {
            F3 f3 = (F3) fm.findFragmentById(R.id.fragment_container);
            if ("GI".equals(code)) {
                f3.disableGI();
            } else if ("CO".equals(code)) {
                f3.disableCO();
            } else if ("CW".equals(code)) {
                f3.disableCW1();
            } else if ("S".equals(code)) {
                f3.disableS();
            }
        } else if (sp.getCurFragment(this) == 4) {
            F4 f = (F4) fm.findFragmentById(R.id.fragment_container);
            if ("CO".equals(code)) {
                f.disableCO();
            } else if ("CW".equals(code)) {
                f.disableCW1();
            } else if ("S".equals(code)) {
                f.disableS();
            }
        } else if (sp.getCurFragment(this) == 5) {
            F5 f = (F5) fm.findFragmentById(R.id.fragment_container);
            if ("CO".equals(code)) {
                f.disableCO();
            } else if ("S".equals(code)) {
                f.disableS();
            }
        } else if (sp.getCurFragment(this) == 6) {
            F6 f = (F6) fm.findFragmentById(R.id.fragment_container);
            if ("S".equals(code)) {
                f.disableS();
            }
        }

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
                1000*30,pendingIntent);
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
        //Log.i("TAG",TAG);
        FragmentManager fm = getSupportFragmentManager();
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
                String event = "profileButtonPressed";
                log.writeLog(event,time);
                Intent ii = new Intent(this,ProfileActivity.class);
                startActivity(ii);
                break;
        }
    }
}
