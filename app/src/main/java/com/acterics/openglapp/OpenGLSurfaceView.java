package com.acterics.openglapp;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * Created by User on 28.04.2016.
 */
public class OpenGLSurfaceView extends GLSurfaceView {

    private static final String TAG = "OpenGLSurfaceView";
    private OpenGLRenderer renderer;


    public OpenGLSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        renderer = new OpenGLRenderer(getContext(), getWidth(), getHeight());
        setEGLContextClientVersion(2);
        setRenderer(renderer);

    }



    public boolean onMove(MotionEvent event) {
        return renderer.onMove(event);
    }
    public boolean onDown(MotionEvent event) {
        return renderer.onDown(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "Touch event " + event.getAction());
        try {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    return onMove(event);

                case MotionEvent.ACTION_DOWN:
                    return onDown(event);
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

}
