package com.acterics.openglapp.rendering.model2D;

import android.content.Context;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;

/**
 * Created by User on 25.04.2016.
 */
public class Triangle extends Model2D {


    public Triangle(int programID, float x, float y) {
        super(programID, x, y);
        create();
    }

    @Override
    public void create() {
        float[] vertices = {
                -0.5f, -0.2f,
                0   , 0.2f,
                0.5f, -0.2f};
        vertexData = ByteBuffer
                .allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(vertices);
        glUseProgram(programID);
        update();
    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void update() {
        bindData();
    }

    @Override
    public void draw() {

        glDrawArrays(GL_TRIANGLES, 0, 3);
    }

}
