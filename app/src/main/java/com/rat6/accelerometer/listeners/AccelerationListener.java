package com.rat6.accelerometer.listeners;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerationListener implements SensorEventListener {

    private SensorManager manager;
    private Sensor sensor;
    private float accelX, accelY, accelZ;
    private int frequency;

    public AccelerationListener(Context context, int TYPE_ACCELERATION, int frequency){ //TYPE_ACCELERATION = Sensor.TYPE_LINEAR_ACCELERATION, frequency = SensorManager.SENSOR_DELAY_UI
        this.frequency = frequency;
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(TYPE_ACCELERATION);
        manager.registerListener(this, sensor, frequency);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        accelX = event.values[0];
        accelY = event.values[1];
        accelZ = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public float getAccelX() {
        return accelX;
    }
    public float getAccelY() {
        return accelY;
    }
    public float getAccelZ() {
        return accelZ;
    }

    public void onResume() {
        manager.registerListener(this, sensor, frequency);
    }

    public void onPause() {
        manager.unregisterListener(this);
    }
}
