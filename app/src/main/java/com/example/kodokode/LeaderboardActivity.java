package com.example.kodokode;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kodokode.ui.login.UsersDbHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private static final String TAG = "LeaderboardActivity";

    private User user;

    private List<User> rankedUsersList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    openHomePage();
                    break;
                case R.id.navigation_leaderboard:
                    //do nothing, stay on current page
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
        setContentView(R.layout.activity_leaderboard);

        user = getIntent().getParcelableExtra("USER");
        if (user == null) {
            Log.d(TAG, "user is null");
        }

        rankedUsersList = getRankedUsersList();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Leaderboard");
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_leaderboard);

        TextView pointsTextView = findViewById(R.id.points_text);
        //TextView rankingTextView = findViewById(R.id.ranking_text);
        pointsTextView.setText("Your points: " + user.getPoints());

        initRecyclerView();

    }

    public void openHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void openUserProfilePage() {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    // ranks users in descending order (most to least) in terms of points
    public List<User> getRankedUsersList() {
        UsersDbHelper usersDbHelper = new UsersDbHelper(this);
        List<User> usersList = usersDbHelper.getAllUsers();
        Collections.sort(usersList); // rank users in ascending order
        Collections.reverse(usersList); // reverse list so list is in descending order
        return usersList;
    }

    // initialise recycler view by calling LeaderboardRecyclerAdapter class
    public void initRecyclerView() {
        Log.d(TAG, "init recycler view");
        RecyclerView recyclerView = findViewById(R.id.recycler_view_leaderboard);
        LeaderboardRecyclerAdapter adapter = new LeaderboardRecyclerAdapter(rankedUsersList, user);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
