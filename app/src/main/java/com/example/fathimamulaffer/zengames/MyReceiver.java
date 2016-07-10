package com.example.fathimamulaffer.zengames;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

//This intent has several filters registered to it
//It checks which filter called it - and takes action accordingly
public class MyReceiver  extends BroadcastReceiver{
    private String daycount_action = "com.example.fathimamulaffer.zengames.USER_ACTION";
    private String co_action = "com.example.fathimamulaffer.zengames.CO_DONE";
    private String gi_action = "com.example.fathimamulaffer.zengames.GI_DONE";
    private String cw_action = "com.example.fathimamulaffer.zengames.CW_DONE";
    private String survey_action = "com.example.fathimamulaffer.zengames.Survey_DONE";

    SharedPreference sp = new SharedPreference();
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreference sp = new SharedPreference();
        MainActivity mainActivity = ((MyApplication) context.getApplicationContext()).mainActivity;
        DescriptionActivity descriptionActivity = ((MyApplication) context.getApplicationContext()).descriptionActivity;
        if (intent.getAction().equals(daycount_action)){
            Toast.makeText(context,"day_action",Toast.LENGTH_SHORT).show();
            Integer day_count = sp.getDayCount(context);
            mainActivity.dayCount.setText("Day "+ day_count + "/21");
            mainActivity.selectF(); //selects acc to new model
        }
        else if (intent.getAction().equals(co_action)){
            Toast.makeText(context,"co_action",Toast.LENGTH_SHORT).show();
            mainActivity.commF("CO");
            descriptionActivity.disableFragment(); //disable app frag
        }
        else if (intent.getAction().equals(gi_action)){
            Toast.makeText(context,"gi_action",Toast.LENGTH_SHORT).show();
            mainActivity.commF("GI");//talks to F to disable button
            descriptionActivity.disableFragment(); //disable app frag
        }
        else if (intent.getAction().equals(cw_action)){
            Toast.makeText(context,"cw_action",Toast.LENGTH_SHORT).show();
            mainActivity.commF("CW");
            descriptionActivity.disableFragment(); //disable app frag
        }
        else if (survey_action.equals(intent.getAction())){
            Toast.makeText(context,"survey_action",Toast.LENGTH_SHORT).show();
            mainActivity.commF("S");

        }


    }
}
