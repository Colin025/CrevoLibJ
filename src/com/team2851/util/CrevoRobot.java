package com.team2851.util;

import com.team2851.robot.auton.*;
import com.team2851.robot.subsystem.Subsystem;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Vector;

public class CrevoRobot extends IterativeRobot
{
    private AutonExecuter mAutonExecuter = new AutonExecuter();
    private Vector<Subsystem> mSubsystems = new Vector<>();
    private SendableChooser autonomousChooser, subsystemChooser;
    private Subsystem testSubsystem;
    private Telemetry telemetry = Telemetry.getInstance();

    public final void registerAuton(Auton auton)
    {
        registerAuton(auton);
    }


    public CrevoRobot()
    {
        autonomousChooser = new SendableChooser();
        subsystemChooser = new SendableChooser();
    }

    public final void registerSubsystem(Subsystem subsystem)
    {
        mSubsystems.add(subsystem);
        telemetry.registerSubsystem(subsystem);
        System.out.println(subsystem.toString());
    }

    @Override
    public final void robotInit()
    {
        for (Subsystem s : mSubsystems) s.start();
        telemetry.pushChoosers();
    }

    @Override
    public final void autonomousInit()
    {
        mAutonExecuter.setAuton(telemetry.getAuton());
        mAutonExecuter.start();
    }

    @Override
    public void teleopInit()
    {
        if (telemetry.getTestMode())
        {
            for (Subsystem subsystem : mSubsystems)
                subsystem.setCommand(subsystem.getTeleopCommand());
        } else
        {
            testSubsystem = (Subsystem) subsystemChooser.getSelected();
            testSubsystem.setCommand(testSubsystem.getTeleopCommand());
        }
    }

    @Override
    public void disabledInit()
    {
        for (Subsystem s : mSubsystems)
            s.setCommand(s.getDefaultCommand());
        mAutonExecuter.stop();
    }

    // Eliminated Functions
    @Override
    public final void autonomousPeriodic() {}
    @Override
    public void teleopPeriodic() {}
}
