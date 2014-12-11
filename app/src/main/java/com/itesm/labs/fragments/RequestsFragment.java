package com.itesm.labs.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.itesm.labs.R;
import com.itesm.labs.adapters.RequestsModelAdapter;
import com.itesm.labs.rest.models.Request;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    ListView mListView;

    ArrayList<Request> data = new ArrayList<Request>();

    public RequestsFragment() {
        // Required empty public constructor
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

        data.add(new Request(R.drawable.ic_request_pending, "Miguel Grado Baylon", "A00758435", "21/11/2014"));
        data.add(new Request(R.drawable.ic_request_pending, "Armando Colomo Baray", "A00758518", "21/11/2014"));
        data.add(new Request(R.drawable.ic_request_ready, "Mauricio Delgado Montes", "A00758620", "21/11/2014"));

        mListView = (ListView) view.findViewById(R.id.request_fragment_list);
        mListView.setAdapter(new RequestsModelAdapter(view.getContext(), data));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
