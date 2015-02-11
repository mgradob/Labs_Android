package com.itesm.labs.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.labs.R;
import com.itesm.labs.animations.RevealAnimation;
import com.itesm.labs.rest.models.Request;
import com.itesm.labs.util.NfcHandler;
import com.itesm.labs.util.NfcHandler.UidCallback;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class RequestDetailFragment extends Fragment implements UidCallback {

    private Request request;
    private TextView userName;
    private TextView userId;
    private ListView userRequestList;
    private String userUid;
    private FloatingActionButton mFab;

    private Activity mActivity;
    private Context mContext;

    private UidCallback mCallback;
    private NfcHandler nfcHandler;

    public RequestDetailFragment() {
        // Required empty public constructor
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_requests_detail, container, false);
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                RevealAnimation revealAnimation = new RevealAnimation(v);
                revealAnimation.revealFromTopLeft(400, 0);
            }
        });

        nfcHandler = new NfcHandler(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mActivity = activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = view.getContext();

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

        userName.setText(request.getUserName());
        userId.setText(request.getUserId());
        userRequestList.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list));

        mFab = (FloatingActionButton) view.findViewById(R.id.fragment_request_detail_fab);
        mFab.attachToListView(userRequestList);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRequest(request.getStatus());
            }
        });

        if (!request.getStatus())
            mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_white));
        else {
            mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_uid_white));
            enableReader();
        }
    }

    private void validateRequest(Boolean requestStatus) {
        if (requestStatus)
            Toast.makeText(mContext, "UID: " + userUid, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        disableReader();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableReader();
    }

    private void enableReader() {
        Activity activity = getActivity();
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (nfcAdapter != null)
            nfcAdapter.enableReaderMode(activity, nfcHandler, NfcAdapter.FLAG_READER_NFC_A, null);
    }

    private void disableReader() {
        Activity activity = getActivity();
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (nfcAdapter != null)
            nfcAdapter.disableReaderMode(getActivity());
    }

    @Override
    public void getUid(String uid) {
        this.userUid = uid;
    }
}
