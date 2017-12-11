package com.example.viteck.viteckchallenge;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * Created by Eric on 12/2/2017.
 */

public class UserQuestions extends Activity implements VerticalStepperForm, mlResponse {
    private LinearLayout parentLayout;
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
    private int lowConditions;
    private int medConditions;
    private int highConditions;

    private String stateInput = "";
    private String cityInput = "";
    private String sexInput = "";
    private String ageInput;
    private  String employmentInput = "";
    private String maritalstatusInput = "";
    private String optionalinsuredInput;
    private  String peoplecoveredInput;
    private String preexisting;
    private String smokerInput = "";
    private String weightInput;
    private String heightInput;
    private String incomeInput;



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
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();
        mDatabase = database.getReference();
        if ((i.getExtras()!=null) && (i.getExtras().get("fromMain").equals("true"))) {
            fromMain = true;





//            String[] mySteps = {"State", "City", "Age", "Sex", "Height (in)", "Weight (lbs)", "Annual Income", "Employment", "Maritial Status", "Smoker", "Optional Insured", "People Covered", "Preconditions"};
//            int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
//            int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

//            LayoutInflater inflater = LayoutInflater.from(getBaseContext());
//            parentLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);
//            // Finding the view

            //DO FIREBASE HERE AND API CALL HERE.
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference mDatabase = database.getReference();

            String user = mAuth.getCurrentUser().getUid();
            final DatabaseReference dbResponse = mDatabase.child("Users").child(user);
            dbResponse.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ageInput = (String) dataSnapshot.child("age").getValue();
                    stateInput = (String) dataSnapshot.child("state").getValue();
                    cityInput = (String) dataSnapshot.child("city").getValue();
                    sexInput = (String) dataSnapshot.child("sex").getValue();
                    employmentInput = (String) dataSnapshot.child("employment").getValue();
                    maritalstatusInput = (String) dataSnapshot.child("maritalstatus").getValue();
                    optionalinsuredInput = (String) dataSnapshot.child("optionalinsured").getValue();
                    peoplecoveredInput = (String) dataSnapshot.child("peoplecovered").getValue();
                    preexisting = (String) dataSnapshot.child("preexisting").getValue();
                    smokerInput = (String) dataSnapshot.child("smoker").getValue();
                    weightInput = (String) dataSnapshot.child("weight").getValue();
                    heightInput = (String) dataSnapshot.child("height").getValue();
                    incomeInput = (String) dataSnapshot.child("income").getValue();

                    verticalStepper();







                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });



        }
        else
        {
            String[] mySteps = {"State", "City", "Age", "Sex", "Height (in)", "Weight (lbs)", "Annual Income", "Employment", "Maritial Status", "Smoker", "Optional Insured", "People Covered", "Preconditions"};
            int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
            int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

            LayoutInflater inflater = LayoutInflater.from(getBaseContext());
            parentLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);

            verticalStepperForm = findViewById(R.id.vertical_stepper_form);

            // Setting up and initializing the form
            VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, this)
                    .primaryColor(colorPrimary)
                    .primaryDarkColor(colorPrimaryDark)
                    .displayBottomNavigation(false) // Defaults to true
                    .init();
        }


    }

    public void verticalStepper()
    {
        String[] mySteps = {"State", "City", "Age", "Sex", "Height (in)", "Weight (lbs)", "Annual Income", "Employment", "Maritial Status", "Smoker", "Optional Insured", "People Covered", "Preconditions"};
        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        parentLayout = (LinearLayout) inflater.inflate(R.layout.views, null, false);

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
                view = createStateStep();
                break;
            case 1:
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
            default:
                break;
        }
    }

    @Override
    public void sendData() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = database.getReference();
        String user = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(user).child("age").setValue(age.getText().toString());
        mDatabase.child("Users").child(user).child("state").setValue(state.getSelectedItem().toString());
        mDatabase.child("Users").child(user).child("city").setValue(city.getSelectedItem().toString());
        RadioButton MaleradioButton = findViewById(R.id.maleRB);
        if (MaleradioButton.isChecked())
        {
            mDatabase.child("Users").child(user).child("sex").setValue("M");

        }
        else {
            mDatabase.child("Users").child(user).child("sex").setValue("F");

        }
        mDatabase.child("Users").child(user).child("height").setValue(height.getText().toString());
        mDatabase.child("Users").child(user).child("weight").setValue(weight.getText().toString());
        mDatabase.child("Users").child(user).child("income").setValue(income.getText().toString());
        RadioButton employedRB = findViewById(R.id.employedRB);
        if (employedRB.isChecked())
        {
            mDatabase.child("Users").child(user).child("employment").setValue("employed");

        }
        else
        {
            mDatabase.child("Users").child(user).child("employment").setValue("unemployed");
        }
        RadioButton maritalRB = findViewById(R.id.marriedRB);
        if (maritalRB.isChecked())
        {
            mDatabase.child("Users").child(user).child("maritalstatus").setValue("married");
        }
        else {
            mDatabase.child("Users").child(user).child("maritalstatus").setValue("single");
        }

        RadioButton smoker = findViewById(R.id.smokerRB);
        if (smoker.isChecked())
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


        int income = Integer.parseInt(this.income.getText().toString().replace("$", ""));
        int height = Integer.parseInt(this.height.getText().toString());
        int peopleCovered = Integer.parseInt(this.peopleCovered.getText().toString());
        int optionallyInsured = Integer.parseInt(this.optionallyInsured.getText().toString().replace("$", ""));
        int weight = Integer.parseInt(this.weight.getText().toString());
        int low =  lowConditions;
        int medium = medConditions;
        int high = highConditions;
        int age = Integer.parseInt(this.age.getText().toString());

        RadioButton isMarried = findViewById(this.maritialStatus.getCheckedRadioButtonId());
        RadioButton gender = findViewById(this.sex.getCheckedRadioButtonId());
        String marital_status = String.valueOf(isMarried.getText().charAt(0));
        String tobacco = smoker.getText().toString();
        String sex = String.valueOf(gender.getText().charAt(0));

        JSONObject j = new JSONObject();
        JSONObject jq = new JSONObject();

        try {
            j.put("STATE", state);
            j.put("ANNUAL_INCOME", income);
            j.put("HEIGHT", height);
            j.put("PEOPLE_COVERED", peopleCovered);
            j.put("OPTIONAL_INSURED", optionallyInsured);
            j.put("WEIGHT", weight);
            j.put("LOW", low);
            j.put("MEDIUM", medium);
            j.put("HIGH", high);
            j.put("AGE", age);
            j.put("MARITAL_STATUS", marital_status);
            j.put("TOBACCO", tobacco);
            j.put("SEX", sex);

            jq.put("customer_data", j);
        } catch (JSONException e) {

        }
        sendRequestToML a = new sendRequestToML();
        a.delegate = this;
        a.execute(jq.toString());
    }

    @Override
    public void handleMlResponse(String s) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("MLResponse", s);
        try
        {
            JSONObject MLResponse = new JSONObject(s);
            String ss = MLResponse.get("cust_data").toString();
            JSONObject j = new JSONObject(ss);
            double bronzePrem = j.getDouble("BRONZE_PREMIUM");
            double silverPrem = j.getDouble("SILVER_PREMIUM");
            double goldPrem = j.getDouble("GOLD_PREMIUM");
            double platinumPrem = j.getDouble("PLATINUM_PREMIUM");
            int cls = j.getInt("CLASS");


            database = FirebaseDatabase.getInstance();
            mAuth = FirebaseAuth.getInstance();
            String thisUser = mAuth.getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference().child("Users").child(thisUser).child("predicted").child("Bronze").setValue(bronzePrem);
            FirebaseDatabase.getInstance().getReference().child("Users").child(thisUser).child("predicted").child("Silver").setValue(silverPrem);
            FirebaseDatabase.getInstance().getReference().child("Users").child(thisUser).child("predicted").child("Gold").setValue(goldPrem);
            FirebaseDatabase.getInstance().getReference().child("Users").child(thisUser).child("predicted").child("Platinum").setValue(platinumPrem);
            FirebaseDatabase.getInstance().getReference().child("Users").child(thisUser).child("predicted").child("Class").setValue(cls);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        startActivity(i);
    }

    private View createStateStep() {
        if (fromMain)
        {
            state = parentLayout.findViewById(R.id.personState);
            state.setPrompt(stateInput);
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
        if (fromMain)
        {
            city = parentLayout.findViewById(R.id.personCity);
            city.setPrompt(cityInput);
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
            age = parentLayout.findViewById(R.id.personAge);
            age.setText(ageInput.toString());
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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAge();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return age;
    }

    private View createSexStep() {
        if (fromMain) {
            sex = parentLayout.findViewById(R.id.genderGroup);

            if (sexInput.equals("M"))
            {
                RadioButton radioButton = parentLayout.findViewById(R.id.maleRB);
                radioButton.setChecked(true);
            }
            else
            {
                RadioButton radioButton = parentLayout.findViewById(R.id.femaleRB);
                radioButton.setChecked(true);
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
        height = parentLayout.findViewById(R.id.personHeight);
        if (fromMain)
        {

            height.setText(heightInput.toString());
        }
        ((ViewGroup) height.getParent()).removeView(height);
        height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkHeight();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return height;
    }

    private View createWeightStep() {
        weight = parentLayout.findViewById(R.id.personWeight);
        if (fromMain)
        {

            weight.setText(weightInput.toString());
        }
        ((ViewGroup) weight.getParent()).removeView(weight);
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkWeight();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return weight;
    }

    private View createIncomeStep() {
        income = parentLayout.findViewById(R.id.annualIncome);
        if (fromMain)
        {

            income.setText(incomeInput.toString());
        }
        ((ViewGroup) income.getParent()).removeView(income);
        income.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String incomeString = income.getText().toString();
                if (!incomeString.startsWith("$")) {
                    income.setText("$" + incomeString.replace("$", ""));
                }
                checkIncome();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return income;
    }

    private View createEmploymentStep() {
        employment = parentLayout.findViewById(R.id.employementGroup);
        if (fromMain) {

            if (employmentInput.equals("employed"))
            {
                RadioButton radioButton = parentLayout.findViewById(R.id.employedRB);
                radioButton.setChecked(true);
            }
            else {
                RadioButton radioButton = parentLayout.findViewById(R.id.unemployedRB);
                radioButton.setChecked(true);
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
        maritialStatus = parentLayout.findViewById(R.id.maritialGroup);
        if (fromMain) {

            if (maritalstatusInput.equals("single"))
            {
                RadioButton radioButton = parentLayout.findViewById(R.id.singledRB);
                radioButton.setChecked(true);
            }
            else {
                RadioButton radioButton = parentLayout.findViewById(R.id.marriedRB);
                radioButton.setChecked(true);
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
        smoker = parentLayout.findViewById(R.id.smokerGroup);
        if (fromMain)
        {

            if (smokerInput.equals("true"))
            {
                RadioButton radioButton = parentLayout.findViewById(R.id.smokerRB);
                radioButton.setChecked(true);
            }
            else {
                RadioButton radioButton = parentLayout.findViewById(R.id.nonSmokerRB);
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
        optionallyInsured = parentLayout.findViewById(R.id.optionallyInsured);
        if (fromMain)
        {

            optionallyInsured.setText(optionalinsuredInput.toString());
        }
        ((ViewGroup) optionallyInsured.getParent()).removeView(optionallyInsured);
        optionallyInsured.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String optString = optionallyInsured.getText().toString();
                if (!optString.startsWith("$")) {
                    optionallyInsured.setText("$" + optString.replace("$", ""));
                }
                checkOptionallyInsured();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return optionallyInsured;
    }

    private View createPeopleCoveredStep() {
        peopleCovered = parentLayout.findViewById(R.id.peopleCovered);
        if (fromMain)
        {

            peopleCovered.setText(peoplecoveredInput.toString());
        }
        ((ViewGroup) peopleCovered.getParent()).removeView(peopleCovered);
        peopleCovered.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkPeopleCovered();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return peopleCovered;
    }
    //what are we doing about this for displaying metrics.
    private View createPreconditionsStep() {
        conditionsLLayout = parentLayout.findViewById(R.id.conditionsLLayout);
        priorConditions = parentLayout.findViewById(R.id.preexistingConditionsList);
        conditionsInput = parentLayout.findViewById(R.id.conditionsInput);
        conditionsSpinner = parentLayout.findViewById(R.id.conditionsSpinner);
        submitConditionsButton = parentLayout.findViewById(R.id.submitConditionButton);

        if (fromMain)
        {
            String user = mAuth.getCurrentUser().getUid();
            final DatabaseReference dbResponse = mDatabase.child("Users").child(user);
            dbResponse.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //String arrayAsString = dataSnapshot.child("preexisting").getValue().toString();
                    //conditionsList = (ArrayList<String>) Arrays.asList(arrayAsString.replace("[", "").replace("]", "").split(","));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        ((ViewGroup) conditionsLLayout.getParent()).removeView(conditionsLLayout);

        priorConditions.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, conditionsList));

        submitConditionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String conditionsText = conditionsInput.getText().toString();
                if (!conditionsText.equals("")) {
                    conditionsList.add(conditionsText);
                    ((ArrayAdapter) priorConditions.getAdapter()).notifyDataSetChanged();

                    String selectedSpinner = (String) conditionsSpinner.getSelectedItem();
                    if (selectedSpinner.equals("Low")) {
                        lowConditions++;
                    } else if (selectedSpinner.equals("Medium")) {
                        medConditions++;
                    } else {
                        highConditions++;
                    }
                } else {
                    Toast.makeText(UserQuestions.this, "Please add a name for this condition", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return conditionsLLayout;
    }

    private void checkAge() {
        if (!age.getText().toString().equals("")) {
            verticalStepperForm.setStepAsCompleted(2);
        } else {
            verticalStepperForm.setStepAsUncompleted(2, "Must Enter An Age");
        }
    }

    private void checkSex() {
        if (sex.getCheckedRadioButtonId() != -1) {
            verticalStepperForm.setStepAsCompleted(3);
        } else {
            verticalStepperForm.setStepAsUncompleted(3, "Must Select A Value");
        }
    }

    private void checkHeight() {
        if (!height.getText().toString().equals("")) {
            verticalStepperForm.setStepAsCompleted(4);
        } else {
            verticalStepperForm.setStepAsUncompleted(4, "Must Enter A Height");
        }
    }

    private void checkWeight() {
        if (!weight.getText().toString().equals("")) {
            verticalStepperForm.setStepAsCompleted(5);
        } else {
            verticalStepperForm.setStepAsUncompleted(5, "Must Enter An Weight");
        }
    }

    private void checkIncome() {
        if (!income.getText().toString().equals("$")) {
            verticalStepperForm.setStepAsCompleted(6);
        } else {
            verticalStepperForm.setStepAsUncompleted(6, "Must Enter An Weight");
        }
    }

    private void checkEmployment() {
        if (employment.getCheckedRadioButtonId() != -1) {
            verticalStepperForm.setStepAsCompleted(7);
        } else {
            verticalStepperForm.setStepAsUncompleted(7, "Must Select A Value");
        }
    }

    private void checkMaritialStatus() {
        if (maritialStatus.getCheckedRadioButtonId() != -1) {
            verticalStepperForm.setStepAsCompleted(8);
        } else {
            verticalStepperForm.setStepAsUncompleted(8, "Must Select A Value");
        }
    }

    private void checkIfSmoker() {
        if (smoker.getCheckedRadioButtonId() != -1) {
            verticalStepperForm.setStepAsCompleted(9);
        } else {
            verticalStepperForm.setStepAsUncompleted(9, "Must Select A Value");
        }
    }

    private void checkOptionallyInsured() {
        if (!optionallyInsured.getText().toString().equals("$")) {
            verticalStepperForm.setStepAsCompleted(10);
        } else {
            verticalStepperForm.setStepAsUncompleted(10, "Must Enter A Insurance Amount");
        }
    }

    private void checkPeopleCovered() {
        if (!peopleCovered.getText().toString().equals("")) {
            verticalStepperForm.setStepAsCompleted(11);
        } else {
            verticalStepperForm.setStepAsUncompleted(11, "Must Enter The Number Of People Covered");
        }
    }
}
