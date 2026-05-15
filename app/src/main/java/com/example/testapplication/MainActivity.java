package com.example.testapplication;

import android.os.Bundle;
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
        for (int i = 0; i < breedCount; i++){
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

        dogSelector.setAdapter(adapter);

        void updatePercentage() {
            myBreed = DogReader.findDogBreed(dogSelector.getSelectedItem().toString());
            bigPercentage =
        }
    }
}