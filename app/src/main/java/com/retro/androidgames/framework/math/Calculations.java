package com.retro.androidgames.framework.math;

public class Calculations {
    /*
    public static float round(float v, int roundingTo){
        float ten = (float) Math.pow(v, roundingTo);
        float a = Math.round(v * ten) / ten;
        return v;
    }
     */

    public static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        double tmp = number * pow;
        return (float) (int) ((tmp - (int) tmp) >= 0.5 ? tmp + 1 : tmp) / pow;
    }
}
