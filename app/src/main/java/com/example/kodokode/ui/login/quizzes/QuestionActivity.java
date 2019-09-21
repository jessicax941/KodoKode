package com.example.kodokode.ui.login.quizzes;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kodokode.R;
import com.example.kodokode.User;
import com.example.kodokode.ui.login.UsersDbHelper;

import java.util.Collections;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private TextView questionCount;
    private TextView questionText;
    private TextView message;
    private TextView scoreMessage;
    private RadioGroup radioGroup;
    private RadioButton button1;
    private RadioButton button2;
    private RadioButton button3;
    private Button checkAnsButton;

    private ColorStateList textColorDefaultRb;

    private List<Question> questionList;
    private int totalQuestionCount;
    private int questionCounter;
    private Question currentQuestion;
    private int score;
    private boolean hasAnswered;

    private User user;
    private UsersDbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        user = getIntent().getParcelableExtra("USER");
        dbHelper = new UsersDbHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Quiz name");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        questionText = findViewById(R.id.question);
        questionCount = findViewById(R.id.question_count);
        message = findViewById(R.id.message);
        scoreMessage = findViewById(R.id.score);
        radioGroup = findViewById(R.id.radio_group);
        button1 = findViewById(R.id.option_a);
        button2 = findViewById(R.id.option_b);
        button3 = findViewById(R.id.option_c);
        checkAnsButton = findViewById(R.id.check_ans_button);

        textColorDefaultRb = button1.getTextColors();

        //this.deleteDatabase("kodokodequiz");
        QuizDbHelper databaseHelper = new QuizDbHelper(this);
        questionList = databaseHelper.getAllQuestions();
        totalQuestionCount = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

        checkAnsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasAnswered) {
                    if (button1.isChecked() || button2.isChecked() || button3.isChecked()) {
                        //user has selected an option
                        checkAnswer();
                        //radioGroup.clearCheck();
                    } else { //none of the radio buttons are selected
                        Toast.makeText(QuestionActivity.this,
                                "Please choose an answer.",
                                Toast.LENGTH_LONG).show();
                    }
                } else { //user has answered question
                    //radioGroup.clearCheck();
                    showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion() {
        clearAllChecked();
        button1.setTextColor(textColorDefaultRb);
        button2.setTextColor(textColorDefaultRb);
        button3.setTextColor(textColorDefaultRb);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        message.setText("");
        scoreMessage.setText("");

        if (questionCounter < totalQuestionCount) { //not on the last question yet
            currentQuestion = questionList.get(questionCounter);

            questionText.setText(currentQuestion.getQuestion());
            button1.setText(currentQuestion.getOption1());
            button2.setText(currentQuestion.getOption2());
            button3.setText(currentQuestion.getOption3());

            questionCounter++;
            questionCount.setText("Question " + questionCounter + "/5");
            hasAnswered = false;
            checkAnsButton.setText("Check Answer");
        } else {
            finishQuiz();
        }
    }

    private void clearAllChecked() {
        radioGroup.clearCheck();
    }

    private void checkAnswer() {
        hasAnswered = true;

        RadioButton optionSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int selectedNum = radioGroup.indexOfChild(optionSelected) + 1;

        if (selectedNum == currentQuestion.getAnswerNum()){
            //if optionSelected is the correct answer
            score++;
            message.setText("Good job! You got it right!");
            message.setTextColor(Color.rgb(60, 179, 113)); //medium sea green
        } else {
            optionSelected.setTextColor(Color.rgb(250, 128, 114));
            message.setText("You didn't get it right. Try again later!");
            message.setTextColor(Color.rgb(250, 128, 114)); //salmon
        }

        showSolution();
    }

    private void showSolution() {
        RadioButton correctOption = (RadioButton) radioGroup.getChildAt(currentQuestion.getAnswerNum() - 1);
        correctOption.setTextColor(Color.rgb(60, 179, 113)); //medium sea green
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);

        if (questionCounter < totalQuestionCount) { //not on the last question
            scoreMessage.setText("Current score: " + score + "/5");
            checkAnsButton.setText("Next");
        } else {
            scoreMessage.setText("Final score: " + score + "/5");
            checkAnsButton.setText("Finish Quiz"); //currently on last question
        }
    }

    public void finishQuiz() {
        // update points and quizzes completed (if applicable)
        if (score == 5) {
            // full marks
            user = dbHelper.updateQuizzesCompleted(user);
        } else {
            // not full marks
            user = dbHelper.updatePoints(user,score * 2);
        }

        openFinishDialog();
    }

    //shows dialog when user clicks on "Finish Quiz" at the end of the quiz, displays quiz score
    private void openFinishDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle("Quiz completed");
        builder.setMessage("Your score is " + score + "/5" +
                "\nYou gained " + score * 2 + " points!");
        builder.setPositiveButton("okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goBackToStartQuiz();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
//        final Dialog dialog = new Dialog(this);
//
//        dialog.setContentView(R.layout.finish_quiz_alert_dialog);
//        dialog.setTitle("Are you sure you want to exit?");
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
//            }
//        });
//        dialog.show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle("Are you sure you want to exit?");
        builder.setMessage("Your progress will not be saved.");
        builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //continue quiz
                dialog.cancel();
            }
        });
        builder.setNegativeButton("exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //exits the quiz
                goBackToStartQuiz();
            }
        }).create();

        final AlertDialog dialog = builder.create();

        dialog.show();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setBackgroundColor(getResources().getColor(R.color.White));
                negativeButton.setTextColor(getResources().getColor(R.color.Red));

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setBackgroundColor(getResources().getColor(R.color.White));
                positiveButton.setTextColor(getResources().getColor(R.color.MediumSeaGreen));
            }
        });

    }

    public void goBackToStartQuiz() {
        Intent intent = new Intent(this, StartQuiz1Activity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
