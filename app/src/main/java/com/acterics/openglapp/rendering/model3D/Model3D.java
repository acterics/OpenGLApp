package com.acterics.openglapp.rendering.model3D;

import com.acterics.openglapp.rendering.Model;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by User on 29.04.2016.
 */
public abstract class Model3D extends Model {

    public Model3D(int programID, float x, float y, float z) {
        super(programID, x, y, z);
    }
    @Override
    protected void bindData() {
        uColorLocation = glGetUniformLocation(programID, "u_Color");
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);



        aVerticesLocation = glGetAttribLocation(programID, "a_Vertex");
        vertexData.position(0);
        glVertexAttribPointer(aVerticesLocation, 3, GL_FLOAT,
                false, 0, vertexData);
        glEnableVertexAttribArray(aVerticesLocation);

        super.bindData();
    }
}
