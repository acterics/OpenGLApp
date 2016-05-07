package com.acterics.openglapp.rendering.model3D;


import android.content.Context;

import com.acterics.openglapp.FileUtils;
import com.acterics.openglapp.R;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import static android.opengl.GLES20.GL_LINE_LOOP;
import static android.opengl.GLES20.GL_LINE_STRIP;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUseProgram;

/**
 * Created by User on 29.04.2016.
 */
public class Sphere extends Model3D {

    public static final float DEFAULT_SMOOTHNESS = 36;

    private float radius;

    private float smoothness = DEFAULT_SMOOTHNESS;

    public Sphere(int programID, float x, float y, float z, float radius) {
        super(programID, x, y, z);
        this.radius = radius;
    }

    @Override
    public void create() {


        ArrayList<Float> vertices = new ArrayList<>();
        float x, y, z;
        for(float i = 0; i < 2 * Math.PI; i += 2 * Math.PI / smoothness) {
            for(float j = 0; j < 2 * Math.PI; j += 2 * Math.PI / smoothness) {
                x = radius * (float) (Math.cos(j) * Math.sin(i));
                y = radius * (float) (Math.sin(j) * Math.sin(i));
                z = radius * (float) Math.cos(i);
                vertices.add(x);
                vertices.add(y);
                vertices.add(z);
                vertexCount ++;
            }
        }
        vertexData = ByteBuffer
                .allocateDirect(vertices.size() * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        for(Float vertex : vertices) {
            vertexData.put(vertex);
        }
        glUseProgram(programID);
        update();
    }

    public void create(Context context) {
//        ArrayList<Float> vertices = FileUtils.parseModel(FileUtils.readTextFromRaw(context, R.raw.sphere));
//        for(int i = 0; i < vertices.size(); i++) {
//            vertices.set(i, vertices.get(i) * radius);
//        }
//        vertexData = ByteBuffer
//                .allocateDirect(vertices.size() * 4)
//                .order(ByteOrder.nativeOrder())
//                .asFloatBuffer();
//
//        for(Float vertex : vertices) {
//            vertexData.put(vertex);
//        }
//        vertexCount = vertices.size() / 3;
//        glUseProgram(programID);
//        update();
    }


    @Override
    public void update() {
        bindData();
    }

    @Override
    public void draw() {
        glDrawArrays(GL_LINE_LOOP, 0, vertexCount);
    }
}
