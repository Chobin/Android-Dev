package com.stevenauger.threebyfour;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Steven on 11-10-2014.
 */
public class MyCircle extends GLDrawnComponent{

    private float center_x = 0.0f;
    private float center_y = 0.0f;
    private float center_z = 0.0f;
    private float scale = 1.0f;

    static float radius = 0.15f;
    static int fv = 60;
    float vertices[];
    //private  int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;


    public MyCircle()
    {
        super();
//create a buffer for vertex data
        vertices= new float[(fv)*3]; // (x,y) for each vertex
        color = new float[]{ 0.0f, 0.76953125f, 0.62265625f, 1.0f };
        SetupVertices();
    }

    private void SetupVertices()
    {

    //center vertex for triangle fan
        vertices[0] = center_x;
        vertices[1] = center_y;
        vertices[2] = center_z;

    //outer vertices of the circle
        int outerVertexCount = fv-1;
        int i;
        for(i =1; i <outerVertexCount; i++){
            float percent = (i / (float)(outerVertexCount));
            float rad = (float)(percent * 2 * 3.14);
            vertices[(i * 3)+ 0] = (float) (radius * Math.cos(rad) + vertices[0]);
            vertices[(i * 3)+ 1] = (float) (radius * Math.sin(rad) + vertices[1]);
            vertices[(i * 3)+ 2] = 0;
        }
        float percent = (i / (float)(outerVertexCount));
        float rad = (float)(percent * 2 * 3.14);
        vertices[(i * 3)+ 0] =  vertices[(1 * 3)+ 0];
        vertices[(i * 3)+ 1] =  vertices[(1 * 3)+ 1];
        vertices[(i*3)+2] = 0.0f;
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                vertices.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(vertices);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);
    }
    public void setNewPosition(float newX, float newY)
    {
        center_x = newX;
        center_y = newY;
        SetupVertices();
    }
    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     *
     */
    public void draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, fv);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
