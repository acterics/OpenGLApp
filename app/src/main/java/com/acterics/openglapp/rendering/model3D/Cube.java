package com.acterics.openglapp.rendering.model3D;

import android.content.Context;

import com.acterics.openglapp.FileUtils;
import com.acterics.openglapp.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import static android.opengl.GLES20.GL_LINE_LOOP;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUseProgram;

/**
 * Created by User on 07.05.2016.
 */
public class Cube extends Model3D {

    private float edge;
    public Cube(int programID, float x, float y, float z, float edge) {
        super(programID, x, y, z);
        this.edge = edge;
    }

    @Override
    public void create() {
//        ArrayList<Float> vertices = new ArrayList<>();
////        float x, y, z;
//
//        vertices.add(edge / 2);
//        vertices.add(edge / 2);
//        vertices.add(edge / 2);
//
//        vertices.add(-edge / 2);
//        vertices.add(edge / 2);
//        vertices.add(edge / 2);
//
//        vertices.add(-edge / 2);
//        vertices.add(-edge / 2);
//        vertices.add(edge / 2);
//
//        vertices.add(edge / 2);
//        vertices.add(-edge / 2);
//        vertices.add(edge / 2);
//
//
//
//
//
//
//        vertexData = ByteBuffer
//                .allocateDirect(vertices.size() * 4)
//                .order(ByteOrder.nativeOrder())
//                .asFloatBuffer();
//
//        for(Float vertex : vertices) {
//            vertexData.put(vertex);
//        }
//        glUseProgram(programID);
//        update();
    }

    @Override
    public void create(Context context) {
        ArrayList<Float> vertices = FileUtils.parseModel(FileUtils.readTextFromRaw(context, R.raw.cube));
        for(int i = 0; i < vertices.size(); i++) {
            vertices.set(i, vertices.get(i) * edge);
        }
        vertexData = ByteBuffer
                .allocateDirect(vertices.size() * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        for(Float vertex : vertices) {
            vertexData.put(vertex);
        }
        vertexCount = vertices.size() / 3;
        glUseProgram(programID);
        update();
    }


    @Override
    public void update() {
        bindData();
    }

    @Override
    public void draw() {
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);
    }
}
