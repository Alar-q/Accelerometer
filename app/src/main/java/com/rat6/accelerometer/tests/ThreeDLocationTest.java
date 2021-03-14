package com.rat6.accelerometer.tests;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.rat6.accelerometer.listeners.AccelerationListener;
import com.retro.androidgames.framework.math.Vector3;

import java.util.ArrayList;
import java.util.List;

import static com.retro.androidgames.framework.math.Calculations.round;


//dx=0.0010103409 dy=-5.7157533E-5=-0.000057157533 dz=-0.038251683 times=52
public class ThreeDLocationTest extends Activity {

    private TextView textView;

    private StringBuilder stringBuilder;

    private AccelerationListener linearAccelerationListener;

    private Vector3 location3D, velocity, acceleration, jerk;
    private final float dx = 0.0010103409f, dy =-0.00005715f, dz = -0.038251683f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        stringBuilder = new StringBuilder();

        location3D = new Vector3();
        velocity = new Vector3();
        acceleration = new Vector3();
        jerk = new Vector3();

        linearAccelerationListener = new AccelerationListener(this, Sensor.TYPE_LINEAR_ACCELERATION, SensorManager.SENSOR_DELAY_GAME);

        textView = new TextView(this);
        setContentView(textView);
    }



    float accX, accY, accZ, deltaTime2, deltaTime3;
    /** обновить 3-мерную координату - где сейчас находится телефон */
    public void updateLocation(float deltaTime){
        accX = linearAccelerationListener.getAccelX() - dx;//round(linearAccelerationListener.getAccelX(), 5);
        accY = linearAccelerationListener.getAccelY() - dy;
        accZ = linearAccelerationListener.getAccelZ() - dz;

        accX = round(accX, 3);
        accY = round(accY, 3);
        accZ = round(accZ, 3);

        if((accX>0 && accX<0.001f) || (accX<0 && accX>-0.001f)){
            accX=0;
        }
        if((accY>0 && accY<0.001f) || (accY<0 && accY>-0.001f)){
            accY=0;
        }
        if((accZ>0 && accZ<0.001f) || (accZ<0 && accZ>-0.001f)){
            accZ=0;
        }


    }

    private void updateByJerk(float deltaTime){

        deltaTime2 = deltaTime * deltaTime;
        deltaTime3 = deltaTime2 * deltaTime;

        accX = linearAccelerationListener.getAccelX() - dx;//round(linearAccelerationListener.getAccelX(), 5);
        accY = linearAccelerationListener.getAccelY() - dy;
        accZ = linearAccelerationListener.getAccelZ() - dz;

        accX = round(accX, 3);
        accY = round(accY, 3);
        accZ = round(accZ, 3);

        if((accX>0 && accX<0.001f) || (accX<0 && accX>-0.001f)){
            accX=0;
        }
        if((accY>0 && accY<0.001f) || (accY<0 && accY>-0.001f)){
            accY=0;
        }
        if((accZ>0 && accZ<0.001f) || (accZ<0 && accZ>-0.001f)){
            accZ=0;
        }


        // Расчитываем рывок. Рывок - изменение ускорения по истечению времени, то есть ускорение ускорения.
        // j(t) = (a-a0) / tб,
        // где a0 - бывшее ускорение на предыдущем обновлении, a - настоящее ускорение в данный момент
        jerk.set(accX, accY, accZ);
        jerk.sub(acceleration);
        jerk.div(deltaTime);
        //************************************************************************************************


        // x(t) = x0 + (a0 * t^2 / 2) + (j * t^3 / 6),
        // где x0 - бывшая координа или просто location3D.x
        Vector3 vector3 = new Vector3();
        vector3.set(velocity);
        vector3.mul(deltaTime);

        location3D.add(vector3);

        vector3.set(acceleration);
        vector3.mul(deltaTime2);
        vector3.div(2f);

        location3D.add(vector3);

        vector3.set(jerk);
        vector3.mul(deltaTime3);
        vector3.div(6f);

        location3D.add(vector3);
        //*************************************************


        // velocity - настоящая скорость в данный момент
        // V(t) = V0 + (a0 * t) + (j * t^2 / 2),
        // где V0 - бывшая скорость на предыдущем обновлении,
        // a0 - бывшее ускорение на предыдущем обновлении.
        vector3.set(acceleration);
        vector3.mul(deltaTime);
        velocity.add(vector3);

        vector3.set(jerk);
        vector3.mul(deltaTime2);
        vector3.div(2f);
        velocity.add(vector3);
        //*****************************************************


        // acceleration - бывшее ускорение, нужно обновить, чтобы сделать бывшем для следующего обновления.
        // a(t) = a0 + (j * t), где а0 - бывшее ускорение,
        // acceleration до этого момента была бывшим ускорением, значит мы можем просто прибавить j*t
        //vector3.set(jerk);
        //vector3.mul(deltaTime);
        //acceleration.add(vector3);
        //************************************************************************************************

        acceleration.set(accX, accY, accZ);

    }

    private void getValues(StringBuilder builder, Vector3 listener){
        stringBuilder.setLength(0);
        builder.append("x:");
        builder.append(round(listener.x, 3));
        builder.append(" y:");
        builder.append(round(listener.y, 3));
        builder.append(" z:");
        builder.append(round(listener.z, 3));
    }




    private class TextView extends View {

        private Paint paint;

        private long lastTime;
        private float deltaTime;
        private FPS fps;

        public TextView(Context context){
            super(context);

            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(25);
            paint.setTextAlign(Paint.Align.LEFT);

            lastTime = System.nanoTime();
            fps = new FPS();
        }

        protected void onDraw(Canvas canvas){
            deltaTime =  (System.nanoTime() - lastTime) / 1000000000.0f;
            lastTime = System.nanoTime();
            updateLocation(deltaTime);

            getValues(stringBuilder, location3D);
            canvas.drawText("Location: " + stringBuilder.toString(), 50,50, paint);

            getValues(stringBuilder, velocity);
            canvas.drawText("Velocity: " + stringBuilder.toString(), 50,100, paint);

            getValues(stringBuilder, acceleration);
            canvas.drawText("acceleration: " + stringBuilder.toString(), 50,150, paint);

            canvas.drawText("velocity: " + velocity.len(), 50,200, paint);

            fps.fps();

            invalidate();//вызываем onDraw заново(вечный цикл)
        }
    }

    public class FPS {
        private long lastTime = System.nanoTime();
        private int fps = 0;

        private float accX1, accY1, accZ1;
        List<Float> dxs = new ArrayList<>(), dys = new ArrayList<>(), dzs = new ArrayList<>();

        public void fps(){
            fps++;

            /*
            accX1+=round(accX, 5);
            accY1+=round(accY, 5);
            accZ1+=round(accZ, 5);
             */

            if(System.nanoTime()-lastTime>1000000000){
                Log.d("FPS", ""+fps);
                //countBias();
                fps=0;
                lastTime=System.nanoTime();
            }
        }

        private void countBias(){
            dxs.add(accX1/fps);
            dys.add(accY1/fps);
            dzs.add(accZ1/fps);
            Log.d("Acceleration_Bios", "dx=" + mean(dxs) + " dy=" + mean(dys) + " dz=" + mean(dzs) + " times="+dxs.size());
            accX1=0; accY1=0; accZ1=0;
        }

        private float mean(List<Float> list){
            float sum = 0;
            for(float f: list){
                sum+=f;
            }
            return sum/(float)list.size();
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        linearAccelerationListener.onResume();
    }
    @Override
    public void onPause(){
        super.onPause();
        linearAccelerationListener.onPause();
    }
}
