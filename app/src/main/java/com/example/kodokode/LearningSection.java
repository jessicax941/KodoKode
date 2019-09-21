package com.example.kodokode;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.kodokode.ui.login.chapters.Chapter1Activity;

import java.util.ArrayList;

public class LearningSection extends AppCompatActivity implements ChapterRecyclerAdapter.OnChapterListener {

    private static final String TAG = "LearningSection";

    private User user;

    private ArrayList<String> chapterNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_section);

        user = getIntent().getParcelableExtra("USER");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked to go back to home page");
                goBackToHomePage();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Chapters");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView chaptersTextView = findViewById(R.id.chapters_count);
        chaptersTextView.setText("Chapters Completed: " + user.getChaptersCompleted() + "/3");

        //Log.d(TAG, "onCreate: started.");
        initChapters();
    }

    private void initChapters() {
        chapterNames.add("Chapter 1. Variables and Types");
        chapterNames.add("Chapter 2. Basic Operators");
        chapterNames.add("Chapter 3. Input and Output");
        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recycler view");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ChapterRecyclerAdapter adapter = new ChapterRecyclerAdapter(chapterNames, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onChapterClick(int position) {
        chapterNames.get(position);
        Intent intent = new Intent(this, Chapter1Activity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void goBackToHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            goBackToHomePage();
        }

        return super.onOptionsItemSelected(item);
    }

}
