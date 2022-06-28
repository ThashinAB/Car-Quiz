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
import java.util.Random;

public class AdvancedLevel extends AppCompatActivity {

    ImageView firstCarImageView;
    ImageView secondCarImageView;
    ImageView thirdCarImageView;
    EditText textBoxOne;
    EditText textBoxTwo;
    EditText textBoxThree;
    TextView scoreTextView;
    TextView answerTextViewOne;
    TextView answerTextViewTwo;
    TextView answerTextViewThree;
    TextView resultTextView;
    TextView timerTextView;
    Button submitButton;

    int points;
    int count;

    //for setUpRandom
    private int[] imageViewImages;
    int firstImageID;
    int secondImageID;
    int thirdImageID;
    int carBrandIndex;
    List<Integer> imageIndexList = new ArrayList<>();
    Random rand = new Random();

    //for onClickListener
    String firstImageName;
    String secondImageName;
    String thirdImageName;
    String firstTextBox;
    String secondTextBox;
    String thirdTextBox;
    int noOfAttempts;
    boolean foundAnswerOne;
    boolean foundAnswerTwo;
    boolean foundAnswerThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);

        initializeElements();
        setRandomImage();
        setListenerOnSubmit();
        setupTimer();

    }

    private void initializeElements() {

        firstCarImageView = findViewById(R.id.imageView_al_one);
        secondCarImageView = findViewById(R.id.imageView_al_two);
        thirdCarImageView = findViewById(R.id.imageView_al_three);
        textBoxOne = findViewById(R.id.editText_al_one);
        textBoxTwo = findViewById(R.id.editText_al_two);
        textBoxThree = findViewById(R.id.editText_al_three);
        answerTextViewOne = findViewById(R.id.textView_answer_one);
        answerTextViewTwo = findViewById(R.id.textView_answer_two);
        answerTextViewThree = findViewById(R.id.textView_answer_three);
        resultTextView = findViewById(R.id.textView_result);
        submitButton = findViewById(R.id.button_al_submit);
        scoreTextView = findViewById(R.id.textView_score);
        scoreTextView.setText("Score : "+points);
        timerTextView = findViewById(R.id.textView_timer);

        noOfAttempts = 3;
        points = 0;
        count = 20;

    }

    public void setRandomImage() {

        imageViewImages = new int[]{
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

        // Image for the first image view
        firstImageID = rand.nextInt((9) + 1);
        //Set the image
        firstCarImageView.setImageResource(imageViewImages[firstImageID]);
        imageIndexList.add(firstImageID);

        // Image for the Second image view
        secondImageID = rand.nextInt((19 - 10) + 1) + 10;
        //set the image
        secondCarImageView.setImageResource(imageViewImages[secondImageID]);
        imageIndexList.add(secondImageID);

        // Image for the Second image view
        thirdImageID = rand.nextInt((29 - 20) + 1) + 20;
        //set the image
        thirdCarImageView.setImageResource(imageViewImages[thirdImageID]);
        imageIndexList.add(thirdImageID);

    }

    public void generateNextImage() {
        if (imageIndexList.size() == 30) {
            //show in toast saying no more images
            Toast.makeText(AdvancedLevel.this, "No more Images", Toast.LENGTH_SHORT).show();

        } else {
            // Create a while to run until the random numbers generated do not exist in the image index list
            while (imageIndexList.contains(firstImageID) || imageIndexList.contains(secondImageID) || imageIndexList.contains(thirdImageID)) {

                firstImageID = rand.nextInt((9) + 1);
                secondImageID = rand.nextInt((19 - 10) + 1) + 10;
                thirdImageID = rand.nextInt((29 - 20) + 1) + 20;
            }
            imageIndexList.add(firstImageID);
            imageIndexList.add(secondImageID);
            imageIndexList.add(thirdImageID);

            // Set the images to the image views
            firstCarImageView.setImageResource(imageViewImages[firstImageID]);
            secondCarImageView.setImageResource(imageViewImages[secondImageID]);
            thirdCarImageView.setImageResource(imageViewImages[thirdImageID]);

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
                            submitButton.setText("Next");
                            MainActivity.isTimerOn = false;
                            Toast.makeText(AdvancedLevel.this, "Timeout, no image selected!", Toast.LENGTH_SHORT).show();

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

    public void setListenerOnSubmit() {

        final String submitButtonText = "Next";

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                noOfAttempts--;//Each time submit button is clicked no of attempts reduces

                scoreTextView.setText("Score : "+points);//Displaying the score
                checkingTextBox();//Running the conditions


                if (submitButton.getText() == submitButtonText){
                    //Reset all
                    resetElements();
                    generateNextImage();
                }
                else {
                    //checking if all conditions are true
                    if (firstTextBox.equals(firstImageName) && secondTextBox.equals(secondImageName) && thirdTextBox.equals(thirdImageName)){
                        submitButton.setText("Next");
                        resultTextView.setText("Correct");
                        resultTextView.setTextColor(Color.parseColor("#00FF00"));//green color
                        points +=3;//Score when all conditions are true(all text boxes are correct)
                        MainActivity.isTimerOn = false; // Stop the timer

                    }
                    else if (noOfAttempts == 0 ){ ;
                        //if zero attempts left
                        submitButton.setText("Next");
                        resultTextView.setText("Wrong");
                        resultTextView.setTextColor(Color.parseColor("#FF0000"));//red color

                        setScoreAndCorrectAnswer();
                        MainActivity.isTimerOn = false; // Stop the timer

                    }
                }



            }
        });

    }

    //Conditions to set points and right answers for the incorrect ones
    public void setScoreAndCorrectAnswer(){

        //If boolean returns true checking the the rights answers individually
        if (foundAnswerOne){
            points += 1;
        }

        if(foundAnswerTwo){
            points +=1;
        }

        if(foundAnswerThree){
            points +=1;
        }

        //If boolean returns false checking the incorrect text boxes individually
        if (!foundAnswerOne){
            answerTextViewOne.setText(firstImageName);
        }
        if (!foundAnswerTwo){
            answerTextViewTwo.setText(secondImageName);
        }
        if (!foundAnswerThree){
            answerTextViewThree.setText(thirdImageName);
        }
    }

    //Checking text boxes individually (conditions)
    public void checkingTextBox(){

        //Getting the car brand names in the three images
        firstImageName = getResources().getStringArray(R.array.hint_car_names)[firstImageID].toLowerCase();
        secondImageName = getResources().getStringArray(R.array.hint_car_names)[secondImageID].toLowerCase();
        thirdImageName = getResources().getStringArray(R.array.hint_car_names)[thirdImageID].toLowerCase();
        //Getting the user inputs in the three text boxes
        firstTextBox = textBoxOne.getText().toString().toLowerCase();
        secondTextBox = textBoxTwo.getText().toString().toLowerCase();
        thirdTextBox = textBoxThree.getText().toString().toLowerCase();

        foundAnswerOne = false;

        if (firstTextBox.equals(firstImageName)){
            textBoxOne.setEnabled(false);// Making the text box uneditable
            textBoxOne.setBackgroundColor(Color.parseColor("#009E00"));//green
            textBoxOne.setTextColor(Color.BLACK);
            foundAnswerOne = true;
        }
        else {
            textBoxOne.setBackgroundColor(Color.parseColor("#D83636"));//red
        }

        foundAnswerTwo = false;
        if (secondTextBox.equals(secondImageName)){
            textBoxTwo.setEnabled(false);
            textBoxTwo.setBackgroundColor(Color.parseColor("#009E00"));
            textBoxTwo.setTextColor(Color.BLACK);
            foundAnswerTwo = true;
        }
        else {
            textBoxTwo.setBackgroundColor(Color.parseColor("#D83636"));
        }

        foundAnswerThree = false;
        if (thirdTextBox.equals(thirdImageName)){
            textBoxThree.setEnabled(false);
            textBoxThree.setBackgroundColor(Color.parseColor("#009E00"));
            textBoxThree.setTextColor(Color.BLACK);
            foundAnswerThree = true;

        }
        else {
            textBoxThree.setBackgroundColor(Color.parseColor("#D83636"));
        }

    }

    public void resetElements() {

        MainActivity.isTimerOn = MainActivity.checkTimerStatus;
        textBoxOne.setText("");
        textBoxTwo.setText("");
        textBoxThree.setText("");
        textBoxOne.setEnabled(true);
        textBoxTwo.setEnabled(true);
        textBoxThree.setEnabled(true);
        textBoxOne.setBackgroundColor(Color.TRANSPARENT);
        textBoxTwo.setBackgroundColor(Color.TRANSPARENT);
        textBoxThree.setBackgroundColor(Color.TRANSPARENT);
        answerTextViewOne.setText("");
        answerTextViewTwo.setText("");
        answerTextViewThree.setText("");
        resultTextView.setText("");
        submitButton.setText(R.string.submit_button);

        noOfAttempts = 3;
        setupTimer();

    }
}