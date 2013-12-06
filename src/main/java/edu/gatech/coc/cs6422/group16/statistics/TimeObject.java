package edu.gatech.coc.cs6422.group16.statistics;

public class TimeObject
{
    long endTime;

    long startTime;

    public long difference()
    {
        return Math.max(this.endTime - this.startTime, 0);
    }

    public void reset()
    {
        this.startTime = 0;
        this.endTime = 0;
    }

    public void start()
    {
        this.startTime = System.currentTimeMillis();
    }

    public void stop()
    {
        this.endTime = System.currentTimeMillis();
    }
}
