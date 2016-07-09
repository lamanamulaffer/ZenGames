package com.example.fathimamulaffer.zengames;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

//Fragment that opens up when user wants to pick a time for notification
public class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener{
    private SharedPreference sp;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        sp = new SharedPreference();
        //default values
        Context context = getActivity();
        Integer[] time = sp.getNotiTime(context);
        return new TimePickerDialog(context,this,time[0],time[1],true);

    }
    //callback method
    //update notification prefered timing
    //cancel prev alarm and set new notification alarm
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        sp = new SharedPreference();
        Context context = getActivity();
        sp.setNotiTime(context,hourOfDay,minute);
        MainActivity mainActivity = ((MyApplication) context.getApplicationContext()).mainActivity;
        //sets new noti alarm (cancels the one before)
        mainActivity.setNotificationAlarm(mainActivity.NOTIFICATION_TIMER);

    }
}
