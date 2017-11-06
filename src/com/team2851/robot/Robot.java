package com.team2851.robot;

import com.ctre.CANTalon;
import com.team2851.util.ConfigFile;
import com.team2851.util.ElementNotFoundException;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot
{
    CANTalon motor;
    Joystick control;
    Timer timer;
    public void robotInit()
    {
        try {
            motor = ConfigFile.getInstance().getCANTalon("motor");
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        }
        timer = new Timer();
        control = new Joystick(2);
    }

    public void teleopInit()
    {
        timer.start();
    }

    public void teleopPeriodic()
    {
        if (control.getRawButton(1)) motor.set(0.5);
        else motor.set(0);
    }
}
