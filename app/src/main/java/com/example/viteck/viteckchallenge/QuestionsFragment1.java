package com.example.viteck.viteckchallenge;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Eric on 12/2/2017.
 */

public class QuestionsFragment1 extends Fragment {
    private Spinner state;
    private Spinner city;
    private EditText income;
    private Button doneButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        final View rootview = inflater.inflate(R.layout.questions, container, false);

        state = rootview.findViewById(R.id.personState);
        city = rootview.findViewById(R.id.personCity);
        income = rootview.findViewById(R.id.annualIncome);
        doneButton = rootview.findViewById(R.id.doneButton);
        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context ctx) {
        //This method is only called on API >= 23
        super.onAttach(ctx);
    }

    @Override
    public void onAttach(Activity ctx) {
        //This method is only called on API < 23. THIS IS NECESSARY.
        super.onAttach(ctx);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
