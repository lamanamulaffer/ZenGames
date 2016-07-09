package com.example.fathimamulaffer.zengames;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ChillOutFragment extends Fragment {
    /*view to start playing chill out */
    TextView start;
    /*setting up listener to allow fragment to talk to activity */
    //OnFragmentListener activityCallback;
    private OnFragmentListener  onFragmentListener;
    public void setOnFragmentListener(OnFragmentListener listener){
        onFragmentListener = listener;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            onFragmentListener = (OnFragmentListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+
                    "must implement chilloutFragmentlistener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.co_fragment, parent,false);
        //Obtain ref to start & set listener
        start = (TextView) view.findViewById(R.id.chillout_start);
        start.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onFragmentListener.onFragmentMsg("CO Started");
            }
        });
        return view ;
    }

    //Disables start button
    public void disableStart(){
        start.setClickable(false);
        start.setBackgroundColor(getResources().getColor(R.color.grey));
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        //set up any handles here

    }
}
