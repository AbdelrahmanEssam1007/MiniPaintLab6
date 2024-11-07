package backend;

import java.awt.*;
import java.util.*;

public abstract class AbsShape implements Shape {
    private Point position;
    private Color color;
    private Color fillColor;
    private Map<String, Double> properties;

    public AbsShape() {
        this.position = new Point(0, 0);
        this.color = Color.BLACK;
        this.fillColor = Color.WHITE;
        this.properties = new HashMap<String, Double>();
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        this.properties = properties;
    }

    @Override
    public Map<String, Double> getProperties() {
        return properties;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }
}
