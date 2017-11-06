package com.team2851.robot.auton.actions;

import edu.wpi.first.wpilibj.Timer;

public class WaitAction implements Action
{
    private float mWaitTime;
    private float mStartTime;

    public WaitAction(float waitTime)
    {
        mWaitTime = waitTime;
    }

    @Override
    public boolean isFinished()
    {
        return Timer.getFPGATimestamp() - mStartTime >= mWaitTime;
    }

    @Override
    public void update()
    {

    }

    @Override
    public void done()
    {

    }

    @Override
    public void start()
    {
        mStartTime = (float)Timer.getFPGATimestamp();
    }
}
