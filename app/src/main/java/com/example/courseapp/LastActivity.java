package com.example.courseapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class LastActivity extends AppCompatActivity {
    private static String TAG = "LastActivity";

    /* firebase variables initialisation */
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("ratings");


    private SeekBar examplesOnLessonsSeekBar, jobOpportunitiesSeekBar;
    private TextView examplesOnLessonsTextView, jobOpportunitiesTextView, totalNumberOfRatings, ratingAverage, totalAverage;
    private EditText commentsEditText;
    private Rating ratingFromMainActivity;
    private Rating finalRatingToSend;
    private Double averageScore;
    private int seedBarsTotal = 6;
    private int totalAmountOfRatings = 0;
    private Double totalAverageDouble = 0.0;
    private Double averageDouble = 0.0;
    private Boolean isCommentFull = false; // comment validation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

        // getting intent from main activity and assigning to local the variable so therefore we can create a final one
        ratingFromMainActivity = getIntent().getParcelableExtra(MainActivity.RAT);
        Log.i(TAG, "Got" + ratingFromMainActivity.toStringFull());

        // initialisation of the components
        init();
    }

    public void showHistory(View view) {
        if (minCharactersValidation()) {
            // setting the text in the textView - average of one rating
            ratingAverage.setText(String.valueOf(averageScore()));

            /* saving new object to the database */
            // getting unique generated key
            String id = ref.push().getKey();

            // creation of the object. First half is taken from the intent
            finalRatingToSend = new Rating(id,
                    ratingFromMainActivity.getCourseName(),
                    ratingFromMainActivity.getSubjectRelevance(),
                    ratingFromMainActivity.getTeacherPerformance(),
                    ratingFromMainActivity.getTeacherPreparation(),
                    ratingFromMainActivity.getGivenFeedback(),
                    Integer.parseInt(String.valueOf(examplesOnLessonsTextView.getText())),
                    Integer.parseInt(String.valueOf(jobOpportunitiesTextView.getText())),
                    String.valueOf(commentsEditText.getText()),
                    averageScore()
            );

            // setting this value to the Firebase database
            ref.child(finalRatingToSend.getId()).setValue(finalRatingToSend);


            /* retrieving the data from a database */
            // Listener applied to database reference is needed so it can be possible to track changes
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //for each loop for each data "row" in database
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        totalAmountOfRatings++;
                        totalNumberOfRatings.setText(String.valueOf(totalAmountOfRatings));

                        // add averages together
                        Double average = ds.child("averageRating").getValue(Double.class);
                        averageDouble += average;
                    }

                    // counting total average of all the ratings
                    totalAverageDouble = averageDouble / totalAmountOfRatings;
                    DecimalFormat df = new DecimalFormat("#.##");

                    // putting the text to the textview
                    totalAverage.setText(String.valueOf(df.format(totalAverageDouble)));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            // assigning the listener to the database reference
            ref.addListenerForSingleValueEvent(eventListener);
        }
    }

    // using intent to send a mail
    public void sendClicked(View view) {
        Log.i(TAG, "sendMail() clicked");
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"michal.moravik@icloud.com", "michal.moravik@icloud.com"}); // the second could be school email
        String htmlContent =
                "<p>New Rating! The average score is: " + averageScore() + "</p>" ;

        email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(htmlContent));
        startActivity(Intent.createChooser(email, "Choose an email client"));
    }

    public void init() {
        examplesOnLessonsSeekBar = findViewById(R.id.examplesSeekBar);
        examplesOnLessonsTextView = findViewById(R.id.examplesTextView);
        jobOpportunitiesSeekBar = findViewById(R.id.jobsSeekBar);
        jobOpportunitiesTextView = findViewById(R.id.jobsTextView);
        commentsEditText = findViewById(R.id.commentsEditText);
        totalNumberOfRatings = findViewById(R.id.totalNumberOfRatings);
        ratingAverage = findViewById(R.id.ratingAverage);
        totalAverage = findViewById(R.id.totalAverage);

        seekBarsInitListener(examplesOnLessonsSeekBar, examplesOnLessonsTextView);
        seekBarsInitListener(jobOpportunitiesSeekBar, jobOpportunitiesTextView);
    }

    /* seek bar progress listener sets number as a text in textView after a user "moves" seek bar */
    public void seekBarsInitListener(SeekBar seekBar, final TextView textView) {
        // listener initialisation
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                textView.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    /* editText validation - min characters */
    public boolean minCharactersValidation() {
        String ecommentsEditTextString=commentsEditText .getText().toString();
        int size=ecommentsEditTextString.length();

        if(size<100) {
            Toast.makeText(LastActivity.this, "Comment needs to contain at least 100 caracters", Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }

    // calculate average score of the rating
    public Double averageScore() {
        averageScore = (double) ((ratingFromMainActivity.getSubjectRelevance() + ratingFromMainActivity.getTeacherPerformance() + ratingFromMainActivity.getTeacherPreparation() +
                ratingFromMainActivity.getGivenFeedback() + Integer.parseInt(String.valueOf(examplesOnLessonsTextView.getText())) +
                Integer.parseInt(String.valueOf(jobOpportunitiesTextView.getText()))) / seedBarsTotal);
        return averageScore;
    }
}
