package com.example.fathimamulaffer.zengames;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Log files in directory data/sdcard/zengames_logs
 * 1 file for each user: userid_log.txt
 */
public class Logging {
    private SharedPreference sp;
    private Context context;
    private String file_suffix =  "_log.txt";
    private String file_dir = "/zengames_logs/";
    public Logging(Context context){
        this.context = context;
    }

    public void writeLog(String event, Long ts){
        sp = new SharedPreference();
        String filename = sp.getUserID(context) + file_suffix;
        String state = Environment.getExternalStorageState();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ts);
        String timestamp = format.format(calendar.getTime());
        //if SD card is available to write
        if (Environment.MEDIA_MOUNTED.equals(state)){
            String basedir = Environment.getExternalStorageDirectory().getAbsolutePath();
            File dir = new File(basedir + file_dir);
            dir.mkdir();
            File file = new File(dir,filename);
            try{
                FileWriter fileWriter = new FileWriter(file,true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write(timestamp + " | " + event + "\n");
                bufferedWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

}
