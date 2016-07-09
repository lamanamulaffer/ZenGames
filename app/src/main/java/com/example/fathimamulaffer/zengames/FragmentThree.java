package com.example.fathimamulaffer.zengames;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//Fragment that has chillout + colour word + survey
public class FragmentThree extends Fragment implements View.OnClickListener{
    TextView chillout;
    TextView colorword;
    TextView survey;
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
        View view = inflater.inflate(R.layout.fragment3, parent,false);
        chillout = (TextView) view.findViewById(R.id.f3chillout);
        chillout.setOnClickListener(this);
        colorword = (TextView) view.findViewById(R.id.colorword);
        colorword.setOnClickListener(this);
        survey = (TextView) view.findViewById(R.id.survey);
        survey.setOnClickListener(this);
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

    //disable color word
    public  void disableCW(){
        colorword.setClickable(false);
        colorword.setBackgroundColor(getResources().getColor(R.color.grey));
    }

    //disable survey
    public  void disablesurvey(){
        survey.setClickable(false);
        survey.setBackgroundColor(getResources().getColor(R.color.grey));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.f3chillout:
                onFragmentListener.onFragmentMsg("CO Selected");
                break;
            case R.id.colorword:
                onFragmentListener.onFragmentMsg("CW Selected");
                break;
            case R.id.survey:
                onFragmentListener.onFragmentMsg("Survey Selected");
                break;
        }
    }
}
