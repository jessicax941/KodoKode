package com.example.kodokode.ui.login;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kodokode.MyLoginActivity;
import com.example.kodokode.HomeActivity;
import com.example.kodokode.R;
import com.example.kodokode.User;
import com.example.kodokode.ui.login.UsersContract.*;

public class CreateAccountActivity extends AppCompatActivity {

    TextInputLayout usernameTextInput;
    TextInputLayout emailTextInput;
    TextInputLayout password1TextInput;
    TextInputLayout password2TextInput;
    String username;
    String email;
    String password1;
    String password2;
    Button createAccountButton;
    TextView signInLink;
    ProgressBar progressBar;

    private User user;

    private UsersDbHelper dbHelper;

    private static final String TAG = "CreateAccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameTextInput = findViewById(R.id.username_prompt);
        final EditText usernameEditText = usernameTextInput.getEditText();

        emailTextInput = findViewById(R.id.email_prompt);
        final EditText emailEditText = emailTextInput.getEditText();

        password1TextInput = findViewById(R.id.pw_prompt1);
        final EditText pw1EditText = password1TextInput.getEditText();

        password2TextInput = findViewById(R.id.pw_prompt2);
        final EditText pw2EditText = password2TextInput.getEditText();

        createAccountButton = findViewById(R.id.create_button);

        signInLink = findViewById(R.id.sign_in_link);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        Typeface typeface1 = pw1EditText.getTypeface();
        pw1EditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pw1EditText.setTypeface(typeface1);
        pw1EditText.setTransformationMethod(new PasswordTransformationMethod());
        password1TextInput.setPasswordVisibilityToggleEnabled(true);

        Typeface typeface2 = pw2EditText.getTypeface();
        pw2EditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pw2EditText.setTypeface(typeface2);
        pw2EditText.setTransformationMethod(new PasswordTransformationMethod());
        password2TextInput.setPasswordVisibilityToggleEnabled(true);

        dbHelper = new UsersDbHelper(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = usernameEditText.getText().toString().trim();
                email = emailEditText.getText().toString().trim();
                password1 = pw1EditText.getText().toString().trim();
                password2 = pw2EditText.getText().toString().trim();

                if (validate(username, email, password1, password2)) {
                    // successful sign up
                    createAccountButton.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    dbHelper.insertUser(username, email, password2);
                                    user = new User(username, password2, true);
                                    //insertUser(username, email, password2);
                                    //database.close();
                                    progressBar.setVisibility(View.GONE);
                                    setResult(RESULT_OK);
                                    Toast.makeText(getApplicationContext(), "sign up successful",
                                            Toast.LENGTH_LONG).show();
                                    openHomePage();
                                }
                            },
                            3000);

                    createAccountButton.setEnabled(true);
                    // finish();

                } else {
                    signUpFailed();
                }
            }
        });

        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginPage();
            }
        });
    }

    public boolean validate(String username, String email, String pw1, String pw2) {
        boolean isValid = true;

        // check if fields are empty
        if (username.isEmpty()) {
            isValid = false;
            usernameTextInput.setError("Field cannot be empty");
        } else {
            usernameTextInput.setError(null);
        }

        if (email.isEmpty()) {
            isValid = false;
            emailTextInput.setError("Field cannot be empty");
        } else {
            emailTextInput.setError(null);
        }

        if (pw1.isEmpty()) {
            isValid = false;
            password1TextInput.setError("Field cannot be empty");
        } else {
            password1TextInput.setError(null);
        }

        if (pw2.isEmpty()) {
            isValid = false;
            password2TextInput.setError("Field cannot be empty");
        } else {
            password2TextInput.setError(null);
        }

        // check if email is valid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false;
            emailTextInput.setError("Please enter a valid email address");
        } else {
            emailTextInput.setError(null);
        }

        // check if password 1 and 2 are the same
        if (!pw1.equals(pw2)) {
            isValid = false;
            password2TextInput.setError("Passwords are not the same");
        } else {
            password2TextInput.setError(null);
        }

//         check if username is at least 6 characters
//
//         check if password is at least 6 characters

        return isValid;
    }

//    public void insertUser(String username, String email, String password) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(UsersTable.COLUMN_USERNAME, username);
//        contentValues.put(UsersTable.COLUMN_EMAIL, email);
//        contentValues.put(UsersTable.COLUMN_PASSWORD, password);
//        contentValues.put(UsersTable.COLUMN_CHAPTERS, 0);
//        contentValues.put(UsersTable.COLUMN_QUIZZES, 0);
//        contentValues.put(UsersTable.COLUMN_QUIZ1_ATTEMPTS, 0);
//        long newRowID = database.insert(UsersTable.TABLE_NAME, null, contentValues);
//    }

    public void signUpFailed() {
        Toast.makeText(this, "Sign up unsuccessful", Toast.LENGTH_LONG).show();
        createAccountButton.setEnabled(true);
    }

    public void openHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        // intent.putExtra("USERNAME", username);
        // intent.putExtra("FIRST_SESSION", true);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, MyLoginActivity.class);
        startActivity(intent);
    }
}
