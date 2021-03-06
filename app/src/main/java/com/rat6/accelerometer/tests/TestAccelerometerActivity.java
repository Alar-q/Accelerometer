package com.rat6.accelerometer.tests;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.rat6.accelerometer.listeners.AccelerationListener;
import com.retro.androidgames.framework.math.Vector3;

public class TestAccelerometerActivity extends Activity {

    private TextView textView;

    private StringBuilder stringBuilder;

    private AccelerationListener acceleration;
    private AccelerationListener linearAcceleration;
    private AccelerationListener gravity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        stringBuilder = new StringBuilder();

        acceleration = new AccelerationListener(this, Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL);
        linearAcceleration = new AccelerationListener(this, Sensor.TYPE_LINEAR_ACCELERATION, SensorManager.SENSOR_DELAY_NORMAL); //linear = acceleration - gravity
        gravity = new AccelerationListener(this, Sensor.TYPE_GRAVITY, SensorManager.SENSOR_DELAY_NORMAL);

        textView = new TextView(this);
        setContentView(textView);
    }

    private void getValues(StringBuilder builder, AccelerationListener listener){
        stringBuilder.setLength(0);
        builder.append("x:");
        builder.append(round(listener.getAccelX()));
        builder.append(" y:");
        builder.append(round(listener.getAccelY()));
        builder.append(" z:");
        builder.append(round(listener.getAccelZ()));
    }


    private class TextView extends View{

        private Paint paint;

        public TextView(Context context){
            super(context);
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(25);
            paint.setTextAlign(Paint.Align.LEFT);

        }

        protected void onDraw(Canvas canvas){
            getValues(stringBuilder, acceleration);
            canvas.drawText("Acceleration: " + stringBuilder.toString(), 50,50, paint);

            getValues(stringBuilder, linearAcceleration);
            canvas.drawText("Linear: " + stringBuilder.toString(), 50,100, paint);

            getValues(stringBuilder, gravity);
            canvas.drawText("Gravity: " + stringBuilder.toString(), 50,150, paint);

            invalidate();
        }
    }



    public static float round(float v){
        return Math.round(v * 1000f) / 1000f;
    }
}
