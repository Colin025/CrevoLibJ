package com.team2851.util.logging;

import java.util.Vector;

public class Logger extends Thread
{
    // Todo: Implement file logging
    // Todo: File logging as format such as csv, xml, or json as well as program to display output data
    // Todo: Add timestamp to data files
    // Todo: Smoother integration with commands and pushLine function to allow a class to push a log without being loggable or for one time requests
    private static Logger sInstance = new Logger();
    private Vector<Loggable> logs = new Vector<>();
    private Vector<String> messages = new Vector<>();
    private Thread mThread;
    private boolean isRunning = false;

    private final String filePath = "/home/lvuser/logs/";
    private String fileName = "null.log";

    public enum RobotMode { INIT, TELEOP, AUTON }
    private RobotMode mRobotMode;

    public static Logger getInstance()
    {
        return sInstance;
    }

    @Deprecated
    public void setPath(String path)
    {
        fileName = path + ".log";
    }

    @Deprecated
    public void setRobotMode(RobotMode mode)
    {
        mRobotMode = mode;
    }

    public synchronized void registerLoggableObject(Loggable loggable)
    {
        logs.add(loggable);
    }

    private synchronized void logData()
    {
        for (Loggable log : logs)
            log.printData();
    }

    public void quit()
    {
        isRunning = false;
    }

    public void run()
    {
        while (isRunning)
        {
            logData();
        }
    }

    public void start()
    {
        if (mThread == null)
        {
            mThread = new Thread(this, "Logger");
            isRunning = true;
            mThread.start();
        }
    }
}
