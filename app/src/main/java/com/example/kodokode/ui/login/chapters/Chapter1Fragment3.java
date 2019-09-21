package com.example.kodokode.ui.login.chapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.kodokode.R;

public class Chapter1Fragment3 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chapter1_fragment3, container, false);

        final Chapter1Fragment2 chapter1Fragment2 = new Chapter1Fragment2();
        if (getArguments() != null) {
            Bundle args = getArguments();
            chapter1Fragment2.setArguments(args);
        }

        final Chapter1Fragment4 chapter1Fragment4 = new Chapter1Fragment4();
        if (getArguments() != null) {
            Bundle args = getArguments();
            chapter1Fragment4.setArguments(args);
        }

        ImageButton leftButton = view.findViewById(R.id.left_arrow_button);
        ImageButton rightButton = view.findViewById(R.id.right_arrow_button);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opens the previous fragment
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, chapter1Fragment2);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opens the next fragment
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, chapter1Fragment4);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            }
        });

        return view;
    }
}
