package com.example.viteck.viteckchallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Eric on 12/2/2017.
 */

public class UserQuestions extends Activity {
    private Spinner state;
    private Spinner city;
    private EditText income;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        state = findViewById(R.id.personState);
        city = findViewById(R.id.personCity);
        income = findViewById(R.id.annualIncome);
        doneButton = findViewById(R.id.doneButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserQuestions.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
