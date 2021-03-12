package com.retro.androidgames.framework.opengles3d;

import com.retro.androidgames.framework.math.Vector3;

public class Sphere {
    public final Vector3 center = new Vector3();
    public float radius;
    public Sphere(float x, float y, float z, float radius){
        this.center.set(x, y, z);
        this.radius = radius;
    }
}
