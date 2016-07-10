package com.example.fathimamulaffer.zengames;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

//This intent has several filters registered to it
//It checks which filter called it - and takes action accordingly
public class MyReceiver  extends BroadcastReceiver{
    private String daycount_action = "com.example.fathimamulaffer.zengames.USER_ACTION";
    private String co_action = "com.example.fathimamulaffer.zengames.CO_DONE";
    private String gi_action = "com.example.fathimamulaffer.zengames.GI_DONE";
    private String cw_action = "com.example.fathimamulaffer.zengames.CW_DONE";
    private String survey_action = "com.example.fathimamulaffer.zengames.Survey_DONE";
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreference sp = new SharedPreference();
        MainActivity mainActivity = ((MyApplication) context.getApplicationContext()).mainActivity;
        DescriptionActivity descriptionActivity = ((MyApplication) context.getApplicationContext()).descriptionActivity;
        Log.i("SURVEY","4");
        Log.i("SURVEY",intent.getAction());/*
        if (intent.getAction().equals(daycount_action)){
            Integer day_count = sp.getDayCount(context);
            //MainActivity mainActivity = ((MyApplication) context.getApplicationContext()).mainActivity;
            //sets daycount value in toolbar
            mainActivity.dayCount.setText("Day "+ day_count + "/21");
            //select which fragment to show in main activity
            mainActivity.selectFragment();
        }
        else if (intent.getAction().equals(co_action)){
            //make the chillout select & start buttons unclickable
            //reset co key only in day count
            Toast.makeText(context,"co_action",Toast.LENGTH_SHORT).show();
            descriptionActivity.disableFragment(); //disable app frag
            mainActivity.disableOption("CO");
        }
        else if (intent.getAction().equals(gi_action)){
            //make the chillout select & start buttons unclickable
            //reset co key only in day count
            Toast.makeText(context,"gi_action",Toast.LENGTH_SHORT).show();
            descriptionActivity.disableFragment(); //disable app frag
            mainActivity.disableOption("GI");

        }
        else if (intent.getAction().equals(cw_action)){
            //make the chillout select & start buttons unclickable
            //reset co key only in day count
            Toast.makeText(context,"cw_action",Toast.LENGTH_SHORT).show();
            descriptionActivity.disableFragment(); //disable app frag
            mainActivity.disableOption("CW");

        }
        else if ("com.example.fathimamulaffer.zengames.Survey_DONE".equals(intent.getAction())){
            Log.i("SURVEY","5");
            Toast.makeText(context,"survey_action",Toast.LENGTH_SHORT).show();
            descriptionActivity.disableFragment(); //disable app frag
            mainActivity.disableOption("Survey");

        }*/


    }
}
