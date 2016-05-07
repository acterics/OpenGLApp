package com.acterics.openglapp.rendering;

/**
 * Created by User on 07.05.2016.
 */
public class Quaternion {
    Vector v;
    private float w;


    public Quaternion(float x, float y, float z, float w) {
        v = new Vector(x, y, z);
        this.w = w;
    }

    public Quaternion(Vector v, float w) {
        this.v = v;
        this.w = w;
    }

    public Quaternion() {
        v = new Vector();
        w = 1;
    }

    public Quaternion plus(Quaternion q) {
        return new Quaternion(v.plus(q.v), w + q.w);
    }

    public Quaternion minus(Quaternion q) {
        return new Quaternion(v.minus(q.v), w - q.w);
    }

    public Quaternion multilpy(float scalar) {
        return new Quaternion(v.multiply(scalar), w * scalar);
    }

    public Quaternion div(float scalar) {
        return new Quaternion(v.div(scalar), w / scalar);
    }



    public Quaternion multiply(Quaternion q) {
        /*
        *
        * qq' = [vv' + wv' + w'v, ww' - v*v']
        * vv' - cross, v*v' - scalar
        *
        */
        return new Quaternion(v.cross(q.v).plus(q.v.multiply(w)).plus(v.multiply(q.w)), w * q.w - v.multiply(q.v));
    }


    public Quaternion conjugate() {
        /*
        * conjugate(q) = [-v, w]
         */
        return new Quaternion(v.conjugate(), w);
    }



    public float norm() {
        return v.norm() + w * w;
    }

    public Quaternion reverse() {
        /*
        * q^-1 = conjugate(q) / norm(q)
        */
        return conjugate().div(norm());
    }

    public float module() {
        return (float)Math.sqrt(norm());
    }

    public Quaternion normalized() {
        return div(module());
    }

    public Vector toVector() {
        return v;
    }
}
