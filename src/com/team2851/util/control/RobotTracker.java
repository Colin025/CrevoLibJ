package com.team2851.util.control;

public class RobotTracker
{
    private final int cpr = 250;

    private static Point mCurrentPoint;
    private static RobotTracker mInstance;
    private int mLastLeftEncValues, mLastRightEncValues;
    private static float mCurrentHeading;
    private enum DriveTrain { Differential }

    private RobotTracker() {}

    public static RobotTracker getInstance()
    {
        return mInstance;
    }

    public static void setCurrentPoint(Point point)
    {
        mCurrentPoint = point;
    }

    public static void setCurrentHeading(float heading)
    {
        mCurrentHeading = heading;
    }

    public static Point getmCurrentPoint()
    {
        return mCurrentPoint;
    }

    public static float getCurrentHeading() {
        return mCurrentHeading;
    }

    public void updatePosition()
    {
        switch (DriveTrain.Differential)
        {
            case Differential:
            {

            }
        }
    }
}
