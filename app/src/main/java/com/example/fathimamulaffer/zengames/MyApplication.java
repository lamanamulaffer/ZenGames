package com.example.fathimamulaffer.zengames;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

//Maintains context for application
//Makes logs for all the different application callbacks
public class MyApplication extends Application {
    public static Context context;
    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        registerActivityLifecycleCallbacks(new MyActivityLifeCycleCallbacks(context));
    }
    MainActivity mainActivity;
    DescriptionActivity descriptionActivity;
    @Override
    public void onTerminate(){
        super.onTerminate();
    }
    @Override
    public void onConfigurationChanged (Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
    private static final class MyActivityLifeCycleCallbacks
        implements ActivityLifecycleCallbacks{
        private Context context1;
        public MyActivityLifeCycleCallbacks(Context context){
            context1 = context;
        }
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            String event = "onActivityCreated: " + activity.getLocalClassName();
            Long time = (Long) System.currentTimeMillis();
            Logging log = new Logging(context1);
            log.writeLog(event, time);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            String event = "onActivityStarted: " + activity.getLocalClassName();
            Long time = (Long) System.currentTimeMillis();
            Logging log = new Logging(context1);
            log.writeLog(event, time);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            String event = "onActivityResumed: " + activity.getLocalClassName();
            Long time = (Long) System.currentTimeMillis();
            Logging log = new Logging(context1);
            log.writeLog(event, time);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            String event = "onActivityPaused: " + activity.getLocalClassName();
            Long time = (Long) System.currentTimeMillis();
            Logging log = new Logging(context1);
            log.writeLog(event, time);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            String event = "onActivityStopped: " + activity.getLocalClassName();
            Long time = (Long) System.currentTimeMillis();
            Logging log = new Logging(context1);
            log.writeLog(event, time);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            String event = "onActivitySaveInstanceState: " + activity.getLocalClassName();
            Long time = (Long) System.currentTimeMillis();
            Logging log = new Logging(context1);
            log.writeLog(event, time);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            String event = "onActivityDestroyed: " + activity.getLocalClassName();
            Long time = (Long) System.currentTimeMillis();
            Logging log = new Logging(context1);
            log.writeLog(event, time);
        }
    }
}
