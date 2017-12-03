package com.example.viteck.viteckchallenge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * Created by Eric on 12/2/2017.
 */

public class UserQuestions extends Activity implements VerticalStepperForm {
    private VerticalStepperFormLayout verticalStepperForm;

    private Spinner state;
    private Spinner city;
    private EditText age;
    private RadioGroup sex;
    private EditText height;
    private EditText weight;
    private EditText income;
    private RadioGroup employment;
    private RadioGroup maritialStatus;
    private RadioGroup smoker;
    private EditText optionallyInsured;
    private EditText peopleCovered;
    private LinearLayout conditionsLLayout;
    private EditText conditionsInput;
    private Spinner conditionsSpinner;
    private ImageButton submitConditionsButton;
    private ListView priorConditions;
    private ArrayList<String> conditionsList = new ArrayList<>();

    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions);

        String[] mySteps = {"State", "City", "Age", "Sex", "Height (in)", "Weight (lbs)", "Annual Income", "Employment", "Maritial Status", "Smoker", "Optional Insured", "People Covered", "Preconditions"};
        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        // Finding the view

        verticalStepperForm = findViewById(R.id.vertical_stepper_form);

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, this)
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(false) // Defaults to true
                .init();
    }

    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;
        switch (stepNumber) {
            case 0:
//                Intent i = new Intent(this, MainActivity.class);
//                startActivity(i);
                view = createStateStep();
                break;
            case 1:
                //view = createPreconditionsStep();
                view = createCityStep();
                break;
            case 2:
                view = createAgeStep();
                break;
            case 3:
                view = createSexStep();

                break;
            case 4:
                view = createHeightStep();
                break;
            case 5:
                view = createWeightStep();
                break;
            case 6:
                view = createIncomeStep();
                break;
            case 7:
                view = createEmploymentStep();
                break;
            case 8:
                view = createMaritialStatusStep();
                break;
            case 9:
                view = createSmokerStep();
                break;
            case 10:
                view = createOptionalInsuredStep();
                break;
            case 11:
                view = createPeopleCoveredStep();
                break;
            case 12:
                view = createPreconditionsStep();
                break;
            default:
                break;
        }
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber) {
            case 0:
                //First selection is selected by default
                verticalStepperForm.setActiveStepAsCompleted();
                break;
            case 1:
                //First selection is selected by default
                verticalStepperForm.setActiveStepAsCompleted();
                break;
            case 2:
                checkAge();
                break;
            case 3:
                checkSex();
                break;
            case 4:
                checkHeight();
                break;
            case 5:
                checkWeight();
                break;
            case 6:
                checkIncome();
                break;
            case 7:
                checkEmployment();
                break;
            case 8:
                checkMaritialStatus();
                break;
            case 9:
                checkIfSmoker();
                break;
            case 10:
                checkOptionallyInsured();
                break;
            case 11:
                checkPeopleCovered();
                break;
            case 12:
                verticalStepperForm.setActiveStepAsCompleted();
                break;
            case 13:
                //Confirmation

                break;
            default:
                break;
        }
    }

    @Override
    public void sendData() {
        /*state;
        //city;
        age;
        sex;
        height;
        weight;
        income;
        //employment;
        maritialStatus;
        smoker;
        optionallyInsured;
        peopleCovered;
        conditionsList;*/
    }

    private View createStateStep() {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        state = spinnerLayout.findViewById(R.id.personState);
        ((ViewGroup) state.getParent()).removeView(state);
        return state;
    }

    private View createCityStep() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        city = spinnerLayout.findViewById(R.id.personState);
        ((ViewGroup) city.getParent()).removeView(city);
        return city;
    }

    private View createAgeStep() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        age = spinnerLayout.findViewById(R.id.personAge);
        ((ViewGroup) age.getParent()).removeView(age);
        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAge();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        return age;
    }

    private View createSexStep() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        sex = spinnerLayout.findViewById(R.id.genderGroup);
        ((ViewGroup) sex.getParent()).removeView(sex);
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                checkSex();
                sex.setOnCheckedChangeListener(null);
            }
        });
        return sex;
    }

    private View createHeightStep() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        height = spinnerLayout.findViewById(R.id.personHeight);
        ((ViewGroup) height.getParent()).removeView(height);
        height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkHeight();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        return height;
    }

    private View createWeightStep() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        weight = spinnerLayout.findViewById(R.id.personWeight);
        ((ViewGroup) weight.getParent()).removeView(weight);
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkWeight();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        return weight;
    }

    private View createIncomeStep() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        income = spinnerLayout.findViewById(R.id.annualIncome);
        ((ViewGroup) income.getParent()).removeView(income);
        income.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String incomeString = income.getText().toString();
                if(!incomeString.startsWith("$")){
                    income.setText("$" + incomeString.replace('$', '\u0000'));
                }
                checkIncome();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        return income;
    }

    private View createEmploymentStep() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        employment = spinnerLayout.findViewById(R.id.employementGroup);
        ((ViewGroup) employment.getParent()).removeView(employment);
        employment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                checkEmployment();
                employment.setOnCheckedChangeListener(null);
            }
        });
        return employment;
    }

    private View createMaritialStatusStep() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        maritialStatus = spinnerLayout.findViewById(R.id.maritialGroup);
        ((ViewGroup) maritialStatus.getParent()).removeView(maritialStatus);
        maritialStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                checkMaritialStatus();
                maritialStatus.setOnCheckedChangeListener(null);
            }
        });
        return maritialStatus;
    }

    private View createSmokerStep() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        smoker = spinnerLayout.findViewById(R.id.smokerGroup);
        ((ViewGroup) smoker.getParent()).removeView(smoker);
        smoker.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                checkIfSmoker();
                smoker.setOnCheckedChangeListener(null);
            }
        });
        return smoker;
    }

    private View createOptionalInsuredStep() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        optionallyInsured = spinnerLayout.findViewById(R.id.optionallyInsured);
        ((ViewGroup) optionallyInsured.getParent()).removeView(optionallyInsured);
        optionallyInsured.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String optString = optionallyInsured.getText().toString();
                if(!optString.startsWith("$")){
                    optionallyInsured.setText("$" + optString.replace('$', '\u0000'));
                }
                checkOptionallyInsured();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        return optionallyInsured;
    }

    private View createPeopleCoveredStep() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        peopleCovered = spinnerLayout.findViewById(R.id.peopleCovered);
        ((ViewGroup) peopleCovered.getParent()).removeView(peopleCovered);
        peopleCovered.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkPeopleCovered();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        return peopleCovered;
    }

    private View createPreconditionsStep() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        conditionsLLayout = spinnerLayout.findViewById(R.id.conditionsLLayout);
        priorConditions = spinnerLayout.findViewById(R.id.preexistingConditionsList);
        conditionsInput = spinnerLayout.findViewById(R.id.conditionsInput);
        conditionsSpinner = spinnerLayout.findViewById(R.id.conditionsSpinner);
        submitConditionsButton = spinnerLayout.findViewById(R.id.submitConditionButton);

        if(((ViewGroup) conditionsLLayout.getParent()) == null){
            int i = 0;
        }else {
            ((ViewGroup) conditionsLLayout.getParent()).removeView(conditionsLLayout);
        }
        if(conditionsLLayout == null){
            int i =0;
        }

        priorConditions.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, conditionsList));

        submitConditionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String conditionsText = conditionsInput.getText().toString();
                if(!conditionsText.equals("")){
                    conditionsList.add(conditionsText);
                    priorConditions.setAdapter(new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_expandable_list_item_1, conditionsList));
                    conditionsInput.setText("");
                    conditionsSpinner.setPromptId(0);



                }
            }
        });

        return conditionsLLayout;
    }

    private void checkAge(){
        if(!age.getText().toString().equals("")) {
            verticalStepperForm.setStepAsCompleted(2);
        }else{
            verticalStepperForm.setStepAsUncompleted(2, "Must Enter An Age");
        }
    }

    private void checkSex(){
        if(sex.getCheckedRadioButtonId() != -1){
            verticalStepperForm.setStepAsCompleted(3);
        }else {
            verticalStepperForm.setStepAsUncompleted(3, "Must Select A Value");
        }
    }
    private void checkHeight(){
        if(!height.getText().toString().equals("")) {
            verticalStepperForm.setStepAsCompleted(4);
        }else{
            verticalStepperForm.setStepAsUncompleted(4, "Must Enter A Height");
        }
    }
    private void checkWeight(){
        if(!weight.getText().toString().equals("")) {
            verticalStepperForm.setStepAsCompleted(5);
        }else{
            verticalStepperForm.setStepAsUncompleted(5, "Must Enter An Weight");
        }
    }
    private void checkIncome(){
        if(!income.getText().toString().equals("$")) {
            verticalStepperForm.setStepAsCompleted(6);
        }else{
            verticalStepperForm.setStepAsUncompleted(6, "Must Enter An Weight");
        }
    }
    private void checkEmployment(){
        if(employment.getCheckedRadioButtonId() != -1){
            verticalStepperForm.setStepAsCompleted(7);
        }else{
            verticalStepperForm.setStepAsUncompleted(7, "Must Select A Value");
        }
    }
    private void checkMaritialStatus(){
        if(maritialStatus.getCheckedRadioButtonId() != -1){
            verticalStepperForm.setStepAsCompleted(8);
        }else{
            verticalStepperForm.setStepAsUncompleted(8, "Must Select A Value");
        }
    }
    private void checkIfSmoker(){
        if(smoker.getCheckedRadioButtonId() != -1){
            verticalStepperForm.setStepAsCompleted(9);
        }else{
            verticalStepperForm.setStepAsUncompleted(9, "Must Select A Value");
        }
    }
    private void checkOptionallyInsured(){
        if(!optionallyInsured.getText().toString().equals("$")) {
            verticalStepperForm.setStepAsCompleted(10);
        }else{
            verticalStepperForm.setStepAsUncompleted(10, "Must Enter A Insurance Amount");
        }
    }
    private void checkPeopleCovered(){
        if(!peopleCovered.getText().toString().equals("")) {
            verticalStepperForm.setStepAsCompleted(11);
        }else{
            verticalStepperForm.setStepAsUncompleted(11, "Must Enter The Number Of People Covered");
        }
    }
}
