package com.copyzero.JackLumber2;

/**
 * Created by DOug on 9/24/13.
 */
public class Timer
{
    float startTime;
    float endTime;
    boolean stopped=true;

    public Timer(float endTime)
    {
        this.endTime = endTime;
        stopped =false;
    }
    public void update(float deltaTime)
    {
        startTime +=deltaTime;
        if(startTime >= endTime)
            stopped = true;

        /*Log.v("CHECKS","STARTTIME : " + Float.toString(startTime));
        Log.v("CHECKS","ENDTIME : " + Float.toString(endTime));*/
    }
    public void reset(float endTime)
    {
        startTime = 0;
        this.endTime = endTime;
        stopped = false;
    }
    public boolean running()
    {
        if(stopped)
            return false;
        else
            return true;

    }
    public float getTimeRemaining()
    {
    	return (endTime-startTime);
    }
    public float getTimeElapsed()
    {
    	return startTime;
    }
}
