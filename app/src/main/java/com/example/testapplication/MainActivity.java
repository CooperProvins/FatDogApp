package com.example.testapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButtonToggleGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private String selected = "";
    private int slide = 0;
    private String gender = "Male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String[] fruits = {"Select a Fruit", "Apple", "Orange", "Grape", "Banana", "Apple", "Orange", "Grape", "Banana","Apple", "Orange", "Grape", "Banana", "Cherry", "Mango", "Peach"};

        Spinner spinner = findViewById(R.id.mySpinner);
        TextView textView = findViewById(R.id.textView2);
        EditText editText = findViewById(R.id.editTextNumber);
        SeekBar seekBar = findViewById(R.id.seekBar);
        MaterialButtonToggleGroup toggleButton = findViewById(R.id.toggleButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                fruits
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        toggleButton.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.buttonMale) {
                    gender = "Male";
                } else if (checkedId == R.id.buttonFemale) {
                    gender = "Female";
                }
                updateTextView(textView, editText);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                slide = progress;
                updateTextView(textView, editText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selected = "None";
                } else {
                    selected = fruits[position];
                }
                updateTextView(textView, editText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateTextView(textView, editText);
            }
        });

    }

    private void updateTextView(TextView textView, EditText editText) {
        textView.setText(gender + " | " + selected + " | " + editText.getText() + " | " + slide);
    }
}