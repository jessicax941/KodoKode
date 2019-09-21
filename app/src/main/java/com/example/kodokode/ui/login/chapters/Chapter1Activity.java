package com.example.kodokode.ui.login.chapters;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.kodokode.LearningSection;
import com.example.kodokode.R;
import com.example.kodokode.User;

public class Chapter1Activity extends AppCompatActivity {

    private User user;

    private static final String TAG = "Chapter1Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked on up button to go back to learning section");
                goBackToLearningSection();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Chapter 1. Variables and Types");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = getIntent().getParcelableExtra("USER");

        Chapter1Fragment1 chapter1Fragment1 = new Chapter1Fragment1();
        Bundle args = new Bundle();
        args.putParcelable("USER", user);
        chapter1Fragment1.setArguments(args);

        // opens the first fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, chapter1Fragment1)
                .commit();

    }

    public User getUser() {
        return this.user;
    }

    //starts activity to go back to learning section
    public void goBackToLearningSection() {
        Intent intent = new Intent(this, LearningSection.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "clicked on back button to go back to learning section");
        goBackToLearningSection();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            goBackToLearningSection();
        }

        return super.onOptionsItemSelected(item);
    }
}
