package com.team2851.robot.subsystem;

import com.team2851.util.logging.Loggable;
import edu.wpi.first.wpilibj.hal.HAL;

public abstract class Subsystem extends Thread implements Loggable
{
    // TODO: Cleanup and possibly redesign class. It works for now but I am not satisfied
    // Fields
    private Thread mThread;
    private String mName = "Unknown Subsystem";
    protected boolean isAlive = false, hasInit = false; // Include is alive in any nested while loops
    private Command command = getDefaultCommand();

    public String toString()
    {
        return mName;
    }

    // Abstract Functions
    public abstract void init(); // Runs when the subsystem starts
    public abstract Command getDefaultCommand();
    public abstract Command getTeleopCommand();

    // Functions
    public Subsystem(String name)
    {
        mName = name;
    }

    public synchronized void setCommand(Command command)
    {
        if (!this.command.isFinished())
            this.command.interrupt();

        hasInit = false;
        this.command = command;
    }

    private synchronized void runCommand()
    {
        if (!hasInit)
        {
            command.start();
            hasInit = true;
        }

        if (!command.isFinished())
            command.update();
        else
            command.done();
    }

    @Override
    public void run()
    {
        while (isAlive)
        {
            runCommand();
            try {
                Thread.sleep(0,1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start()
    {
        if (mThread == null)
        {
            mThread = new Thread(this, mName);
            init();
            isAlive = true;
            mThread.start();
        }
    }

    public boolean isSubsystemActive()
    {
        return !command.isFinished();
    }

    public void halt()
    {
        if (!command.isFinished())
            command.interrupt();
        isAlive = false;
    }
}
