package com.itesm.labs.util;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.itesm.labs.R;

/**
 * Created by mgradob on 7/3/15.
 */
public class SwipeDetector implements View.OnTouchListener {

    public enum Action {
        LR,
        RL,
        UD,
        DU,
        NONE
    }

    private static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;
    private Action mSwipeDetected = Action.NONE;

    public boolean swipeDetected() {
        return mSwipeDetected != Action.NONE;
    }

    public Action getAction() {
        return mSwipeDetected;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                mSwipeDetected = Action.NONE;
                return false;
            case MotionEvent.ACTION_MOVE:
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    if (deltaX < 0) {
                        Log.d("SWIPE", "Swipe Left to Right");
                        mSwipeDetected = Action.LR;
                        return true;
                    } else if (deltaX > 0) {
                        Log.d("SWIPE", "Swipe Right to Left");
                        mSwipeDetected = Action.RL;
                        return true;
                    }
                }
            default:
                return false;
        }
    }
}
