package com.retro.androidgames.framework.math;

public class Calculations {
    public static float round(float v, int roundingTo){
        float ten = (float) Math.pow(v, roundingTo);
        return Math.round(v * ten) / ten;
    }
}
