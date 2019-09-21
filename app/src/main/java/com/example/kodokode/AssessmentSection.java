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
import android.widget.TextView;

import com.example.kodokode.ui.login.quizzes.StartQuiz1Activity;

import java.util.ArrayList;

public class AssessmentSection extends AppCompatActivity implements QuizRecyclerAdapter.OnQuizListener {

    private static final String TAG = "AssessmentSection";

    private ArrayList<String> quizNames = new ArrayList<>();

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_section);

        user = getIntent().getParcelableExtra("USER");

        if (user == null) {
            Log.d(TAG, "user is null");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Practice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView quizzesTextView = findViewById(R.id.quizzes_count);
        quizzesTextView.setText("Quizzes Completed: " + user.getQuizzesCompleted() + "/3");

        //Log.d(TAG, "onCreate: started.");
        initQuizzes();
    }

    private void initQuizzes() {
        quizNames.add("Quiz 1. Variables And Types");
        quizNames.add("Quiz 2. Basic Operators");
        quizNames.add("Quiz 3. Input and Output");
        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recycler view");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        QuizRecyclerAdapter adapter = new QuizRecyclerAdapter(quizNames, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onQuizClick(int position) {
        quizNames.get(position);
        Intent intent = new Intent(this, StartQuiz1Activity.class);
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

    public void goBackToHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }
}

