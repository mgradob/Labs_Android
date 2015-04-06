package com.itesm.labs.fragments;


import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.itesm.labs.R;
import com.itesm.labs.activities.RequestDetailActivity;
import com.itesm.labs.adapters.RequestsModelAdapter;
import com.itesm.labs.rest.models.Request;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    ProgressBar mProgressBar;
    private ListView mListView;
    private ArrayList<Request> data = new ArrayList<Request>();
    private Toolbar mSubtoolbar;
    private String ENDPOINT;
    private Context mContext;

    public RequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) setENDPOINT(savedInstanceState.getString("ENDPOINT"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requests, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = view.getContext();

        mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_requests_progressbar);
        mProgressBar.setIndeterminate(true);

        mListView = (ListView) view.findViewById(R.id.fragment_requests_list);
        mProgressBar.setVisibility(View.INVISIBLE);
        mListView.setAdapter(new RequestsModelAdapter(view.getContext(), data));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, RequestDetailActivity.class);
                intent.putExtra("USERNAME", data.get(position).getUserName());
                intent.putExtra("USERID", data.get(position).getUserId());

                // Replace when obtaining requests from db.
                if (position == 0 || position == 1)
                    intent.putExtra("STATUS", false);   // Pending request.
                else
                    intent.putExtra("STATUS", true);    // Done request.

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                        getActivity(),
                        Pair.create(view.findViewById(R.id.request_item_user_name), getResources().getString(R.string.requests_fragment_transition_name)),
                        Pair.create(view.findViewById(R.id.request_item_user_id), getResources().getString(R.string.requests_fragment_transition_id))
                );
                startActivity(intent, activityOptions.toBundle());
            }
        });

        mSubtoolbar = (Toolbar) view.findViewById(R.id.fragment_requests_subtoolbar);
        mSubtoolbar.setTitle("Pedidos");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ENDPOINT", ENDPOINT);
    }

    public String getENDPOINT() {
        return ENDPOINT;
    }

    public void setENDPOINT(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }
}
