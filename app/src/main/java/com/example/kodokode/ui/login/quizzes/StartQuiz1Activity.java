package com.example.kodokode.ui.login.quizzes;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.kodokode.AssessmentSection;
import com.example.kodokode.R;
import com.example.kodokode.User;

public class StartQuiz1Activity extends AppCompatActivity {
    //private WebView webView;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);

        user = getIntent().getParcelableExtra("USER");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Quizzes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuiz1();
            }
        });
    }

    private void openQuiz1() {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void goBackToAssessmentSection() {
        Intent intent = new Intent(this, AssessmentSection.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            goBackToAssessmentSection();
        }

        return super.onOptionsItemSelected(item);
    }

}

//        webView = findViewById(R.id.webView);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("http://www.google.com");


//    @Override
//    public void onBackPressed() {
//        if (webView.canGoBack()) {
//            webView.goBack();
//        } else {
//            super.onBackPressed();
//        }
//    }

