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
import com.example.kodokode.User;

public class Chapter1Fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_chapter1_fragment1, container, false);

        final Chapter1Fragment2 chapter1Fragment2 = new Chapter1Fragment2();
        if (getArguments() != null) {
            Bundle args = getArguments();
            chapter1Fragment2.setArguments(args);
        }

        ImageButton rightButton = view.findViewById(R.id.right_arrow_button);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opens the next fragment
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, chapter1Fragment2);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
