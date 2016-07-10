package com.example.fathimamulaffer.zengames;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/*
* Updates day count. Sends broadcast which is handled by MyReceiver
* */
public class AlarmReceiver extends BroadcastReceiver{
    SharedPreference sp;
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context,"day count updated",Toast.LENGTH_SHORT).show();
        sp = new SharedPreference();
        Integer day_count = sp.getDayCount(context);
        day_count = day_count + 1;
        sp.setDayCount(context, day_count);
        Intent i = new Intent("com.example.fathimamulaffer.zengames.USER_ACTION");
        context.sendBroadcast(i);

    }

}
