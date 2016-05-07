package com.acterics.openglapp.rendering;

import android.content.Context;
import android.opengl.Matrix;

import java.nio.FloatBuffer;

import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;

/**
 * Created by User on 25.04.2016.
 */
public abstract class Model {

    protected float x;
    protected float y;
    protected float z;
    protected int programID;
    protected FloatBuffer vertexData;
    protected int uColorLocation;
    protected int uPositionLocation;
    protected int aVerticesLocation;
    protected int uMatrixLocation;

    protected int vertexCount = 0;

    protected float[] mProjectionMatrix = new float[16];

    protected Model() {
        x = 0;
        y = 0;
        z = 0;
    }

    protected Model(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    protected Model(int programID, float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.programID = programID;
    }

    public abstract void create();
    public abstract void create(Context context);
    public abstract void update();
    public abstract void draw();

    protected void bindData() {
        uPositionLocation = glGetUniformLocation(programID, "u_Translation");
        glUniform4f(uPositionLocation, x, y, z, 0);
    }

    public void bindMatrix(int width, int height) {

    }


}
