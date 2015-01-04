package com.itesm.labs.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.itesm.labs.R;
import com.itesm.labs.adapters.RequestsModelAdapter;
import com.itesm.labs.rest.models.Request;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    private ListView mListView;
    private ArrayList<Request> data = new ArrayList<Request>();
    private RequestFragmentComm mCallback;
    private Toolbar mSubtoolbar;
    ProgressBar mProgressBar;

    public RequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data.add(new Request(R.drawable.ic_request_pending, "Miguel Grado Baylon", "A00758435", "21/11/2014"));
        data.add(new Request(R.drawable.ic_request_pending, "Armando Colomo Baray", "A00758518", "21/11/2014"));
        data.add(new Request(R.drawable.ic_request_ready, "Mauricio Delgado Montes", "A00758620", "21/11/2014"));
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

        mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_requests_progressbar);
        mProgressBar.setIndeterminate(true);

        mListView = (ListView) view.findViewById(R.id.fragment_requests_list);
        mProgressBar.setVisibility(View.INVISIBLE);
        mListView.setAdapter(new RequestsModelAdapter(view.getContext(), data));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.loadNewRequestDetail(data.get(position));
            }
        });

        mSubtoolbar = (Toolbar) view.findViewById(R.id.fragment_requests_subtoolbar);
        mSubtoolbar.setTitle("Pedidos");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (RequestFragmentComm) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement interface.");
        }
    }

    public interface RequestFragmentComm {
        void loadNewRequestDetail(Request request);
    }
}
