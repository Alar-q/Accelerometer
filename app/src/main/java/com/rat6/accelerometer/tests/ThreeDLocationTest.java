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

import static com.retro.androidgames.framework.math.Calculations.round;

public class ThreeDLocationTest extends Activity {

    private TextView textView;

    private StringBuilder stringBuilder;

    private AccelerationListener linearAccelerationListener;
    private AccelerationListener gravity;

    private Vector3 location3D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        stringBuilder = new StringBuilder();
        location3D = new Vector3(0, 0, 0);

        linearAccelerationListener = new AccelerationListener(this, Sensor.TYPE_GYROSCOPE, SensorManager.SENSOR_DELAY_GAME);
        gravity = new AccelerationListener(this, Sensor.TYPE_GRAVITY, SensorManager.SENSOR_DELAY_GAME);

        textView = new TextView(this);
        setContentView(textView);
    }

    private void getValues(StringBuilder builder, AccelerationListener listener){
        stringBuilder.setLength(0);
        builder.append("x:");
        builder.append(round(listener.getAccelX(), 5));
        builder.append(" y:");
        builder.append(round(listener.getAccelY(), 5));
        builder.append(" z:");
        builder.append(round(listener.getAccelZ(), 5));
    }

    public void update(){
        //Положить в StringBuilder 3-мерную координату - где сейчас находится телефон.
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
            canvas.drawText("Location: " + stringBuilder.toString(), 50,50, paint);

            invalidate();
        }
    }



}
