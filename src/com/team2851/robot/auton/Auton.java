package com.team2851.robot.auton;

import com.team2851.robot.auton.actions.Action;

public abstract class Auton {
    private String name = "NULL Auton";
    protected Auton(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    protected boolean isAlive = false;

    protected abstract void routine() throws AutonEndedException;

    public void run()
    {
        isAlive = true;
        try {
            routine();
        } catch (AutonEndedException e) {
            System.out.println("Auton Ended Unexpectedly");
            return;
        }
        done();
        System.out.println("Auton Complete");
    }

    public void done() { }

    public void stop() { isAlive = false; }

    public boolean isAlive() throws AutonEndedException
    {
        if (!isAlive) throw new AutonEndedException();
        return isAlive;
    }

    public void runAction(Action action) throws AutonEndedException {
        isAlive();
        action.start();

        while (isAlive() && !action.isFinished())
        {
            action.update();
            try { Thread.sleep(0, 1); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        action.done();
    }
}
