package com.itesm.labs.fragments;


import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.animations.RevealAnimation;
import com.itesm.labs.rest.models.User;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailFragment extends Fragment {

    private User user;
    private TextView userName;
    private TextView userId;
    private TextView userCarrer;
    private ListView userHistoryList;
    private int colorCode;
    private FloatingActionButton mFab;

    public User getmRequestModel() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    public UserDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_detail, container, false);
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                RevealAnimation revealAnimation = new RevealAnimation(v);
                revealAnimation.revealFromTopLeft(400, 0);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> list = new ArrayList<String>();
        list.add("Resistencia");
        list.add("Capacitor");
        list.add("Inductor");
        list.add("PIC16F877A");

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.users_detail_info);
        ColorDrawable colorDrawable = (ColorDrawable) relativeLayout.getBackground();
        colorDrawable.setColor(colorCode);

        userName = (TextView) view.findViewById(R.id.users_detail_user_name);
        userId = (TextView) view.findViewById(R.id.users_detail_user_id);
        userCarrer = (TextView) view.findViewById(R.id.users_detail_user_career);
        userHistoryList = (ListView) view.findViewById(R.id.user_detail_history_list);

        userName.setText(user.getFullName());
        userId.setText(user.getUserId());
        userCarrer.setText(user.getUserCareer());
        userHistoryList.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list));

        mFab = (FloatingActionButton) view.findViewById(R.id.fragment_users_detail_fab);
        mFab.attachToListView(userHistoryList);
        mFab.setColorNormal(colorCode);
    }
}
