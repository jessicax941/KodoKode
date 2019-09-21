package com.example.kodokode.ui.login.quizzes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.kodokode.ui.login.quizzes.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "kodokodequiz";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase database;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.database = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NUM + " INTEGER" +
                ")";

        database.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(database);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("What is a variable?",
                "a. A variable is a quantity that can be used to represent a value.",
                "b. A variable is a quality that can be used to represent a value.",
                "c. A variable is a quantity that can be used to represent a constant value.",
                1);
        addQuestion(q1);

        Question q2 = new Question("What is the value of x in 7 + x = 10?",
                "a. 2",
                "b. 3",
                "c. 4",
                2);
        addQuestion(q2);

        Question q3 = new Question("What is an integer variable?",
                "a. A variable that holds a whole number",
                "b. A variable that holds a word",
                "c. A variable that holds a non-whole number",
                1);
        addQuestion(q3);

        Question q4 = new Question("What is a string variable?",
                "a. A variable that holds a whole number",
                "b. A variable that holds a string of characters",
                "c. A variable that holds a non-whole number",
                2);
        addQuestion(q4);

        Question q5 = new Question("What is a double variable?",
                "a. A variable that holds a whole number",
                "b. A variable that holds a string of characters",
                "c. A variable that holds a non-whole number",
                3);
        addQuestion(q5);
    }

    private void addQuestion(Question qn) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, qn.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, qn.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, qn.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, qn.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NUM, qn.getAnswerNum());
        database.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> qnList = new ArrayList<>();
        database = getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question qn = new Question();
                qn.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                qn.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                qn.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                qn.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                qn.setAnswerNum(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NUM)));
                qnList.add(qn);
            } while (c.moveToNext());
        }

        c.close();
        return qnList;
    }

}
