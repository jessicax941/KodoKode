package com.example.kodokode.ui.login.chapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kodokode.LearningSection;
import com.example.kodokode.R;
import com.example.kodokode.User;
import com.example.kodokode.ui.login.UsersDbHelper;

public class Chapter1Fragment5 extends Fragment {

    private User user;

    private UsersDbHelper dbHelper;

    private static final String TAG = "Chapter1Fragment5";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chapter1_fragment5, container, false);

        dbHelper = new UsersDbHelper(this.getContext());

        final Chapter1Fragment4 chapter1Fragment4 = new Chapter1Fragment4();
        if (getArguments() != null) {
            Bundle args = getArguments();
            chapter1Fragment4.setArguments(args);
        }

        ImageButton leftButton = view.findViewById(R.id.left_arrow_button);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opens the previous fragment
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, chapter1Fragment4);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            }
        });

        user = getArguments().getParcelable("USER");

        Button completeButton = view.findViewById(R.id.go_back_button);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // increase number of chapters completed by one
                user = dbHelper.updateChaptersCompleted(user);
                Log.d(TAG, "chapters completed updated for user " + user.getUsername()
                        + " to " + user.getChaptersCompleted());
                openCompletedDialog();
//                openLearningSection();
//                getActivity().finish();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.detach(new Chapter1Fragment5());
//                transaction.commit();
            }
        });

        int width = (int)(RelativeLayout.LayoutParams.MATCH_PARENT * 0.9);
        View divider1 = view.findViewById(R.id.divider);
        View divider2 = view.findViewById(R.id.divider2);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, 1);
        divider1.setLayoutParams(lp);
        divider2.setLayoutParams(lp);
        return view;
    }

    public void openLearningSection() {
        Intent intent = new Intent(this.getActivity(), LearningSection.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    // alert dialog pops up, telling user that they got 10 points
    // when click on 'okay', learning section opens and chapter1activity finishes
    public void openCompletedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext(), R.style.AlertDialogTheme);
        builder.setTitle("Chapter 1 Completed");
        builder.setMessage("You got 10 points!");
        builder.setPositiveButton("okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openLearningSection();
                getActivity().finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
