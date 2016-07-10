package com.example.fathimamulaffer.zengames;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.gesture.Prediction;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/*  ******** Keys in SP file **********
*  "Initialize" : first time access (0), returning access (1)
*  "user_id" - user id (by Abdullah)
*  "blu_addr" - bluetooth address (by Abdullah)
*  "daycount" - day number in experiment
*   ""noti_time_hr", "noti_time_min" - set prefered time for notification. hr & min
*  */
public class SharedPreference {
    public static final String PREF_FILE = "ZenGames_Pref";

    public SharedPreference(){super();}

    //Key: Initialize: 0/1
    public void setInitialize(Context context, Integer initVal){
        SharedPreferences sp;
        SharedPreferences.Editor editor;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt("Initialize",initVal);
        editor.apply();
    }

    public Integer getInitialize(Context context){
        SharedPreferences sp;
        Integer initVal;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        initVal = sp.getInt("Initialize",0); //def 0
        return initVal;
    }

    //Key: user_id & blu_addr
    public void setUserID(Context context,String userID){
        SharedPreferences sp;
        SharedPreferences.Editor editor;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("user_id",userID);
        editor.apply();
    }
    public static void setbluetoothAddress(Context context,String bluAddr){
        SharedPreferences sp;
        SharedPreferences.Editor editor;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("blu_addr",bluAddr);
        editor.apply();
    }
    public String getUserID(Context context){
        SharedPreferences sp;
        String userID;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        userID = sp.getString("user_id","");
        return userID;
    }
    public String getBluetoothAddress(Context context){
        SharedPreferences sp;
        String bluAddr;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        bluAddr = sp.getString("blu_addr","");
        return bluAddr;
    }

    //key: daycount - keeps track of the days of the experiment
    public void setDayCount(Context context, Integer dayCount){
        SharedPreferences sp;
        SharedPreferences.Editor editor;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt("daycount",dayCount);
        editor.apply();
    }
    public Integer getDayCount(Context context){
        SharedPreferences sp;
        Integer dayCount;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        dayCount = sp.getInt("daycount",0); //default 0 since notification will run immediately
        return dayCount;
    }

    //key: noti_time_hr & noti_time_min - prefered time to fire reminder notification
    public void setNotiTime(Context context, Integer hr, Integer min){
        SharedPreferences sp;
        SharedPreferences.Editor editor;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt("noti_time_hr",hr);
        editor.putInt("noti_time_min",min);
        editor.apply();
    }
    //returns an array [hr,min]
    public Integer[] getNotiTime(Context context){
        SharedPreferences sp;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        //def time: 1:11 am
        Integer hr = sp.getInt("noti_time_hr",1);
        Integer min = sp.getInt("noti_time_min",11);
        Integer[] time = new Integer[]{hr,min};
        return time;
    }

    public void setWorkStack(Context context, String[] stackarray){
        SharedPreferences sp;
        SharedPreferences.Editor editor;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        editor = sp.edit();
        String s = TextUtils.join(",",stackarray); //array: [a,b,c] -> a,b,c
        editor.putString("ws",s);
        editor.apply();
    }
    public String[] getWorkStack(Context context){
        SharedPreferences sp;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        String s = sp.getString("ws","");
        return s.split(",");
    }

    public void setRollOver(Context context, Integer r){
        SharedPreferences sp;
        SharedPreferences.Editor editor;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt("rollover",r);
        editor.apply();
    }
    public Integer getRollOver(Context context){
        SharedPreferences sp;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return sp.getInt("rollover",0);
    }

    public void setCurFragment(Context context, Integer c){
        SharedPreferences sp;
        SharedPreferences.Editor editor;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt("cur_frag",c);
        editor.apply();
    }
    public Integer getCurFragment(Context context){
        SharedPreferences sp;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return sp.getInt("cur_frag",0);  //default
    }

    public void setCWNum(Context context, Integer i){
        SharedPreferences sp;
        SharedPreferences.Editor editor;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt("CNum",i);
        editor.apply();
    }
    public Integer getCWNum(Context context){
        SharedPreferences sp;
        sp = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return sp.getInt("CNum",0);
    }
}
