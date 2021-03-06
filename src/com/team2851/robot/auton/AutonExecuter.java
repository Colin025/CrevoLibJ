package com.team2851.robot.auton;

public class AutonExecuter
{
    private Auton mAuton;
    private Thread mThread = null;

    public void setAuton(Auton auton)
    {
        mAuton = auton;
    }

    public void start()
    {
        if (mThread == null)
        {
            mThread = new Thread()
            {
                @Override
                public void run()
                {
                    if (mAuton != null)
                        mAuton.run();
                }
            };
            mThread.start();
        }
    }

    public void stop()
    {
        if (mAuton != null && mAuton.isAlive)
            mAuton.stop();

        mThread = null;
    }
}
