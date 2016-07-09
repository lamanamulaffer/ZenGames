package com.example.fathimamulaffer.zengames;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/* This class inflates fragment1 - only chill out */
public class FragmentOne extends Fragment  implements View.OnClickListener{
    /*if chillout is pressed */
    TextView chillout;
    private OnFragmentListener onFragmentListener;

    public void setOnFragmentListener(OnFragmentListener listener) {
        onFragmentListener = listener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onFragmentListener = (OnFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement OnFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, parent, false);
        chillout = (TextView) view.findViewById(R.id.f1chillout);
        chillout.setOnClickListener(this);
        return view;
    }
    //disables chillout button
    public  void disableChillout(){
        chillout.setClickable(false);
        chillout.setBackgroundColor(getResources().getColor(R.color.grey));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //set up any handles here
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.f1chillout:
                //chillout button is clicked
                onFragmentListener.onFragmentMsg("CO Selected");
        }
    }
}


