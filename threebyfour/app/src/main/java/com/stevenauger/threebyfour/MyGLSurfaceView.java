package com.stevenauger.threebyfour;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by Steven on 11-10-2014.
 */
public class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer mRenderer;
    public MyGLSurfaceView(Context context)
    {
        super(context);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        mRenderer = new MyGLRenderer();
        mRenderer.setContext(context);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        //create game object which has game loop to update data!

    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                float glX = 2*(x - (getWidth()/2.0f))/getWidth();
                float glY =  -2*(y - (getHeight()/2.0f))/getHeight();
                mRenderer.onActionMove(glX, glY, 1.0f);
                requestRender();
                break;
            case MotionEvent.ACTION_DOWN:
                //convert screen coordinate to gl coordinates
                float glXDown = 2*(x - (getWidth()/2.0f))/getWidth();
                float glYDown =  -2*(y - (getHeight()/2.0f))/getHeight();
                mRenderer.onActionDown(glXDown, glYDown);
                requestRender();
                break;
            case MotionEvent.ACTION_UP:
                //convert screen coordinate to gl coordinates
                float glXUp = 2*(x - (getWidth()/2.0f))/getWidth();
                float glYUp =  -2*(y - (getHeight()/2.0f))/getHeight();
                mRenderer.onActionUp(glXUp, glYUp);
                requestRender();
                break;
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}
