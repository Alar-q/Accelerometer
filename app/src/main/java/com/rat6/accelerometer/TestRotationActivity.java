package com.rat6.accelerometer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.rat6.accelerometer.listeners.RotationListener;

public class TestRotationActivity extends Activity {

    private TextView view;
    private RotationListener rotationListener;
    private StringBuilder stringBuilder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stringBuilder = new StringBuilder();
        rotationListener = new RotationListener(this, SensorManager.SENSOR_DELAY_NORMAL);

        view = new TextView(this);
        setContentView(view);    // Register the sensor listeners
    }

    private void update(){
        stringBuilder.setLength(0);
        stringBuilder.append("azimut: ");
        stringBuilder.append(round(rotationListener.getAzimut()));
        stringBuilder.append(" pitch: ");
        stringBuilder.append(round(rotationListener.getPitch()));
        stringBuilder.append(" roll: ");
        stringBuilder.append(round(rotationListener.getRoll()));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(rotationListener!=null)
            rotationListener.register();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (rotationListener != null)
            rotationListener.unregister();
    }

    private class TextView extends View {

        private Paint paint;

        public TextView(Context context){
            super(context);
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(25);
            paint.setTextAlign(Paint.Align.LEFT);

        }

        protected void onDraw(Canvas canvas){
            update();
            canvas.drawText(stringBuilder.toString(), 50,50, paint);
            invalidate();
        }
    }

    public static float round(float v) {
        return Math.round(v * 1000f) / 1000f;
    }
}
