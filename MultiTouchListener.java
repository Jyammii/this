package com.example.closetifiy_finalproject;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;

public class MultiTouchListener implements View.OnTouchListener {

    private static final String TAG = "MultiTouchListener";

    private final ScaleGestureDetector mScaleGestureDetector;
    private final GestureDetector mGestureDetector;
    private VelocityTracker mVelocityTracker;

    private float mScaleFactor = 1.0f;
    private float mRotationDegrees = 0f;
    private float mLastTouchX;
    private float mLastTouchY;
    private float mPosX = 0f;
    private float mPosY = 0f;

    public MultiTouchListener(Context context) {
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        mGestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        mGestureDetector.onTouchEvent(event);

        final int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    mVelocityTracker.clear();
                }
                mVelocityTracker.addMovement(event);

                mLastTouchX = event.getRawX();
                mLastTouchY = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                mVelocityTracker.computeCurrentVelocity(1000);

                final float dx = event.getRawX() - mLastTouchX;
                final float dy = event.getRawY() - mLastTouchY;

                mPosX += dx;
                mPosY += dy;

                view.setX(mPosX);
                view.setY(mPosY);

                mLastTouchX = event.getRawX();
                mLastTouchY = event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                break;
        }

        view.setScaleX(mScaleFactor);
        view.setScaleY(mScaleFactor);
        view.setRotation(mRotationDegrees);

        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            return true;
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mRotationDegrees += 45f;
            return true;
        }
    }
}