package com.acterics.openglapp.rendering;

/**
 * Created by User on 07.05.2016.
 */
public class Vector {
    private float x;
    private float y;
    private float z;

    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector plus(Vector v) {
        return new Vector(x + v.x, y + v.y, z + v.z);
    }

    public Vector minus(Vector v) {
        return new Vector(x - v.x, y - v.y, z - v.z);
    }

    public Vector multiply(float scalar) {
        return new Vector(x * scalar, y * scalar, z * scalar);
    }

    public float multiply(Vector v) {
        return x * v.x + y * v.y;
    }

    public Vector div(float scalar) {
        return new Vector(x / scalar, y / scalar, z / scalar);
    }

    public Vector cross(Vector v) {
        /*
        *
        * a(ax, ay, az), b(bx, by, bz)
        * a * b = (ay*bz - az*by, az*bx - ax*bz, ax*by - ay*bx)
        *
        */
        return new Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }

    public float module() {
        return (float)Math.sqrt(norm());
    }

    public float norm() {
        return x * x + y * y + z * z;
    }


    public Vector normalized() {
        return div(module());
    }

    public Quaternion toQuaternion() {
        return new Quaternion(this, 0);
    }

    public Vector conjugate() {
        return new Vector(-x, -y, -z);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
