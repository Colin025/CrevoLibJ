package com.team2851.robot.subsystem.systems;

import com.ctre.CANTalon;
import com.team2851.robot.RobotConstants;
import com.team2851.robot.subsystem.Command;
import com.team2851.robot.subsystem.Subsystem;
import com.team2851.util.logging.Loggable;

public class TestSubsystem extends Subsystem
{
    CANTalon testTalon;
    RobotConstants constants = RobotConstants.getInstance();

    public TestSubsystem()
    {
        super("Test Subsystem");
    }

    @Override
    public void init() {
        testTalon = new CANTalon(constants.TALON_TEST_PORT);
    }

    @Override
    public Command getDefaultCommand() {
        return new CommandIdle();
    }

    @Override
    public Command getTeleopCommand() {
        return new CommandTeleop();
    }

    @Override
    public void printData() {

    }

    public class CommandTeleop implements Command, Loggable
    {

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public void start() {
            System.out.println("[Test Subsystem]: Command TELEOP is starting");
            testTalon.set(0);
        }

        @Override
        public void update() {
            if (constants.driveController.a.getState())
                testTalon.set(1);
            else
                testTalon.set(0);

            System.out.println("[Test Subsystem]: " + constants.driveController.a.getState());
        }

        @Override
        public void done() {

        }

        @Override
        public void interrupt() {
            System.out.println("[Test Subsystem]: Command TELEOP was interrupted");

        }

        @Override
        public void printData() {

        }
    }

    public class CommandIdle implements Command, Loggable
    {

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public void start() {
            System.out.println("[Test Subsystem]: Command IDLE is starting");
        }

        @Override
        public void update() {

        }

        @Override
        public void done() {

        }

        @Override
        public void interrupt() {
            System.out.println("[Test Subsystem]: Command IDLE was interrupted");
        }

        @Override
        public void printData() {

        }
    }
}
