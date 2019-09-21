package com.example.kodokode;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private User user;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
        = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //do nothing, stay on current page
                    break;
                case R.id.navigation_leaderboard:
                    openLeaderboardPage();
                    break;
                case R.id.navigation_userprofile:
                    openUserProfilePage();
                    break;
                }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Home");

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button learningButton = findViewById(R.id.learningButton);
        learningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLearningSection();
            }
        });
        Button assessmentButton = findViewById(R.id.assessmentButton);
        assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAssessmentSection();
            }
        });

        TextView welcomeTextView = findViewById(R.id.welcomeText);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("USER");
        String username = user.getUsername();
        welcomeTextView.setText("Welcome, " + username + "!");

        int points = user.getPoints();
        TextView pointsTextView = findViewById(R.id.pointsText);
        pointsTextView.setText("Points: " + points);

//        if (intent.hasExtra("USERNAME")) {
//            username = intent.getStringExtra("USERNAME");
//            if (intent.getBooleanExtra("FIRST_SESSION", true)) {
//                welcomeTextView.setText("Welcome, " + username + "!");
//            } else {
//                // this is not the first session for user
//                welcomeTextView.setText("Welcome back, " + username + "!");
//            }
//        } else {
//            // no username
//            welcomeTextView.setText("Welcome!");
//        }

    }

    public void openLeaderboardPage() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void openUserProfilePage() {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void openLearningSection() {
        Intent intent = new Intent(this, LearningSection.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void openAssessmentSection() {
        Intent intent = new Intent(this, AssessmentSection.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the main activity
        moveTaskToBack(true);
    }
}
