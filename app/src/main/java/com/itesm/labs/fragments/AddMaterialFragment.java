package com.itesm.labs.fragments;


import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.itesm.labs.R;
import com.itesm.labs.adapters.AddMaterialAdapter;
import com.itesm.labs.animations.RevealAnimation;

import java.util.ArrayList;


public class AddMaterialFragment extends Fragment {

    private ListView mListView;
    private Button mCancel, mAccept;

    public AddMaterialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_material, container, false);
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                RevealAnimation revealAnimation = new RevealAnimation(v);
                revealAnimation.revealFromBottomRight(200, 0);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (ListView) view.findViewById(R.id.fragment_add_material_list);
        ArrayList<String> dummyData = new ArrayList<>();
        dummyData.add("Resistencias");
        dummyData.add("Capacitores");
        dummyData.add("Inductores");
        mListView.setAdapter(new AddMaterialAdapter(view.getContext(), dummyData));

        mAccept = (Button) view.findViewById(R.id.fragment_add_material_accept);
        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mCancel = (Button) view.findViewById(R.id.fragment_add_material_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
