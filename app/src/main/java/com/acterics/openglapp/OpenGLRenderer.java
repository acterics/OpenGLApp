package com.acterics.openglapp;

//////////////////////////////////////////////////////////////////////////
import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

import com.acterics.openglapp.rendering.Model;
import com.acterics.openglapp.rendering.model2D.Triangle;
import com.acterics.openglapp.rendering.model3D.Cube;
import com.acterics.openglapp.rendering.model3D.Sphere;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glLineWidth;

public class OpenGLRenderer implements Renderer{

    private final static int POSITION_COUNT = 3;

    private ArrayList<Model> models;

    private Context context;

    private FloatBuffer vertexData;
    private int uColorLocation;
    private int aVertecesLocation;
    private int uViewMatrixLocation;
    private int uProjectionMatrixLocation;
    private int uPositionLocation;
    private int programId;

    private float[] mProjectionMatrix = new float[16];
    private float[] mViewMatrix = new float[16];

    //View matrix attributes
    private float eyeX = 0;
    private float eyeY = 1;
    private float eyeZ = 3;

    private float centerX = 0;
    private float centerY = 0;
    private float centerZ = 0;


    //Projection matrix attributes
    private float near = 2;
    private float far = 30;
    int width;
    int height;





    float prevX = 0;
    float prevY = 0;


    private static final String TAG = "Renderer";



    public OpenGLRenderer(Context context, int width, int height) {
        this.context = context;
        this.width = width;
        this.height = height;
        models = new ArrayList<>();




    }

    public void setNear(float value) {
        near = value;
    }

    public void setFar(float value) {
        far = value;
    }


    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
        glClearColor(0f, 0f, 0f, 1f);
        glEnable(GL_DEPTH_TEST);
        int vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader);//.shaders.vertex_shader);
        //int vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER);
        int fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader);//.shaders.fragment_shader);
        programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);

        glUseProgram(programId);
        createViewMatrix();
        prepareData();
        bindData();
    }


    @Override
    public void onSurfaceChanged(GL10 arg0, int width, int height) {
        this.width = width;
        this.height = height;
        glViewport(0, 0, width, height);
        createProjectionMatrix(width, height);
        bindMatrix();
    }
    Triangle triangle;
    Sphere sphere;

    private void prepareData() {
        float l = 3;
        float faceY = -3;

        float[] vertices = {
                // ось X
                -l, 0,0,
                l,0,0,

                // ось Y
                0,-l,0,
                0,l,0,

                // ось Z
                0,0,-l,
                0,0,l,

                // bottom face
                -2, faceY, 3,
                2, faceY, 3,
                2, faceY, -3,
                -2, faceY, -3
        };

        vertexData = ByteBuffer
                .allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(vertices);
        models.add(new Triangle(programId, 0, -2));
        models.add(new Sphere(programId, 0, 2, 0, 0.5f));
        models.add(new Cube(programId, 0, 0, 0, 0.5f));
        for(Model model: models) {
            model.create();
            model.create(context);
        }


    }



    private void bindData() {
        // примитивы
        aVertecesLocation = glGetAttribLocation(programId, "a_Vertex");
        vertexData.position(0);
        glVertexAttribPointer(aVertecesLocation, POSITION_COUNT, GL_FLOAT,
                false, 0, vertexData);
        glEnableVertexAttribArray(aVertecesLocation);

        // цвет
        uColorLocation = glGetUniformLocation(programId, "u_Color");


        // матрица
        uProjectionMatrixLocation = glGetUniformLocation(programId, "u_Projection");
        uViewMatrixLocation = glGetUniformLocation(programId, "u_View");
        uPositionLocation = glGetUniformLocation(programId, "u_Translation");
    }

    private void createProjectionMatrix(int width, int height) {
        float ratio;
        float left = -1;
        float right = 1;
        float bottom = -1;
        float top = 1;
        if (width > height) {
            ratio = (float) width / height;
            left *= ratio;
            right *= ratio;
        } else {
            ratio = (float) height / width;
            bottom *= ratio;
            top *= ratio;
        }

        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    private void createViewMatrix() {
        // точка положения камеры
//        float eyeX = 1;
//        float eyeY = 1;
//        float eyeZ = 3;

        // up-вектор
        float upX = 0;
        float upY = 1;
        float upZ = 0;

        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);

    }


    private void bindMatrix() {
        glUniformMatrix4fv(uProjectionMatrixLocation, 1, false, mProjectionMatrix, 0);
        glUniformMatrix4fv(uViewMatrixLocation, 1, false, mViewMatrix, 0);
    }


    private double i = 0;
    private int r = 3;

    @Override
    public void onDrawFrame(GL10 arg0) {


//        eyeX = (float)(r * Math.sin(i));
//        eyeZ = (float)(r * Math.cos(i));
//        i += 0.01;
        bindData();
        updateView();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


        glLineWidth(1);

        glUniform4f(uPositionLocation, 0, 0, 0, 0);

        glUniform4f(uColorLocation, 0.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_LINES, 0, 2);

        glUniform4f(uColorLocation, 1.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_LINES, 2, 2);

        glUniform4f(uColorLocation, 1.0f, 0.5f, 0.0f, 1.0f);
        glDrawArrays(GL_LINES, 4, 2);

        glUniform4f(uColorLocation, 1, 1, 1, 0);
        glDrawArrays(GL_TRIANGLE_FAN, 6, 4);

//        triangle.update();
//        triangle.draw();
        for(Model model: models) {
            model.update();
            model.draw();
        }

//        sphere.update();
//        sphere.draw();
    }

    public boolean onDown(MotionEvent event) {
        Log.i(TAG, "Down action " + event.getX() + " " + event.getY());
        prevX = event.getX();
        prevY = event.getY();
        return true;
    }

    private float stepX = 0;
    private float stepY = 0;
    private final float STEP_PERIOD = 20;
    public boolean onMove(MotionEvent event) {
        Log.i(TAG, "Move action " + event.getX() + " " + event.getY());
        float dx = event.getX() - prevX;
        float dy = event.getY() - prevY;
        prevX = event.getX();
        prevY = event.getY();

//        centerX += -5 * dx / width;
//        centerY += 5 * dy / height;
        stepX += 4 * dx / width;
        stepY += 4 * dy / height;

        eyeX = r * (float)Math.sin(stepX);
        eyeZ = r * (float)(Math.cos(stepX));
//        eyeZ = r * (float)(Math.cos(stepY));
//        eyeY = r * (float)Math.sin(stepY);
        return true;
    }

    private void updateView() {
        createViewMatrix();
        bindMatrix();
    }



}