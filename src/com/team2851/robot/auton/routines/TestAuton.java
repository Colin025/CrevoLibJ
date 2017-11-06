package com.team2851.robot.auton.routines;

import com.team2851.robot.auton.Auton;
import com.team2851.robot.auton.AutonEndedException;
import com.team2851.robot.auton.actions.TestAction;
import com.team2851.util.Telemetry;

public class TestAuton extends Auton
{
    private TestAuton()
    {
        super("Test Auton");
    }

    @Override
    protected void routine() throws AutonEndedException {
        while(true)runAction(new TestAction());
    }
}
