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
import com.itesm.labs.models.RequestModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestDetailFragment extends Fragment {

    private RequestModel mRequestModel;
    private TextView userName;
    private TextView userId;
    private ListView userRequestList;

    public RequestModel getmRequestModel() {
        return mRequestModel;
    }

    public void setmRequestModel(RequestModel mRequestModel) {
        this.mRequestModel = mRequestModel;
    }

    public RequestDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requests_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> list = new ArrayList<String>();
        list.add("Resistencia");
        list.add("Capacitor");
        list.add("Inductor");
        list.add("PIC16F877A");

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.request_detail_info);
        ColorDrawable colorDrawable = (ColorDrawable) relativeLayout.getBackground();
        colorDrawable.setColor(getResources().getColor(R.color.primary_dark));

        userName = (TextView) view.findViewById(R.id.request_detail_user_name);
        userId = (TextView) view.findViewById(R.id.request_detail_user_id);
        userRequestList = (ListView) view.findViewById(R.id.request_detail_user_list);

        userName.setText(mRequestModel.getUserName());
        userId.setText(mRequestModel.getUserId());
        userRequestList.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list));
    }
}
