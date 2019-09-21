package com.example.kodokode.ui.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.kodokode.User;
import com.example.kodokode.ui.login.UsersContract.*;

import java.util.ArrayList;
import java.util.List;

public class UsersDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase database;

    private static final String TAG = "UsersDbHelper";

    public UsersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.database = db;
        
        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " +
                UsersTable.TABLE_NAME + " ( " +
                UsersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsersTable.COLUMN_USERNAME + " TEXT, " +
                UsersTable.COLUMN_EMAIL + " TEXT, " +
                UsersTable.COLUMN_PASSWORD + " TEXT, " +
                UsersTable.COLUMN_POINTS + " INTEGER, " +
                UsersTable.COLUMN_CHAPTERS + " INTEGER, " +
                UsersTable.COLUMN_QUIZZES + " INTEGER, " +
                UsersTable.COLUMN_QUIZ1 + " INTEGER" +
                ")";

        //UsersTable.COLUMN_QUIZ1_MASTERY + " INTEGER" +

        database.execSQL(SQL_CREATE_USERS_TABLE);
       //
    }

    public boolean isUserValid(String username, String pw) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT username, password FROM " + UsersTable.TABLE_NAME + " WHERE username = ? AND password = ?", new String[] { username, pw });
        if (cursor.moveToLast()) {
            // such a user with the corresponding pw exists
            cursor.close();
            return true;
        } else {
            // such a user with the corresponding pw does not exist
            cursor.close();
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + UsersTable.TABLE_NAME);
        onCreate(database);
    }

    // insert user details
    public void insertUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersTable.COLUMN_USERNAME, username);
        contentValues.put(UsersTable.COLUMN_EMAIL, email);
        contentValues.put(UsersTable.COLUMN_PASSWORD, password);
        contentValues.put(UsersTable.COLUMN_CHAPTERS, 0);
        contentValues.put(UsersTable.COLUMN_QUIZZES, 0);
        contentValues.put(UsersTable.COLUMN_QUIZ1, 0);
        long newRowID = db.insert(UsersTable.TABLE_NAME, null, contentValues);
        Log.d(TAG, "successfully inserted user: " + username);
        db.close();
    }

    // returns user with the associated username and password
    // returns null if user with the username and password is not to be found
    public User getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase(); //
        String query = "SELECT username, password, points, chapters, quizzes, quiz1 FROM " + UsersTable.TABLE_NAME + " WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[] { username, password });

        if (cursor.moveToFirst()) {
            int points = cursor.getInt(cursor.getColumnIndex(UsersContract.UsersTable.COLUMN_POINTS));
            int chaptersCompleted = cursor.getInt(cursor.getColumnIndex(UsersContract.UsersTable.COLUMN_CHAPTERS));
            int quizzesCompleted = cursor.getInt(cursor.getColumnIndex(UsersContract.UsersTable.COLUMN_QUIZZES));
            int quiz1Attempts = cursor.getInt(cursor.getColumnIndex(UsersContract.UsersTable.COLUMN_QUIZ1));
            User user = new User(username, password, points, false);
            user.setChaptersCompleted(chaptersCompleted);
            user.setQuizzesCompleted(quizzesCompleted);
            user.setQuiz1Attempts(quiz1Attempts);
            cursor.close();
            Log.d(TAG, "user found: " + user.getUsername());
            return user;
        } else {
            // cursor is empty, user not found
            Log.d(TAG, "user not found: " + username);
            return null;
        }
    }

    // returns user with the associated username and password
    // returns null if user with the username and password is not to be found
    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT username, password, points, chapters, quizzes, quiz1 FROM " + UsersTable.TABLE_NAME + " WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[] { username });

        if (cursor.moveToLast()) {
            int points = cursor.getInt(cursor.getColumnIndex(UsersContract.UsersTable.COLUMN_POINTS));
            int chaptersCompleted = cursor.getInt(cursor.getColumnIndex(UsersContract.UsersTable.COLUMN_CHAPTERS));
            int quizzesCompleted = cursor.getInt(cursor.getColumnIndex(UsersContract.UsersTable.COLUMN_QUIZZES));
            int quiz1Attempts = cursor.getInt(cursor.getColumnIndex(UsersContract.UsersTable.COLUMN_QUIZ1));
            User user = new User(username, null, points, false);
            user.setChaptersCompleted(chaptersCompleted);
            user.setQuizzesCompleted(quizzesCompleted);
            user.setQuiz1Attempts(quiz1Attempts);
            cursor.close();
            Log.d(TAG, "user found: " + username);
            return user;
        } else {
            Log.d(TAG, "user not found: " + username);
            return null;
        }
    }

    // for leaderboard feature
    // gets all users in the database
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + UsersTable.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_PASSWORD));
                int points = cursor.getInt(cursor.getColumnIndex(UsersTable.COLUMN_POINTS));
                User user = new User(username, password, false);
                user.setPoints(points);
                usersList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return usersList;
    }

    // called when user did not get full marks for quiz
    // update points in database
    // returns user object with updated points
    public User updatePoints(User user, int points) {
        String username = user.getUsername();
        int oldPoints = user.getPoints();
        int newPoints = oldPoints + points;
        user.setPoints(newPoints);

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + UsersTable.TABLE_NAME
                + " SET " + UsersTable.COLUMN_POINTS + " = '" + newPoints + "' WHERE "
                + UsersTable.COLUMN_USERNAME + " = '" + username + "'";
        db.execSQL(query);
        Log.d(TAG, "update points for " + username + " from "
                + oldPoints + " to " + newPoints);

        return user;
    }

    // increases number of chapters completed for that user by one and increases points
    public User updateChaptersCompleted(User user) {
        String username = user.getUsername();
        int oldChaptersCompleted = user.getChaptersCompleted();
        int newChaptersCompleted = oldChaptersCompleted + 1;
        user.setChaptersCompleted(newChaptersCompleted);

        SQLiteDatabase db = this.getWritableDatabase();
        // update number of chapters completed in database
        String query1 = "UPDATE " + UsersTable.TABLE_NAME
                + " SET " + UsersTable.COLUMN_CHAPTERS + " = '" + newChaptersCompleted + "' WHERE "
                + UsersTable.COLUMN_USERNAME + " = '" + username + "'";
        db.execSQL(query1);
        Log.d(TAG, "update chapters completed for " + username + " from "
                + oldChaptersCompleted + " to " + newChaptersCompleted);

        int oldPoints = user.getPoints();
        int newPoints = oldPoints + 10;
        user.setPoints(newPoints);

        //update user's points in database
        String query2 = "UPDATE " + UsersTable.TABLE_NAME
                + " SET " + UsersTable.COLUMN_POINTS + " = '" + newPoints + "' WHERE "
                + UsersTable.COLUMN_USERNAME + " = '" + username + "'";
        db.execSQL(query2);
        Log.d(TAG, "update points for " + username + " from "
                + oldPoints + " to " + newPoints);

        return user;
    }

    // called when user got full marks for a quiz
    // increases number of quizzes completed for that user by one and add points
    public User updateQuizzesCompleted(User user) {
        String username = user.getUsername();
        int oldQuizzesCompleted = user.getQuizzesCompleted();
        int newQuizzesCompleted = oldQuizzesCompleted + 1;
        user.setQuizzesCompleted(newQuizzesCompleted);

        SQLiteDatabase db = this.getWritableDatabase();
        // update number of quizzes completed in database
        String query1 = "UPDATE " + UsersTable.TABLE_NAME
                + " SET " + UsersTable.COLUMN_QUIZZES + " = '" + newQuizzesCompleted
                + "' WHERE " + UsersTable.COLUMN_USERNAME + " = '" + username + "'";
        Log.d(TAG, "update quizzes completed for " + username + " from "
                + oldQuizzesCompleted + " to " + newQuizzesCompleted);
        db.execSQL(query1);

        int oldPoints = user.getPoints();
        int newPoints = oldPoints + 10;
        user.setPoints(newPoints);

        //update user's points in database
        String query2 = "UPDATE " + UsersTable.TABLE_NAME
                + " SET " + UsersTable.COLUMN_POINTS + " = '" + newPoints + "' WHERE "
                + UsersTable.COLUMN_USERNAME + " = '" + username + "'";
        db.execSQL(query2);
        Log.d(TAG, "update points for " + username + " from "
                + oldPoints + " to " + newPoints);
        return user;
    }


}
