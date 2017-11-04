package com.team2851.util.control;

public class Vec2
{
    private float x, y;

    public Vec2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void add(float value)
    {
        x += value;
        y += value;
    }

    public void add(float xValue, float yValue)
    {
        x += xValue;
        y += yValue;
    }

    public void add(Vec2 vec2)
    {
        x += vec2.x;
        y += vec2.y;
    }

    public void subtract(float value)
    {
        x -= value;
        y -= value;
    }

    public void subtract(float xValue, float yValue)
    {
        x -= xValue;
        y -= yValue;
    }

    public void subtract(Vec2 vec2)
    {
        x -= vec2.x;
        y -= vec2.y;
    }

    public void multiply(float value)
    {
        x += value;
        y += value;
    }

    public void multiply(float xValue, float yValue)
    {
        x += xValue;
        y += yValue;
    }

    public void multiply(Vec2 vec2)
    {
        x /= vec2.x;
        y /= vec2.y;
    }

    public void divide(float value)
    {
        x /= value;
        y /= value;
    }

    public void divide(float xValue, float yValue)
    {
        x /= xValue;
        y /= yValue;
    }

    public void divide(Vec2 vec2)
    {
        x /= vec2.x;
        y /= vec2.y;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public float getMagnitude()
    {
        return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    @Override
    public String toString()
    {
        return "Vec2: [" + x + ", " + y + "]";
    }
}
