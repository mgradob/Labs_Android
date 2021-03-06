package com.itesm.labs.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by alumno on 1/17/15.
 */
public class RevealAnimation {

    View targetView;

    public RevealAnimation(View targetView) {
        this.targetView = targetView;
    }

    public void revealFromBottomRight(int duration, int delay) {
        targetView.setVisibility(View.INVISIBLE);
        int cx = targetView.getRight();
        int cy = targetView.getBottom();
        int radius = Math.max(targetView.getWidth(), targetView.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(targetView, cx, cy, 0, radius);
        anim.setDuration(duration);
        anim.setInterpolator(new DecelerateInterpolator(1.5f));
        anim.setStartDelay(delay);
        targetView.setVisibility(View.VISIBLE);
        anim.start();
    }

    public void revealFromBottomLeft(int duration, int delay) {
        targetView.setVisibility(View.INVISIBLE);
        int cx = targetView.getLeft();
        int cy = targetView.getBottom();
        int radius = Math.max(targetView.getWidth(), targetView.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(targetView, cx, cy, 0, radius);
        anim.setDuration(duration);
        anim.setInterpolator(new DecelerateInterpolator(1.5f));
        anim.setStartDelay(delay);
        targetView.setVisibility(View.VISIBLE);
        anim.start();
    }

    public void revealFromTopRight(int duration, int delay) {
        targetView.setVisibility(View.INVISIBLE);
        int cx = targetView.getRight();
        int cy = targetView.getTop();
        int radius = Math.max(targetView.getWidth(), targetView.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(targetView, cx, cy, 0, radius);
        anim.setDuration(duration);
        anim.setInterpolator(new DecelerateInterpolator(1.5f));
        anim.setStartDelay(delay);
        targetView.setVisibility(View.VISIBLE);
        anim.start();
    }

    public void revealFromTopLeft(int duration, int delay) {
        targetView.setVisibility(View.INVISIBLE);
        int cx = targetView.getLeft();
        int cy = targetView.getTop();
        int radius = Math.max(targetView.getWidth(), targetView.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(targetView, cx, cy, 0, radius);
        anim.setDuration(duration);
        anim.setInterpolator(new DecelerateInterpolator(1.5f));
        anim.setStartDelay(delay);
        targetView.setVisibility(View.VISIBLE);
        anim.start();
    }

    public void revealFromCenter(int duration, int delay) {
        targetView.setVisibility(View.INVISIBLE);
        int cx = (targetView.getLeft() + targetView.getRight()) / 2;
        int cy = (targetView.getTop() + targetView.getBottom()) / 2;
        int radius = Math.max(targetView.getWidth(), targetView.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(targetView, cx, cy, 0, radius);
        anim.setDuration(duration);
        anim.setInterpolator(new DecelerateInterpolator(1.5f));
        anim.setStartDelay(delay);
        targetView.setVisibility(View.VISIBLE);
        anim.start();
    }

    public void revealFromPosition(int duration, int delay, int x, int y) {
        targetView.setVisibility(View.INVISIBLE);
        int radius = Math.max(targetView.getWidth(), targetView.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(targetView, x, y, 0, radius);
        anim.setDuration(duration);
        anim.setInterpolator(new DecelerateInterpolator(1.5f));
        anim.setStartDelay(delay);
        targetView.setVisibility(View.VISIBLE);
        anim.start();
    }

    public void unvealFromCenter(int duration, int delay) {
        int cx = (targetView.getLeft() + targetView.getRight()) / 2;
        int cy = (targetView.getTop() + targetView.getBottom()) / 2;
        int radius = Math.max(targetView.getWidth(), targetView.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(targetView, cx, cy, radius, 0);
        anim.setDuration(duration);
        anim.setInterpolator(new DecelerateInterpolator(1.5f));
        anim.setStartDelay(delay);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                targetView.setVisibility(View.INVISIBLE);
            }
        });
        anim.start();
    }
}
