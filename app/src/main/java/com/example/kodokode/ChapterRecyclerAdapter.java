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

public class ChapterRecyclerAdapter extends RecyclerView.Adapter<ChapterRecyclerAdapter.ViewHolder> {

    private static final String TAG = "ChapterRecyclerAdapter";
    private OnChapterListener onChapterListener;

    private ArrayList<String> chapterNames = new ArrayList<>();

    public ChapterRecyclerAdapter(ArrayList<String> chapterNames, OnChapterListener onChapterListener) {
        this.chapterNames =  chapterNames;
        this.onChapterListener = onChapterListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_chapter, parent, false);
        ViewHolder holder = new ViewHolder(view, onChapterListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        viewHolder.setChapterName(chapterNames.get(position));
    }

    @Override
    public int getItemCount() {
        return chapterNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView chapterName;
        RelativeLayout parentLayout;
        OnChapterListener onChapterListener;

        public ViewHolder(View itemView, OnChapterListener onChapterListener) {
            super(itemView);
            chapterName = itemView.findViewById(R.id.chapter_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            this.onChapterListener = onChapterListener;

            itemView.setOnClickListener(this);
        }

        public void setChapterName(String chapterNameString) {
            this.chapterName.setText(chapterNameString);
        }

        @Override
        public void onClick(View v) {
            onChapterListener.onChapterClick(getAdapterPosition());
        }
    }

    public interface OnChapterListener {
        void onChapterClick(int position);
    }
}
