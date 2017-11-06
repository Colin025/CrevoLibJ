package com.team2851.robot.subsystem.systems;

import com.ctre.CANTalon;
import com.team2851.robot.RobotConstants;
import com.team2851.robot.subsystem.Command;
import com.team2851.robot.subsystem.Subsystem;
import com.team2851.util.logging.Loggable;
import com.team2851.util.logging.Logger;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveTrain extends Subsystem
{
    CANTalon leftFront, leftRear, rightFront, rightRear;
    RobotDrive robotDrive;
    RobotConstants robotConstants = RobotConstants.getInstance();

    public enum DriveMode { TANK, ARCADE }

    public DriveTrain()
    {
        super("Drive Train");
    }

    @Override
    public void init()
    {
        System.out.println("asdfghjkl;");
        leftFront = new CANTalon(robotConstants.TALON_LEFT_FRONT_PORT);
        leftFront.setInverted(true);
        leftRear = new CANTalon(robotConstants.TALON_LEFT_REAR_PORT);
        leftRear.setControlMode(CANTalon.TalonControlMode.Follower.getValue());
        leftRear.set(robotConstants.TALON_LEFT_FRONT_PORT);

        rightFront = new CANTalon(robotConstants.TALON_RIGHT_FRONT_PORT);
        rightRear = new CANTalon(robotConstants.TALON_RIGHT_REAR_PORT);
        rightRear.setControlMode(CANTalon.TalonControlMode.Follower.getValue());
        rightRear.set(robotConstants.TALON_RIGHT_FRONT_PORT);

        robotDrive = new RobotDrive(leftFront, rightFront);
    }

    @Override
    public Command getDefaultCommand()
    {
        return new CommandIdle();
    }

    @Override
    public Command getTeleopCommand()
    {
        return new CommandTeleop();
    }

    class CommandIdle implements Command
    {
        private boolean hasInit = false, isAlive = false, wasInterrupted = false;

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public void start()
        {
            isAlive = true;
        }

        @Override
        public void update() {
            hasInit = true;
        }

        @Override
        public void done() {
            isAlive = false;
        }

        @Override
        public void interrupt() {
            wasInterrupted = true;
            isAlive = false;
        }
    }

    class CommandTeleop implements Command
    {
        private Logger logger = Logger.getInstance();
        private boolean hasInit = false, isAlive = false, wasInterrupted = false;
        private DriveMode driveMode = DriveMode.TANK;

        public void setDriveMode(DriveMode driveMode)
        {
            this.driveMode = driveMode;
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public void start()
        {
            System.out.println("[Drive Train] Starting teleop DRIVE TRAIN");
            isAlive = true;
            robotDrive.setLeftRightMotorOutputs(0, 0);
        }

        @Override
        public void update()
        {
            hasInit = true;
            switch (driveMode)
            {
                case TANK:
                {
                    robotDrive.tankDrive(robotConstants.driveController.leftY.getValue(), robotConstants.driveController.rightY.getValue());
                    break;
                }

                case ARCADE:
                {
                    robotDrive.arcadeDrive(robotConstants.driveController.leftY.getValue(), robotConstants.driveController.rightX.getValue());
                }
            }
        }

        @Override
        public void done()
        {

        }

        @Override
        public void interrupt() {
            robotDrive.setLeftRightMotorOutputs(0, 0);
            wasInterrupted = true;
            isAlive = false;
        }
    }

    class CommandDriveDistance implements Command
    {
        private boolean isFinished = false;
        private double distance;

        public CommandDriveDistance(double distance)
        {
            this.distance = distance;
        }

        @Override
        public boolean isFinished() {
            return isFinished;
        }

        @Override
        public void start() {

        }

        @Override
        public void update() {

        }

        @Override
        public void done() {

        }

        @Override
        public void interrupt() {

        }
    }

    public void printData()
    {

    }
}
