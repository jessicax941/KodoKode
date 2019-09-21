package com.example.kodokode.ui.login;

import android.provider.BaseColumns;

public final class UsersContract implements BaseColumns {

    private UsersContract() {}

    public static class UsersTable implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_POINTS = "points";
        public static final String COLUMN_CHAPTERS = "chapters";
        public static final String COLUMN_QUIZZES = "quizzes";
        public static final String COLUMN_QUIZ1 = "quiz1";
        //public static final String COLUMN_QUIZ1_MASTERY = "quiz1 mastery";
    }
}
