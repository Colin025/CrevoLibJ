package com.team2851.robot;

import com.ctre.CANTalon;
import com.team2851.robot.auton.routines.TestAuton;
import com.team2851.robot.subsystem.systems.*;
import com.team2851.util.*;
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
        motor = new CANTalon(2);
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
