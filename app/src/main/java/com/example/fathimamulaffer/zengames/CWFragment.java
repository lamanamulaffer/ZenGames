package com.example.fathimamulaffer.zengames;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CWFragment extends Fragment {
    TextView start;
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
                    "must implement OnFragmentListener");
        }
    }

    //Disables start button
    public void disableStart(){
        start.setClickable(false);
        start.setBackgroundColor(getResources().getColor(R.color.grey));
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.cw_fragment, parent,false);
        //Obtain ref to start & set listener
        start = (TextView) view.findViewById(R.id.cw_start);
        start.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onFragmentListener.onFragmentMsg("CW Started");
            }
        });
        return view ;
    }
}
