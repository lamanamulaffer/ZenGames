package com.example.fathimamulaffer.zengames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*
* the userID and bluetooth address is saved in the sp file
* sp - intialization is updated
* Go to Main activity page  */
public class Initialization extends AppCompatActivity implements View.OnClickListener {
    private EditText userID, bluetoothAddr;
    Button save;
    private SharedPreference sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialization);

        sp = new SharedPreference();
        userID = (EditText) findViewById(R.id.uname);
        bluetoothAddr = (EditText) findViewById(R.id.baddr);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                String uID = userID.getText().toString();
                String bAddr = bluetoothAddr.getText().toString();
                sp.setUserID(this,uID);
                sp.setbluetoothAddress(this,bAddr);
                sp.setInitialize(this,1);
                //log event
                Logging log = new Logging(this);
                String event = "savedUserDetails";
                Long time = (Long) System.currentTimeMillis();
                log.writeLog(event,time);
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
        }
    }
}
