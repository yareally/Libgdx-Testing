package com.badlogic.androidgames.framework.impl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerHandler implements SensorEventListener
{
    private float accelX;
    private float accelY;
    private float accelZ;

    /**
     *
     * @param context
     */
    public AccelerometerHandler(Context context)
    {
        SensorManager manager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor accelerometer = manager.getSensorList(
                    Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    /**
     *
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // nothing to do here 
    }

    /**
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        this.accelX = event.values[0];
        this.accelY = event.values[1];
        this.accelZ = event.values[2];
    }

    /**
     *
     * @return
     */
    public float getAccelX()
    {
        return this.accelX;
    }

    /**
     *
     * @return
     */
    public float getAccelY()
    {
        return this.accelY;
    }

    /**
     * 
     * @return
     */
    public float getAccelZ()
    {
        return this.accelZ;
    }
} 