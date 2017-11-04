package com.team2851.util;

import com.team2851.robot.auton.Auton;
import com.team2851.robot.subsystem.Subsystem;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Telemetry
{
    private static Telemetry sInstance = new Telemetry();
    private SendableChooser subsystemChooser, autonChooser;
    private Preferences preferences = Preferences.getInstance();
    private DriverStation ds = DriverStation.getInstance();

    private Telemetry() {}

    public static Telemetry getInstance() {
        return sInstance;
    }

    public DriverStation.Alliance getAlliance() { return ds.getAlliance(); }
    public double getBatteryVoltage() { return ds.getBatteryVoltage(); }
    public double getMatchTime() { return ds.getMatchTime(); }

    public void registerAuton(Auton auton) { autonChooser.addObject(auton.getName(), auton); }
    public void registerSubsystem(Subsystem subsystem) { subsystemChooser.addObject(subsystem.getName(), subsystem); }

    public void pushChoosers()
    {
        SmartDashboard.putData("subsystem", subsystemChooser);
        SmartDashboard.putData("autonomous", autonChooser);
    }

    public Auton getAuton() { return (Auton) autonChooser.getSelected(); }
    public Subsystem getSubsystem() { return (Subsystem) subsystemChooser.getSelected(); }

    public void setTestMode(boolean isTest) { preferences.putBoolean("Test Mode", isTest); }
    public boolean getTestMode() { return preferences.getBoolean("Test Mode", false); }
}
