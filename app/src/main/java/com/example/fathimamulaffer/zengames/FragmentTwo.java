package com.example.fathimamulaffer.zengames;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//When someone clicks guided imagery - then this fragment shud notify the parent app - mainactvity
//main activity will then call desc activity - which shud showcase the guided imagery fragment
public class FragmentTwo extends Fragment implements View.OnClickListener {
    TextView chillout;
    /* If guided imagery is pressed */
    TextView guidedImagery;
    // Setting up listener for this fragment - to talk to parent activity
    private OnFragmentListener onFragmentListener;

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
                    "must implement Fragment Two Listener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment2, parent,false);
        chillout = (TextView) view.findViewById(R.id.f2chillout) ;
        chillout.setOnClickListener(this);
        guidedImagery = (TextView) view.findViewById(R.id.guidedImagery);
        guidedImagery.setOnClickListener(this);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        //set up any handles here
    }

    //disable chillout
    public  void disableChillout(){
        chillout.setClickable(false);
        chillout.setBackgroundColor(getResources().getColor(R.color.grey));
    }

    //disable guided imagery
    public  void disableGI(){
        guidedImagery.setClickable(false);
        guidedImagery.setBackgroundColor(getResources().getColor(R.color.grey));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.f2chillout:
                onFragmentListener.onFragmentMsg("CO Selected");
                break;
            case R.id.guidedImagery:
                onFragmentListener.onFragmentMsg("GI Selected");
                break;
        }
    }
}
