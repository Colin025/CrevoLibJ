package com.team2851.util.configuration;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.HLUsageReporting;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HardwareMap
{
    private SAXBuilder saxBuilder = new SAXBuilder();
    private Document document;

    public HardwareMap(String filePath)
    {
        File file = new File(filePath);
        try {
            document = saxBuilder.build(file);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private Element getElement(Element baseElement, String name) throws ElementNotFoundException
    {
        List<Element> elements = baseElement.getChildren();
        for (Element e : elements)
            if (e.getAttributeValue("name") == name) return e;
        throw new ElementNotFoundException();
    }
}

class ElementNotFoundException extends Exception { }

