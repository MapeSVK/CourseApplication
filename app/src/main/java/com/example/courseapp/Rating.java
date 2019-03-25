package com.example.courseapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Rating implements Parcelable {

	// first activity
	private String id;
	private String courseName;
	private int subjectRelevance;
	private int teacherPerformance;
	private int teacherPreparation;
	private int givenFeedback;

	// last activity
	private int givenExamples;
	private int jobOpportunities;
	private String comments;

	private double averageRating;

	public Rating() {
	}

	// first activity constructor
	public Rating(String courseName, int subjectRelevance, int teacherPerformance, int teacherPreparation, int givenFeedback) {
		this.id = id;
		this.courseName = courseName;
		this.subjectRelevance = subjectRelevance;
		this.teacherPerformance = teacherPerformance;
		this.teacherPreparation = teacherPreparation;
		this.givenFeedback = givenFeedback;
	}

	// after second activity constructor = final object
	public Rating(String id, String courseName, int subjectRelevance, int teacherPerformance, int teacherPreparation, int givenFeedback, int givenExamples, int jobOpportunities, String comments, Double averageRating) {
		this.id = id;
		this.courseName = courseName;
		this.subjectRelevance = subjectRelevance;
		this.teacherPerformance = teacherPerformance;
		this.teacherPreparation = teacherPreparation;
		this.givenFeedback = givenFeedback;
		this.givenExamples = givenExamples;
		this.jobOpportunities = jobOpportunities;
		this.comments = comments;
		this.averageRating = averageRating;
	}

	protected Rating(Parcel in) {
		id = in.readString();
		averageRating = in.readDouble();
		courseName = in.readString();
		subjectRelevance = in.readInt();
		teacherPerformance = in.readInt();
		teacherPreparation = in.readInt();
		givenFeedback = in.readInt();
		givenExamples = in.readInt();
		jobOpportunities = in.readInt();
		comments = in.readString();
	}

	public static final Creator<Rating> CREATOR = new Creator<Rating>() {
		@Override
		public Rating createFromParcel(Parcel in) {
			return new Rating(in);
		}

		@Override
		public Rating[] newArray(int size) {
			return new Rating[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(id);
		parcel.writeDouble(averageRating);
		parcel.writeString(courseName);
		parcel.writeInt(subjectRelevance);
		parcel.writeInt(teacherPerformance);
		parcel.writeInt(teacherPreparation);
		parcel.writeInt(givenFeedback);
		parcel.writeInt(givenExamples);
		parcel.writeInt(jobOpportunities);
		parcel.writeString(comments);
	}


    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getSubjectRelevance() {
		return subjectRelevance;
	}

	public void setSubjectRelevance(int subjectRelevance) {
		this.subjectRelevance = subjectRelevance;
	}

	public int getTeacherPerformance() {
		return teacherPerformance;
	}

	public void setTeacherPerformance(int teacherPerformance) {
		this.teacherPerformance = teacherPerformance;
	}

	public int getTeacherPreparation() {
		return teacherPreparation;
	}

	public void setTeacherPreparation(int teacherPreparation) {
		this.teacherPreparation = teacherPreparation;
	}

	public int getGivenFeedback() {
		return givenFeedback;
	}

	public void setGivenFeedback(int givenFeedback) {
		this.givenFeedback = givenFeedback;
	}

	public int getGivenExamples() {
		return givenExamples;
	}

	public void setGivenExamples(int givenExamples) {
		this.givenExamples = givenExamples;
	}

	public int getJobOpportunities() {
		return jobOpportunities;
	}

	public void setJobOpportunities(int jobOpportunities) {
		this.jobOpportunities = jobOpportunities;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return this.courseName;
	}

	public String toStringFull() {
		return "Rating{" +
				"id=" + id +
				", courseName='" + courseName + '\'' +
				", subjectRelevance=" + subjectRelevance +
				", teacherPerformance=" + teacherPerformance +
				", teacherPreparation=" + teacherPreparation +
				", givenFeedback=" + givenFeedback +
				", givenExamples=" + givenExamples +
				", jobOpportunities=" + jobOpportunities +
				", comments=" + comments +
				'}';
	}
}
