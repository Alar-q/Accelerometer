package com.rat6.accelerometer.listeners;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

public class RotationListener implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private int frequency;

    // https://developer.android.com/reference/android/hardware/SensorManager#getOrientation(float[],%20float[])
    private float azimut /*z*/, pitch/*x*/, roll/*y*/;


    public RotationListener(Context context, int frequency) {
        this.frequency = frequency;
        mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        register();
    }

    private float[] mGravity;
    private float[] mGeomagnetic;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetic = event.values;
        }
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);

            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                azimut = orientation[0]; // orientation contains: azimut, pitch and roll
                pitch = orientation[1];
                roll = orientation[2];
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    public void register() {
        mSensorManager.registerListener(this, accelerometer, frequency);
        mSensorManager.registerListener(this, magnetometer, frequency);
    }

    public void unregister() {
        mSensorManager.unregisterListener(this);
    }


    public float getAzimut() {
        return azimut;
    }

    public float getPitch() {
        return pitch;
    }

    public float getRoll() {
        return roll;
    }

}

