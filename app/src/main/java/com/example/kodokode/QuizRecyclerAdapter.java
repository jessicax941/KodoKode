package com.example.kodokode;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.ViewHolder> {

    private static final String TAG = "QuizRecyclerAdapter";
    private QuizRecyclerAdapter.OnQuizListener onQuizListener;

    private ArrayList<String> quizNames;

    public QuizRecyclerAdapter(ArrayList<String> quizNames, QuizRecyclerAdapter.OnQuizListener onQuizListener) {
        this.quizNames =  quizNames;
        this.onQuizListener = onQuizListener;
    }

    @NonNull
    @Override
    public QuizRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_quiz, parent, false);
        QuizRecyclerAdapter.ViewHolder holder = new QuizRecyclerAdapter.ViewHolder(view, onQuizListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuizRecyclerAdapter.ViewHolder viewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        viewHolder.setQuizName(quizNames.get(position));
    }

    @Override
    public int getItemCount() {
        return quizNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView quizName;
        RelativeLayout parentLayout;
        QuizRecyclerAdapter.OnQuizListener onQuizListener;

        public ViewHolder(View itemView, QuizRecyclerAdapter.OnQuizListener onQuizListener) {
            super(itemView);
            quizName = itemView.findViewById(R.id.quiz_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            this.onQuizListener = onQuizListener;

            itemView.setOnClickListener(this);
        }

        public void setQuizName(String quizNameString) {
            this.quizName.setText(quizNameString);
        }

        @Override
        public void onClick(View v) {
            onQuizListener.onQuizClick(getAdapterPosition());
        }
    }

    public interface OnQuizListener {
        void onQuizClick(int position);
    }
}
