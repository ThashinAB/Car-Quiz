package com.example.cwone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    // Create a global variable for the switch
    public static boolean isTimerOn;
    // To check if the user selected the timer or not
    public static boolean checkTimerStatus;

    Button identifyTheCarMake;
    Button hint;
    Button identifyTheCarImage;
    Button advancedLevel;
    Switch timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeElements();
        addListenerOnButton();
        setTimer();

    }
    //Initialize Elements
    public void initializeElements(){

        identifyTheCarMake = findViewById(R.id.button_identify_the_car_make);
        hint = findViewById(R.id.button_hints);
        identifyTheCarImage = findViewById(R.id.button_identify_the_car_image);
        advancedLevel = findViewById(R.id.button_advanced_level);
        timer = findViewById(R.id.switch_timer);

    }

    //When user taps the button
    public void addListenerOnButton(){

        identifyTheCarMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,IdentifyCarMake.class);
                startActivity(intent);
            }
        });

        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Hint.class);
                startActivity(intent);
            }
        });

        identifyTheCarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,IdentifyCarImage.class);
                startActivity(intent);
            }
        });

        advancedLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AdvancedLevel.class);
                startActivity(intent);
            }
        });


    }

    //https://www.youtube.com/watch?v=ZcWN-d3tTT4
    public void setTimer(){

        timer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                // Simplified if condition
                isTimerOn = isChecked; // Sets the isTimerOn variable according to the switch being checked or not
                checkTimerStatus = isChecked;

//                if (isChecked) {
//                    isTimerOn = true;

//                } else {
//                    isTimerOn = false;
//                }
            }
        });
    }

}