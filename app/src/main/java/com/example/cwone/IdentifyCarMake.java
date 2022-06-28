package com.example.cwone;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class IdentifyCarMake extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

     ImageView carImageView;
     Spinner spinnerDropdown;
     Button identifyButton;
     private TextView resultTextView;
     TextView answerTextView;
     TextView timerTextView;

     int count;
     int imageID; // To store the index of the random image generated
     int chosenID; // To store the index of the image chosen by the user
     String correctAnswer;


    private int[] imagesArray;
    List<Integer> imageIndexList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_make);

        initializeElements();
        setRandomImage();
        dropDrown();
        setListenerOnIdentifyButton();
        setUpTimer();

    }

    public void initializeElements(){

        spinnerDropdown = findViewById(R.id.spinner_dropdown);
        identifyButton = findViewById(R.id.button_identify);
        carImageView = findViewById(R.id.imageView_car);
        resultTextView = findViewById(R.id.textView_result);
        answerTextView = findViewById(R.id.textView_answer);
        timerTextView = findViewById(R.id.textView_timer);

        count = 20;

    }

    public void setRandomImage(){
        imagesArray = new int[]{
                R.drawable.audi,
                R.drawable.bentley,
                R.drawable.bmw,
                R.drawable.bugatti,
                R.drawable.cadillac,
                R.drawable.chevrolet,
                R.drawable.chrysler,
                R.drawable.ferrari,
                R.drawable.ford,
                R.drawable.hyundai,
                R.drawable.jaguar,
                R.drawable.kia,
                R.drawable.lamborghini,
                R.drawable.land_rover,
                R.drawable.mazda,
                R.drawable.mercedes,
                R.drawable.mini_cooper,
                R.drawable.mitsubishi,
                R.drawable.morris_garage,
                R.drawable.nissan,
                R.drawable.peugeot,
                R.drawable.porsche,
                R.drawable.renault,
                R.drawable.rolls_royce,
                R.drawable.suzuki,
                R.drawable.tata,
                R.drawable.tesla,
                R.drawable.toyota,
                R.drawable.volkswagen,
                R.drawable.volvo};

        //getting a random number
        imageID = (int)(Math.random() * imagesArray.length);
        imageIndexList.add(imageID);

        carImageView.setImageResource(imagesArray[imageID]);
    }


    public void generateNextImage(){
        if (imageIndexList.size() == 30){ //show in toast saying no more images
            Toast.makeText(IdentifyCarMake.this,"No more Images",Toast.LENGTH_LONG).show();
            //Disable the identify button
            identifyButton.setOnClickListener(null);

        } else {
            // while loop to run until the random numbers generated do not exist in the image index list
            while (imageIndexList.contains(imageID)){
                imageID = (int)(Math.random() * imagesArray.length);


            }
            imageIndexList.add(imageID);
            carImageView.setImageResource(imagesArray[imageID]);
        }


    }

    public void setUpTimer(){

        final Handler handler = new Handler();
        final int delay = 1000;
        count = 20;

        if (MainActivity.isTimerOn) {
            handler.postDelayed(new Runnable() {

                public void run() {

                    if (MainActivity.isTimerOn) {

                        timerTextView.setText(String.valueOf(count));
                        handler.postDelayed(this, delay);

                        // Stop the timer and show the results
                        if (count == 0) {

                            identifyButton.setText(R.string.next);
                            MainActivity.isTimerOn = false;
                            Toast.makeText(IdentifyCarMake.this, "Timeout!", Toast.LENGTH_SHORT).show();

                            if (chosenID == imageID) { // The user selected the correct answer
                                resultTextView.setText(R.string.correct_text);
                                resultTextView.setTextColor(Color.parseColor("#00FF00"));

                            } else {
                                resultTextView.setText(R.string.wrong_text);
                                resultTextView.setTextColor(Color.parseColor("#FF0000"));
                            }

                        } else {
                            count--;
                        }
                    }
                }
            }, delay);

        } else {
            timerTextView.setText("");
        }
    }

    //METHOD TO RESET ALL THE ELEMENTS
    public void resetElements(){

        identifyButton.setText(R.string.identify);
        resultTextView.setText("");
        answerTextView.setText("");
        timerTextView.setText("20");

        setUpTimer();

    }

    public void setListenerOnIdentifyButton(){

        final String identifyButtonText = "Next";

        identifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Assign correct answer to a variable
                 correctAnswer = getResources().getStringArray(R.array.car_brand_names)[imageID]; //getting the correct id

                if (identifyButton.getText() == identifyButtonText){
                    //Reset all

                    // Simplified
                    MainActivity.isTimerOn = MainActivity.checkTimerStatus;

//                    if (MainActivity.checkTimerStatus) {
//                        MainActivity.isTimerOn = true;
//
//                    } else {
//                        MainActivity.isTimerOn = false;
//                    }
                    resetElements();
                    generateNextImage();
                }
                else {

                    MainActivity.isTimerOn = false;
                    identifyButton.setText("Next");
                    answerTextView.setText(correctAnswer); //setting the correct id to the text view if the answer is wrong

                    if(chosenID == imageID){
                        resultTextView.setText(R.string.correct_text);
                        resultTextView.setTextColor(Color.parseColor("#00FF00")); //green color


                    }
                    else{
                        resultTextView.setText(R.string.wrong_text);
                        resultTextView.setTextColor(Color.parseColor("#FF0000"));//red color
                    }
                }

            }
        });

    }

    public void dropDrown(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.car_brand_names, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDropdown.setAdapter(adapter);
        spinnerDropdown.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.chosenID = position;
        /*String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}