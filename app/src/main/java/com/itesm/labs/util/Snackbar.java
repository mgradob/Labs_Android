package com.itesm.labs.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.labs.R;

/**
 * Created by mgradob on 1/26/15.
 */
public class Snackbar extends Toast {

    private LayoutInflater mLayoutInflater;
    private View mView;
    private TextView mText, mAction;

    private Context mContext;

    public static final int NO_ACTION = -1;
    public static final int ACTION_DONE = 0;
    public static final int ACTION_UNDO = 1;
    public static final int ACTION_RETRY = 2;

    public static final int LENGHT_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGHT_LONG = Toast.LENGTH_LONG;

    /**
     * @param activity
     * @param message
     * @param duration
     * @param action
     */
    public Snackbar(Activity activity, String message, int duration, final int action) {
        super(activity.getApplicationContext());
        mContext = activity.getApplicationContext();

        this.setDuration(duration);
        mLayoutInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = mLayoutInflater.inflate(
                R.layout.snackbar,
                (ViewGroup) activity.findViewById(R.id.snackbar_layout)
        );
        this.setView(mView);
        mText = (TextView) mView.findViewById(R.id.snackbar_text);
        mText.setText(message);
        mAction = (TextView) mView.findViewById(R.id.snackbar_action);
        mAction.setText(setAction(action));
        mAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction(action);
            }
        });
        this.setGravity(Gravity.LEFT | Gravity.BOTTOM, 16, 16);
    }

    private String setAction(int action) {
        switch (action) {
            case NO_ACTION:
                return "";
            case ACTION_DONE:
                return "DONE";
            case ACTION_UNDO:
                return "UNDO";
            case ACTION_RETRY:
                return "RETRY";
            default:
                return "";
        }
    }

    private void doAction(int action) {
        switch (action) {
            case NO_ACTION:
                break;
            case ACTION_DONE:
                break;
            case ACTION_UNDO:
                break;
            case ACTION_RETRY:
                break;
        }
    }
}
