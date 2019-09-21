package com.example.kodokode;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {

    private User user;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    openHomePage();
                    break;
                case R.id.navigation_leaderboard:
                    openLeaderboardPage();
                    break;
                case R.id.navigation_userprofile:
                    //do nothing, stay on current page
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user = getIntent().getParcelableExtra("USER");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Your Profile");
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_userprofile);

        TextView usernameTextView = findViewById(R.id.username);
        TextView pointsTextView = findViewById(R.id.points);
        TextView chaptersTextView = findViewById(R.id.chapters);
        TextView quizzesTextView = findViewById(R.id.quizzes);
        usernameTextView.setText(user.getUsername());
        pointsTextView.setText("Your points: " + user.getPoints());
        chaptersTextView.setText("Chapters completed: " + user.getChaptersCompleted() + "/3");
        quizzesTextView.setText("Quizzes completed: " + user.getQuizzesCompleted() + "/3");
    }

    public void openHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void openLeaderboardPage() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

}
