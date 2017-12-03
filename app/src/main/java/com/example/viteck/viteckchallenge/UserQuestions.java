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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    boolean fromMain = false;
    private Button doneButton;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions);
        Intent i = this.getIntent();
        if (i.getExtras().get("fromMain").equals("true"))
        {
            fromMain = true;
             database = FirebaseDatabase.getInstance();
             mAuth = FirebaseAuth.getInstance();
             mDatabase = database.getReference();



        }
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
                //DO FIREBASE HERE AND API CALL HERE.
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                DatabaseReference mDatabase = database.getReference();
                String user = mAuth.getCurrentUser().getUid();
                mDatabase.child("Users").child(user).child("age").setValue(age.getText().toString());
                mDatabase.child("Users").child(user).child("state").setValue(state.getSelectedItem().toString());
                mDatabase.child("Users").child(user).child("city").setValue(city.getSelectedItem().toString());
                int sexId = sex.getId();
                if (sexId == -1)
                {
                    mDatabase.child("Users").child(user).child("sex").setValue("M");
                }
                else
                {
                    mDatabase.child("Users").child(user).child("sex").setValue("F");
                }
                mDatabase.child("Users").child(user).child("height").setValue(height.getText().toString());
                mDatabase.child("Users").child(user).child("weight").setValue(weight.getText().toString());
                mDatabase.child("Users").child(user).child("income").setValue(income.getText().toString());
                int employementId = employment.getId();
                if (employementId == -1)
                {
                    mDatabase.child("Users").child(user).child("employment").setValue("employed");
                }
                else {
                    mDatabase.child("Users").child(user).child("employment").setValue("unemployed");

                }
                int martialStat = maritialStatus.getId();
                if (martialStat == -1)
                {
                    mDatabase.child("Users").child(user).child("maritalstatus").setValue("single");

                }
                else
                {
                    mDatabase.child("Users").child(user).child("maritalstatus").setValue("married");

                }
                int smokerStat = smoker.getId();
                if (smokerStat == -1)
                {
                    mDatabase.child("Users").child(user).child("smoker").setValue("true");

                }
                else
                {
                    mDatabase.child("Users").child(user).child("smoker").setValue("false");

                }
                mDatabase.child("Users").child(user).child("optionalinsured").setValue(optionallyInsured.getText().toString());
                mDatabase.child("Users").child(user).child("peoplecovered").setValue(peopleCovered.getText().toString());
                mDatabase.child("Users").child(user).child("preexisting").setValue(conditionsList.toString());






                startActivity(new Intent(this, MainActivity.class));

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
        if (fromMain == true)
        {
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
            state = spinnerLayout.findViewById(R.id.personState);
            String user = mAuth.getCurrentUser().getUid();
            String someState =  mDatabase.child("Users").child(user).child("state").getKey();
            state.setPrompt(someState);
            ((ViewGroup) state.getParent()).removeView(state);
            return state;
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        state = spinnerLayout.findViewById(R.id.personState);
        ((ViewGroup) state.getParent()).removeView(state);
        return state;
    }

    private View createCityStep() {
        if (fromMain == true)
        {
            LayoutInflater inflater = LayoutInflater.from(getBaseContext());
            LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
            city = spinnerLayout.findViewById(R.id.personState);
            String user = mAuth.getCurrentUser().getUid();
            String somecity =  mDatabase.child("Users").child(user).child("city").getKey();
            city.setPrompt(somecity);
            ((ViewGroup) city.getParent()).removeView(city);
            return city;
        }
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
        city = spinnerLayout.findViewById(R.id.personState);
        ((ViewGroup) city.getParent()).removeView(city);
        return city;
    }

    private View createAgeStep() {
        if(fromMain) {
            LayoutInflater inflater = LayoutInflater.from(getBaseContext());
            LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
            age = spinnerLayout.findViewById(R.id.personAge);
            String user = mAuth.getCurrentUser().getUid();
            String someage =  mDatabase.child("Users").child(user).child("age").getKey();
            age.setText(someage);
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
        if (fromMain) {
            LayoutInflater inflater = LayoutInflater.from(getBaseContext());
            LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
            sex = spinnerLayout.findViewById(R.id.genderGroup);
            String user = mAuth.getCurrentUser().getUid();
            String somesex =  mDatabase.child("Users").child(user).child("sex").getKey();
            if (somesex.equals("M"))
            {
                sex.check(-1);
            }
            else
            {
                sex.check(0);
            }
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
        if (fromMain)
        {
            LayoutInflater inflater = LayoutInflater.from(getBaseContext());
            LinearLayout spinnerLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
            height = spinnerLayout.findViewById(R.id.personHeight);
            ((ViewGroup) height.getParent()).removeView(height);
            String user = mAuth.getCurrentUser().getUid();
            String someheight =  mDatabase.child("Users").child(user).child("height").getKey();
            height.setText(someheight);
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
        if (fromMain)
        {
            String user = mAuth.getCurrentUser().getUid();
            String someweight =  mDatabase.child("Users").child(user).child("weight").getKey();
            weight.setText(someweight);
        }
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
        if (fromMain)
        {
            String user = mAuth.getCurrentUser().getUid();
            String someincome =  mDatabase.child("Users").child(user).child("income").getKey();
            income.setText(someincome);
        }
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
        if (fromMain) {
            String user = mAuth.getCurrentUser().getUid();
            String someemployment =  mDatabase.child("Users").child(user).child("employement").getKey();
            if (someemployment.equals("employed"))
            {
                employment.check(-1);
            }
            else {
                employment.check(0);
            }
        }

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
        if (fromMain) {
            String user = mAuth.getCurrentUser().getUid();
            String martialstat =  mDatabase.child("Users").child(user).child("maritalstatus").getKey();
            if (martialstat.equals("single"))
            {
                employment.check(-1);
            }
            else {
                employment.check(0);
            }
        }
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
        if (fromMain)
        {
            String user = mAuth.getCurrentUser().getUid();
            String smoker =  mDatabase.child("Users").child(user).child("smoker").getKey();
            if (smoker.equals("true"))
            {
                RadioButton radioButton = findViewById(R.id.smokerRB);
                radioButton.setChecked(true);
            }
            else {
                RadioButton radioButton = findViewById(R.id.nonSmokerRB);
                radioButton.setChecked(true);
            }
        }
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
        if (fromMain)
        {
            String user = mAuth.getCurrentUser().getUid();
            String optional =  mDatabase.child("Users").child(user).child("optionalinsured").getKey();
            optionallyInsured.setText(optional);
        }
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
        if (fromMain)
        {
            String user = mAuth.getCurrentUser().getUid();
            String peopleCov =  mDatabase.child("Users").child(user).child("peoplecovered").getKey();
            peopleCovered.setText(peopleCov);
        }
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
    //what are we doing about this for displaying metrics.
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
