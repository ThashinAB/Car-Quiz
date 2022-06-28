package com.example.cwone;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Hint extends AppCompatActivity {

    ImageView hintImageView;
    EditText textBox;
    Button submitButton;
    TextView hintTextView;
    TextView resultTextView;
    TextView answerTextView;
    TextView timerTextView;

    String correctAnswer;

    int[] viewHintImages;
    int imageID; // To store the index of the random image generated
    List<Integer> imageIndexList = new ArrayList<>();
    int noOfGuesses;
    int count;

    String hiddenText;
    char[] textCharArray;
    StringBuilder dashes;
    String valueOfHintAnswers;
    String dashesChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        initializeElements();
        setRandomImage();
        setListenerOnSubmitButton();
        showDashes();
        setupTimer();
    }

    private void initializeElements() {

        hintImageView = findViewById(R.id.imageView_hint_car);
        textBox = findViewById(R.id.editText_user_input);
        submitButton = findViewById(R.id.button_hint_submit);
        hintTextView = findViewById(R.id.textView_hint_text);
        resultTextView = findViewById(R.id.textView_result);
        answerTextView = findViewById(R.id.textView_answer);
        timerTextView = findViewById(R.id.textView_timer);

        noOfGuesses = 3;
        count = 20;


    }

    public void setRandomImage() {
        viewHintImages = new int[]{
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
        imageID = (int) (Math.random() * viewHintImages.length);
        imageIndexList.add(imageID);

        hintImageView.setImageResource(viewHintImages[imageID]);
    }

    public void generateNextImage(){
        if (imageIndexList.size() == 30){ //show in toast saying no more images
            Toast.makeText(Hint.this,"No more Images",Toast.LENGTH_LONG).show();
            //Disable the identify button
            submitButton.setOnClickListener(null);

        } else {
            // while loop to run until the random numbers generated do not exist in the image index list
            while (imageIndexList.contains(imageID)){
                imageID = (int)(Math.random() * viewHintImages.length);


            }
            imageIndexList.add(imageID);
            hintImageView.setImageResource(viewHintImages[imageID]);
        }


    }

    public void setupTimer() {

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
                            MainActivity.isTimerOn = false;
                            submitButton.setText("Next");
                            Toast.makeText(Hint.this, "Timeout, no image selected!", Toast.LENGTH_SHORT).show();

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

    //to display underscores
    public void showDashes(){

        hiddenText = getResources().getStringArray(R.array.hint_car_names)[imageID].toLowerCase();
        textCharArray = hiddenText.toCharArray();

        //changing the indexes to underscores (equalling each index to underscore)
        char[] hintAnswers = new char[textCharArray.length];
        for(int i = 0; i< textCharArray.length; i++){
            hintAnswers[i] = '_';

        }
        //add underscores
        valueOfHintAnswers = String.valueOf(hintAnswers); //outside of onclick cus it replaces not resets
        dashes = new StringBuilder(valueOfHintAnswers);
        hintTextView.setText(dashes);

    }

    public void setListenerOnSubmitButton(){

        final String submitButtonText = "Next";

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (submitButton.getText() == submitButtonText){
                    //Reset all
                    resetElements();
                    generateNextImage();
                    showDashes();
                }

                String letter = textBox.getText().toString().toLowerCase();
                char letterInTextBox = 0;
                //converting string to char because the other condition (textArray) type is also char
                for (int i = 0; i<letter.length(); i++){
                    letterInTextBox = letter.charAt(i);
                }

                boolean foundLetter = false;
                for(int i = 0; i< textCharArray.length; i++){
                    //checks if the letter is in the word
                    if(letterInTextBox == textCharArray[i]){
                        //validation done in xml(max length(1))

                        dashes.setCharAt(i, textCharArray[i]);
                        hintTextView.setText(dashes);
                        foundLetter = true;
                        textBox.setText(""); //clear input field
                    }

                }

                //Converting char type dashes in to a string to use "Contain" and iterate
                 dashesChar = dashes.toString();
                if (!dashesChar.contains("_")){
                    submitButton.setText("Next");
                    resultTextView.setText("Correct");
                    resultTextView.setTextColor(Color.parseColor("#00FF00")); //green color
                    MainActivity.isTimerOn = false; // Stop the timer
                }

                //If letter is not found in the text box
                if (!foundLetter){
                    noOfGuesses--;
                    Toast.makeText(Hint.this,"Wrong - Try again!",Toast.LENGTH_SHORT).show();//delete
                    textBox.setText(""); //clear input field

                }

                //if attempts are over
                if (noOfGuesses == 0 ){ ;
                    submitButton.setText("Next");
                    resultTextView.setText("Wrong");
                    resultTextView.setTextColor(Color.parseColor("#FF0000"));//red color
                    answerTextView.setText(hiddenText);
                    MainActivity.isTimerOn = false; // Stop the timer
                }
            }
        });
    }

    public void resetElements(){

        MainActivity.isTimerOn = MainActivity.checkTimerStatus;
        submitButton.setText(R.string.submit_button);
        resultTextView.setText("");
        answerTextView.setText("");
        hintTextView.setText("");

        noOfGuesses = 4;
        setupTimer();

    }



}