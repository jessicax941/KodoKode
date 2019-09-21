package com.example.kodokode;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

public class User implements Parcelable, Comparable<User> {

    private final String username;
    private final String password;
    private int points;
    private int chaptersCompleted;
    private int quizzesCompleted;
    private int quiz1Attempts;
    private int quiz1Mastery;

    private boolean isNewUser;

    public User(String username, String password, boolean isNewUser) {
        this.username = username;
        this.password = password;
        this.points = 0; // every new user starts with 0 points at the start
        this.isNewUser = isNewUser;
        this.chaptersCompleted = 0;
        this.quizzesCompleted = 0;
        this.quiz1Attempts = 0;
        this.quiz1Mastery = 0;
    }

    public User(String username, String password, int points, boolean isNewUser) {
        this.username = username;
        this.password = password;
        this.points = points; // for current users
        this.isNewUser = isNewUser;
        this.chaptersCompleted = 0;
        this.quizzesCompleted = 0;
        this.quiz1Attempts = 0;
        this.quiz1Mastery = 0;
    }

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        points = in.readInt();
        chaptersCompleted = in.readInt();
        quizzesCompleted = in.readInt();
        quiz1Attempts = in.readInt();
        quiz1Mastery = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPoints() {
        return points;
    }

    public boolean isNewUser() {
        return isNewUser;
    }

    public int getChaptersCompleted() {
        return this.chaptersCompleted;
    }

    public int getQuizzesCompleted() {
        return this.quizzesCompleted;
    }

    public int getQuiz1Attempts() {
        return this.quiz1Attempts;
    }

    public int getQuiz1Mastery() {
        return this.quiz1Mastery;
    }


    public void setPoints(int newPoints) {
        this.points = newPoints;
    }

    public void setIsNewUser(boolean isNewUser) {
        this.isNewUser = isNewUser;
    }

    public void setChaptersCompleted(int chaptersCompleted) {
        this.chaptersCompleted = chaptersCompleted;
    }

    public void setQuizzesCompleted(int quizzesCompleted) {
        this.quizzesCompleted = quizzesCompleted;
    }

    public void setQuiz1Attempts(int attempts) {
        this.quiz1Attempts = attempts;
    }

    public void setQuiz1Mastery(int mastery) {
        this.quiz1Mastery = mastery;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeInt(points);
        dest.writeInt(chaptersCompleted);
        dest.writeInt(quizzesCompleted);
        dest.writeInt(quiz1Attempts);
        dest.writeInt(quiz1Mastery);
    }

    public int compareTo(User user) {
        return this.getPoints() - user.getPoints();
    }

}
