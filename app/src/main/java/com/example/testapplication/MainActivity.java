package com.example.testapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    int dogWeight;
    Sex dogSex;
    String dogBreedInput;
    double percentile;

    DogBreed myBreed;


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

        DogReader.initialize(this);

        int breedCount = DogReader.getDogBreeds().size();
        String[] dogNames = new String[breedCount];
        for (int i = 0; i < breedCount; i++) {
            dogNames[i] = DogReader.getDogBreeds().get(i).getName();
        }

        Spinner dogSelector = findViewById(R.id.mySpinner);
        TextView bigPercentage = findViewById(R.id.textView2);
        TextView weightText = findViewById(R.id.textView3);
        SeekBar weightSelector = findViewById(R.id.seekBar);
        MaterialButtonToggleGroup genderButton = findViewById(R.id.toggleButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                dogNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        weightText.setText(String.valueOf(weightSelector.getProgress()));

        dogSelector.setAdapter(adapter);

        genderButton.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.buttonFemale) {
                    dogSex = Sex.FEMALE;
                } else if (checkedId == R.id.buttonMale) {
                    dogSex = Sex.MALE;
                }
                updateInfo(bigPercentage);
            }
        });
        weightSelector.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dogWeight = (int)(Math.pow(weightSelector.getProgress(),2)*0.02);
                weightText.setText(String.valueOf(dogWeight));
                updateInfo(bigPercentage);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dogSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myBreed = DogReader.findDogBreed(parent.getItemAtPosition(position).toString());
                updateInfo(bigPercentage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Initialize variables with defaults
        dogWeight = weightSelector.getProgress();
        dogSex = Sex.MALE; // Default selection

        if (dogSelector.getSelectedItem() != null) {
            myBreed = DogReader.findDogBreed(dogSelector.getSelectedItem().toString());
        }

        updateInfo(bigPercentage);
    }
    private void updateInfo(TextView bigPercentage){
        if (myBreed != null && dogSex != null) {
            percentile = myBreed.weightPercentile(dogSex, dogWeight);
            bigPercentage.setText(String.format("%.2f", percentile*100)+"%");
        }
    }
}