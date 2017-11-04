package com.team2851.util;

import com.ctre.CANTalon;
import com.team2851.robot.RobotConstants;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigFile
{
    private SAXBuilder saxBuilder = new SAXBuilder();
    private Document document;

    private static ConfigFile sInstance = new ConfigFile();

    private ConfigFile()
    {
        File file = new File(RobotConstants.getInstance().configFilePath);
        try {
            document = saxBuilder.build(file + "robot.xml");
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigFile getInstance() {
        return sInstance;
    }

    public String getRobotName()
    {
        return document.getRootElement().getAttributeValue("name");
    }

    public CANTalon getCANTalon(String name) throws ElementNotFoundException
    {
        CANTalon talon;
        Element element = null;
        int port;
        double p = 0, i = 0, d = 0, f = 0;
        boolean pidEnabled = true, fEnabled = true, isInverted = false;

        try {
            element = getElement(document.getRootElement(), name);
        } catch (ElementNotFoundException e) {
            System.err.println("CANTalon [" + name + "] was not found in XML sheet");
            throw e;
        }

        try {
            port = element.getAttribute("port").getIntValue();
        } catch (DataConversionException e) {
            System.err.println("CANTalon [" + name + "] encountered a DataConversionException for port. Check port data type.");
            throw new ElementNotFoundException();
        } catch (NullPointerException e) {
            System.err.println("CANTalon [" + name + "] does not have a defined port");
            throw new ElementNotFoundException();
        }

        try {
            p = element.getAttribute("p").getDoubleValue();
            i = element.getAttribute("i").getDoubleValue();
            d = element.getAttribute("d").getDoubleValue();
        } catch (DataConversionException e) {
            System.err.println("CANTalon [" + name + "] encountered a DataConversionException for pid. Check pid data type. Not using pid");
            pidEnabled = false;
        } catch (NullPointerException e) {
            System.err.println("CANTalon [" + name + "] does not have a p, i, or d value defined. Not using pid.");
            pidEnabled = false;
        }

        try {
            f = element.getAttribute("f").getDoubleValue();
        } catch (DataConversionException e) {
            System.err.println("CANTalon [" + name + "] encountered a DataConversionException for f. Check f data type. Not using f");
            fEnabled = false;
        } catch (NullPointerException e) {
            System.err.println("CANTalon [" + name + "] does not have a f value defined. Not using f.");
            fEnabled = false;
        }

        try {
            isInverted = element.getAttribute("isInverted").getBooleanValue();
        } catch (DataConversionException e) {
            System.err.println("CANTalon [" + name + "] encountered a DataConversionException for isInverted. Check data type. isInverted is false");
            isInverted = false;
        } catch (NullPointerException e) {
            System.err.println("CANTalon [" + name + "] does not have a value for isInverted defined. isInverted is false");
        }

        talon = new CANTalon(port);

        if (pidEnabled)
            talon.setPID(p, i, d);

        talon.setInverted(isInverted);

        return talon;
    }

    public static Controller getController(String configFile) throws ElementNotFoundException {
        File file = new File(RobotConstants.getInstance().configFilePath + configFile);
        Document doc = null;
        try {
            doc = new SAXBuilder().build(file);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Controller controller;

        Element rootElement;
        rootElement = doc.getRootElement();

        try {
            controller = new Controller(rootElement.getAttribute("port").getIntValue());
        } catch (DataConversionException e) {
            System.err.println("Controller could not be configured, port could not be parsed.");
            throw new ElementNotFoundException();
        } catch (NullPointerException e) {
            System.err.println("Controller could not be configured, port was not defined.");
            throw new ElementNotFoundException();
        }

        List<Element> buttonElements = rootElement.getChildren("Button");
        List<Element> axisElements = rootElement.getChildren("Axis");

        for (Element e : buttonElements)
        {
            String button;
            try {
                button = e.getAttributeValue("id");

            } catch (NullPointerException ex) {
                System.err.println("Button id was not defined, button will not be initialized");
                continue;
            }

            Controller.ButtonMode mode = null;
            try {
                if (e.getAttributeValue("mode").equals("raw")) {
                    mode = Controller.ButtonMode.Raw;
                }
                else if (e.getAttributeValue("mode").equals("toggle")) {
                    mode = Controller.ButtonMode.Toggle;
                }
                else {
                    mode = Controller.ButtonMode.Raw;
                    System.err.println("Invalid button mode for a, setting to raw");
                }
            } catch (NullPointerException ex) {
                System.err.println("Button mode not defined, setting to raw");
                mode = Controller.ButtonMode.Raw;
            }

            switch (button)
            {
                case "a":
                {
                    Controller.ButtonID id = Controller.ButtonID.A;
                    controller.a = new Controller.Button(id, mode, controller.joystick);
                    break;
                }

                case "b":
                {
                    Controller.ButtonID id = Controller.ButtonID.B;
                    controller.b = new Controller.Button(id, mode, controller.joystick);
                    break;
                }

                case "x":
                {
                    Controller.ButtonID id = Controller.ButtonID.X;
                    controller.x = new Controller.Button(id, mode, controller.joystick);
                    break;
                }

                case "y":
                {
                    Controller.ButtonID id = Controller.ButtonID.Y;
                    controller.y = new Controller.Button(id, mode, controller.joystick);
                    break;
                }

                case "start":
                {
                    Controller.ButtonID id = Controller.ButtonID.START;
                    controller.start = new Controller.Button(id, mode, controller.joystick);
                    break;
                }

                case "select":
                {
                    Controller.ButtonID id = Controller.ButtonID.SELECT;
                    controller.select = new Controller.Button(id, mode, controller.joystick);
                    break;
                }

                case "leftBumper":
                {
                    Controller.ButtonID id = Controller.ButtonID.LEFT_BUMPER;
                    controller.leftBumper = new Controller.Button(id, mode, controller.joystick);
                    break;
                }

                case "rightBumper":
                {
                    Controller.ButtonID id = Controller.ButtonID.RIGHT_BUMPER;
                    controller.rightBumper = new Controller.Button(id, mode, controller.joystick);
                    break;
                }

                case "leftJoy":
                {
                    Controller.ButtonID id = Controller.ButtonID.LEFT_JOY;
                    controller.leftJoy = new Controller.Button(id, mode, controller.joystick);
                    break;
                }

                case "rightJoy":
                {
                    Controller.ButtonID id = Controller.ButtonID.RIGHT_JOY;
                    controller.rightJoy = new Controller.Button(id, mode, controller.joystick);
                    break;
                }

                default:
                {
                    System.err.println("Invalid button id, not initializing button");
                    break;
                }
            }
        }

        for (Element e : axisElements) {
            String axis;
            try {
                axis = e.getAttributeValue("id");

            } catch (NullPointerException ex) {
                System.err.println("Axis id was not defined, button will not be initialized");
                continue;
            }

            Controller.AxisMode mode = null;
            try {
                if (e.getAttributeValue("mode").equals("inverted")) {
                    mode = Controller.AxisMode.Inverted;
                } else {
                    if (!e.getAttributeValue("mode").equals("raw"))
                        System.err.println("Invalid axis mode, setting mode to raw");
                    mode = Controller.AxisMode.Inverted;
                }
            } catch (NullPointerException ex) {
                System.err.println("Axis mode not defined, setting to raw");
                mode = Controller.AxisMode.Raw;
            }

            switch (axis) {
                case "leftX": {
                    Controller.AxisID id = Controller.AxisID.LEFT_X;
                    controller.leftX = new Controller.Axis(id, mode, controller.joystick);
                    break;
                }

                case "leftY": {
                    Controller.AxisID id = Controller.AxisID.LEFT_Y;
                    controller.leftY = new Controller.Axis(id, mode, controller.joystick);
                    break;
                }

                case "rightX": {
                    Controller.AxisID id = Controller.AxisID.RIGHT_X;
                    controller.rightX = new Controller.Axis(id, mode, controller.joystick);
                    break;
                }

                case "rightY": {
                    Controller.AxisID id = Controller.AxisID.RIGHT_Y;
                    controller.rightY = new Controller.Axis(id, mode, controller.joystick);
                    break;
                }

                case "rightTrigger": {
                    Controller.AxisID id = Controller.AxisID.RIGHT_TRIGGER;
                    controller.rightTrigger = new Controller.Axis(id, mode, controller.joystick);
                    break;
                }

                case "leftTrigger": {
                    Controller.AxisID id = Controller.AxisID.LEFT_TRIGGER;
                    controller.leftTrigger = new Controller.Axis(id, mode, controller.joystick);
                    break;
                }

                default: {
                    System.err.println("Invalid axis id, not initializing button");
                    continue;
                }
            }

        }
        return controller;
    }

    private Element getElement(Element baseElement, String name) throws ElementNotFoundException
    {
        List<Element> elements = baseElement.getChildren();
        for (Element e : elements)
            if (e.getAttributeValue("name") == name) return e;
        throw new ElementNotFoundException();
    }
}

class ElementNotFoundException extends Exception { }

