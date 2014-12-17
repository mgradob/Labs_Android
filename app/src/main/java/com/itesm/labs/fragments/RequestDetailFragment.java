package com.itesm.labs.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.models.RequestModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestDetailFragment extends Fragment {

    private RequestModel mRequestModel;
    private ViewHolder mViewHolder;

    public RequestModel getmRequestModel() {
        return mRequestModel;
    }

    public void setmRequestModel(RequestModel mRequestModel) {
        this.mRequestModel = mRequestModel;
    }

    public RequestDetailFragment() {
        // Required empty public constructor
    }

    static class ViewHolder {
        TextView userName, userId;
        ListView userRequestList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewHolder =  new ViewHolder();
        mViewHolder.userName = (TextView) view.findViewById(R.id.request_detail_user_name);
        mViewHolder.userId = (TextView) view.findViewById(R.id.request_detail_user_id);
        mViewHolder.userRequestList = (ListView) view.findViewById(R.id.request_detail_user_list);

        mViewHolder.userName.setText(mRequestModel.getUserName());
        mViewHolder.userId.setText(mRequestModel.getUserId());
    }
}
