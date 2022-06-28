package com.example.cwone;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IdentifyCarImage extends AppCompatActivity {

    ImageView firstCarImageView;
    ImageView secondCarImageView;
    ImageView thirdCarImageView;
    TextView carBrandTextView;
    TextView resultTextView;
    Button nextButton;
    TextView timerTextView;

    private int[] imageViewImages;

    int firstImageID;
    int secondImageID;
    int thirdImageID;
    int carBrandIndex;
    int count;

    List<Integer> imageIndexList = new ArrayList<>();
    Random rand = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_image);

        initializeElements();
        setupTimer();
        setRandomImage();
        setListenerOnImageView();
        setCarBrandTextView();
        setListenerOnNextButton();

    }

    private void initializeElements() {

        firstCarImageView = findViewById(R.id.imageView_car_one);
        secondCarImageView = findViewById(R.id.imageView_car_two);
        thirdCarImageView = findViewById(R.id.imageView_car_three);

        carBrandTextView = findViewById(R.id.textView_car_brand);
        resultTextView = findViewById(R.id.textView_result);

        nextButton = findViewById(R.id.button_next);
        timerTextView = findViewById(R.id.textView_timer);
        count = 20;

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
                            Toast.makeText(IdentifyCarImage.this, "Timeout, no image selected!", Toast.LENGTH_SHORT).show();

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

        // Get a random number out of the three image IDs
        int[] imageIDs = {firstImageID, secondImageID, thirdImageID};
        int carIndex = rand.nextInt(imageIDs.length);
        carBrandIndex = imageIDs[carIndex];

    }

    public void setCarBrandTextView() {

        String[] carBrandArray = getResources().getStringArray(R.array.car_brand_names);
        carBrandTextView.setText(carBrandArray[carBrandIndex]);//setting one car brand name in the textview out of three car images in the imageviews
    }

    public void generateNextImage() {
        if (imageIndexList.size() == 30) {
            //show in toast saying no more images
            Toast.makeText(IdentifyCarImage.this, "No more Images", Toast.LENGTH_SHORT).show();

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

            // Get a random number out of the three image IDs
            int[] imageIDs = {firstImageID, secondImageID, thirdImageID};
            int carIndex = rand.nextInt(imageIDs.length);
            carBrandIndex = imageIDs[carIndex];
        }
    }

    public void setListenerOnImageView() {

        firstCarImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (count > 0) {

                    MainActivity.isTimerOn = false; // Stop the timer
                    firstCarImageView.setAlpha(0.7f);

                    firstCarImageView.setOnClickListener(null);
                    secondCarImageView.setOnClickListener(null);
                    thirdCarImageView.setOnClickListener(null);

                    if (carBrandIndex == firstImageID) {
                        resultTextView.setTextColor(Color.parseColor("#00FF00"));
                        resultTextView.setText("Correct");
                    } else {
                        resultTextView.setText("Wrong");
                        resultTextView.setTextColor(Color.parseColor("#FF0000"));
                    }
                }
                else {
                    Toast.makeText(IdentifyCarImage.this, "Timeout, please tap Next to restart", Toast.LENGTH_LONG).show();
                    }


            }
        });

        secondCarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count > 0) {

                    MainActivity.isTimerOn = false; // Stop the timer
                    secondCarImageView.setAlpha(0.7f);

                    firstCarImageView.setOnClickListener(null);
                    secondCarImageView.setOnClickListener(null);
                    thirdCarImageView.setOnClickListener(null);

                    if (carBrandIndex == secondImageID) {
                        resultTextView.setTextColor(Color.parseColor("#00FF00"));
                        resultTextView.setText("Correct");
                    } else {
                        resultTextView.setText("Wrong");
                        resultTextView.setTextColor(Color.parseColor("#FF0000"));
                    }
                }
                else {
                    Toast.makeText(IdentifyCarImage.this, "Timeout, please tap Next to restart", Toast.LENGTH_LONG).show();
                }


            }
        });

        thirdCarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count > 0) {

                    MainActivity.isTimerOn = false; // Stop the timer
                    thirdCarImageView.setAlpha(0.7f);

                    firstCarImageView.setOnClickListener(null);
                    secondCarImageView.setOnClickListener(null);
                    thirdCarImageView.setOnClickListener(null);

                    if (carBrandIndex == thirdImageID) {
                        resultTextView.setTextColor(Color.parseColor("#00FF00"));
                        resultTextView.setText("Correct");
                    } else {
                        resultTextView.setText("Wrong");
                        resultTextView.setTextColor(Color.parseColor("#FF0000"));
                    }
                }
                else {
                    Toast.makeText(IdentifyCarImage.this, "Timeout, please tap Next to restart", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    // Setup the onclick listener for the next button
    public void setListenerOnNextButton() {

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MainActivity.isTimerOn = MainActivity.checkTimerStatus;
                generateNextImage();
                setListenerOnImageView();
                setCarBrandTextView();

                firstCarImageView.setAlpha(1.0f);
                secondCarImageView.setAlpha(1.0f);
                thirdCarImageView.setAlpha(1.0f);

                resultTextView.setText("");
                timerTextView.setText("20");

                setupTimer();

            }
        });

    }

}