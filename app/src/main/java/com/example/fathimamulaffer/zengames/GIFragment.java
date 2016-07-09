package com.example.fathimamulaffer.zengames;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//Fragment that describes GI
public class GIFragment extends Fragment {
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

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.gi_fragment, parent,false);
        //Obtain ref to start & set listener
        start = (TextView) view.findViewById(R.id.gi_start);
        start.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onFragmentListener.onFragmentMsg("GI Started");
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
