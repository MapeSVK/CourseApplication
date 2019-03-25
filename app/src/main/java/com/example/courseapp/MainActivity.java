package com.example.courseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
	private static String TAG = "MainActivity";

	private SeekBar subjectRelevanceSeekBar, teacherPerformanceSeekBar, teacherPreparationSeekBar, feedbackSeekBar;
	private TextView subjectRelevanceTextView, teacherPerformanceTextView, teacherPreparationTextView, feedbackTextView;
	private Spinner coursesSpinner;

	// courses which can be picked
	private ArrayList<String> courseArrayList;

	// final rating after the first activity
	private Rating firstActivityRating;

	// intent
	public static final String RAT = "rat";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate() called");
		setContentView(R.layout.activity_main);

		init();
		initCourses();
	}

	/* new rating object is created after "next" button is clicked and moved to second activity using intent */
	public void nextButtonClicked(View view) {
		Log.d(TAG, "nextButtonClicked() called");

		// firstly get the course user picked by simply getting item from the spinner
		String selectedCourseName = (String) ( (Spinner) findViewById(R.id.coursesSpinner) ).getSelectedItem();

		// assign values and create constructor
		firstActivityRating = new Rating(
				selectedCourseName,
				Integer.parseInt(String.valueOf(subjectRelevanceTextView.getText())),
				Integer.parseInt(String.valueOf(teacherPerformanceTextView.getText())),
				Integer.parseInt(String.valueOf(teacherPreparationTextView.getText())),
				Integer.parseInt(String.valueOf(feedbackTextView.getText())));

		Log.d(TAG, "object sent: " + firstActivityRating.toStringFull());

		// calling method and saving rating into the intent
		createIntent(firstActivityRating);
	}

	/* intent creation */
	public void createIntent(Rating ratingAfterFirstActivity) {
		Intent intent = new Intent(this, LastActivity.class);
		intent.putExtra(RAT, ratingAfterFirstActivity);
		startActivity(intent);
	}

	/* assigning arrayList, just to have 4 courses to pick from */
	public void initCourses() {
		courseArrayList = new ArrayList<>();
		courseArrayList.add("Android App");
		courseArrayList.add("iOS");
		courseArrayList.add("Python");
		courseArrayList.add("Angular");

		ArrayAdapter<String> coursesAdapter = new ArrayAdapter<>(this, R.layout.spinner, courseArrayList);
		coursesSpinner.setAdapter(coursesAdapter);
	}

	/* initialisation - connection with the UI */
	public void init() {
		// variable initialisation
		subjectRelevanceSeekBar = findViewById(R.id.subjectRelevanceSeekBar);
		subjectRelevanceTextView = findViewById(R.id.subjectRelevanceTextView);
		teacherPerformanceSeekBar = findViewById(R.id.teacherPerformanceSeekBar);
		teacherPerformanceTextView = findViewById(R.id.teacherPerformanceTextView);
		teacherPreparationSeekBar = findViewById(R.id.teacherPreparationSeekBar);
		teacherPreparationTextView = findViewById(R.id.teacherPreparationTextView);
		feedbackSeekBar = findViewById(R.id.feedbackSeekBar);
		feedbackTextView = findViewById(R.id.feedbackTextView);

		coursesSpinner = findViewById(R.id.coursesSpinner);

		seekBarsInitListener(subjectRelevanceSeekBar, subjectRelevanceTextView);
		seekBarsInitListener(teacherPerformanceSeekBar, teacherPerformanceTextView);
		seekBarsInitListener(teacherPreparationSeekBar, teacherPreparationTextView);
		seekBarsInitListener(feedbackSeekBar, feedbackTextView);
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
}

