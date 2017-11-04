package com.team2851.robot.auton.actions;

import com.team2851.robot.RobotConstants;

public class TestAction implements Action
{
    private boolean isFinished;
    int i = 0;
    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void update() {
        if (i < 255)
            System.out.println(i);
        else
            isFinished = true;
        i++;
    }

    @Override
    public void done() {

    }

    @Override
    public void start() {

    }
}
