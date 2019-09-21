package com.example.kodokode;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kodokode.ui.login.CreateAccountActivity;
import com.example.kodokode.ui.login.UsersContract;
import com.example.kodokode.ui.login.UsersDbHelper;

public class MyLoginActivity extends AppCompatActivity {

    private TextInputLayout usernameTextInput;
    private TextInputLayout pwTextInput;
    private EditText usernameEditText;
    private EditText pwEditText;
    private String usernameInput;
    private String passwordInput;
    private Button signInButton;
    private TextView signUpView;
    private ProgressBar progressBar;

    private UsersDbHelper dbHelper;
    private SQLiteDatabase database;

    private User user;

    private static final int REQUEST_SIGNUP = 0;

    private static final String TAG = "MyLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Sign In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameTextInput = findViewById(R.id.username_prompt);
        usernameEditText = usernameTextInput.getEditText();
        pwTextInput = findViewById(R.id.pw_prompt);
        pwEditText = pwTextInput.getEditText();

        Typeface typeface = pwEditText.getTypeface();
        pwEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pwEditText.setTypeface(typeface);
        pwEditText.setTransformationMethod(new PasswordTransformationMethod());
        pwTextInput.setPasswordVisibilityToggleEnabled(true);

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        signUpView = findViewById(R.id.sign_up_link);
        signUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        //this.deleteDatabase("users.db");
        dbHelper = new UsersDbHelper(this);

    }

    public void signIn() {

        usernameInput = usernameEditText.getText().toString().trim();
        passwordInput = pwEditText.getText().toString().trim();

        if (!validate()) {
            onSignInFailed();
            return;
        }

        // if valid user details
        signInButton.setEnabled(false);

        progressBar.setVisibility(View.VISIBLE);

        // TODO: implement own authentication logic here

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        onSignInSuccess();
                        progressBar.setVisibility(View.GONE);
                        openHomePage();
                    }
                },
        3000);
    }

    // validates the username and password input by user
    public boolean validate() {
        boolean valid = true;

        // check if fields are empty
        if (usernameInput.isEmpty()) {
            usernameTextInput.setError("Field cannot be empty");
            valid = false;
        } else {
            usernameTextInput.setError(null);
        }
        if (passwordInput.isEmpty()) {
            pwTextInput.setError("Field cannot be empty");
            valid = false;
        } else {
            pwTextInput.setError(null);
        }

        // check if user exists
        if (!isUserValid(usernameInput, passwordInput)) {
            usernameTextInput.setError("Username and password cannot be found");
            pwTextInput.setError("Username and password cannot be found");
            valid = false;
        } else {
            //Log.d(TAG, "it fking worked!!!!");
            usernameTextInput.setError(null);
            pwTextInput.setError(null);
        }

        return valid;
    }

    // checks if user alr exists in the database
    public boolean isUserValid(String username, String pw) {
//        database = openHelper.getReadableDatabase();
//        boolean valid;
//
//        Cursor cursor = database.rawQuery(
//                "SELECT username, password, points, chapters completed, quizzes completed, quiz1 attempts FROM " + UsersContract.UsersTable.TABLE_NAME + " WHERE username = ? AND password = ?", new String[] { username, pw });
//        if (cursor.moveToFirst()) {
//            // such a user with the corresponding pw exists
//            valid = true;
//            int points = cursor.getInt(cursor.getColumnIndex(UsersContract.UsersTable.COLUMN_POINTS));
//            int chaptersCompleted = cursor.getInt(cursor.getColumnIndex(UsersContract.UsersTable.COLUMN_CHAPTERS));
//            int quizzesCompleted = cursor.getInt(cursor.getColumnIndex(UsersContract.UsersTable.COLUMN_QUIZZES));
//            int quiz1Attempts = cursor.getInt(cursor.getColumnIndex(UsersContract.UsersTable.COLUMN_QUIZ1_ATTEMPTS));
//            User user = new User(username, pw, points, false);
//            user.setChaptersCompleted(chaptersCompleted);
//            user.setQuizzesCompleted(quizzesCompleted);
//            user.setQuiz1Attempts(quiz1Attempts);
//            this.user = user;
//        } else {
//            // such a user with the corresponding pw does not exist
//            valid = false;
//        }
//
//        cursor.close();
//        database.close();
//        return valid;

        user = dbHelper.getUser(username, pw);

        if (user != null) {
            // user is found
            Log.d(TAG, "user is found: " + user.getUsername());
            return true;
        } else {
            return false;
        }


    }

    // shows toast message that sign in has failed
    public void onSignInFailed() {
        Toast.makeText(getBaseContext(), "Sign in failed", Toast.LENGTH_LONG).show();
        signInButton.setEnabled(true);
    }

    public void onSignInSuccess() {
        signInButton.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Sign in successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: implement successful sign up logic here
                // by default, finish the activity and sign them in automatically
                finish();
            }
        }
    }

    public void openHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        //intent.putExtra("USERNAME", usernameInput);
        //intent.putExtra("FIRST_SESSION", false);
        intent.putExtra("USER", user);
        startActivity(intent);
        Log.d(TAG, "home page opened for: " + user.getUsername());
    }

//    @Override
//    public void onBackPressed() {
//        // disable going back to the main activity
//        moveTaskToBack(true);
//    }

}
