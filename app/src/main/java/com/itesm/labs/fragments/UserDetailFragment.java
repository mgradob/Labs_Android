package com.itesm.labs.fragments;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.models.UserModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailFragment extends Fragment {

    private UserModel UserModel;
    private TextView userName;
    private TextView userId;
    private TextView userCarrer;
    private ListView userHistoryList;
    private int colorCode;

    public UserModel getmRequestModel() {
        return UserModel;
    }

    public void setUserModel(UserModel userModel) {
        this.UserModel = userModel;
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
        return inflater.inflate(R.layout.fragment_users_detail, container, false);
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

        userName.setText(UserModel.getFullName());
        userId.setText(UserModel.getUserId());
        userCarrer.setText(UserModel.getUserCareer());
        userHistoryList.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list));
    }
}
