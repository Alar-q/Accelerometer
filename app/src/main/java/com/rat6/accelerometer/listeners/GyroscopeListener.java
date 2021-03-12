package com.rat6.accelerometer.listeners;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

//Все точно так, как и в ускорении
public class GyroscopeListener implements SensorEventListener {

    private SensorManager manager;
    private Sensor sensor;
    private float angularVelX, angularVelY, angularVelZ;
    private int frequency;

    public GyroscopeListener(Context context, int frequency){ // frequency = SensorManager.SENSOR_DELAY_UI
        this.frequency = frequency;
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        manager.registerListener(this, sensor, frequency);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        angularVelX = event.values[0];
        angularVelY = event.values[1];
        angularVelZ = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public float getAngularVelX() {
        return angularVelX;
    }
    public float getAngularVelY() {
        return angularVelY;
    }
    public float getAngularVelZ() {
        return angularVelZ;
    }

    public void onResume() {
        manager.registerListener(this, sensor, frequency);
    }

    public void onPause() {
        manager.unregisterListener(this);
    }
}


