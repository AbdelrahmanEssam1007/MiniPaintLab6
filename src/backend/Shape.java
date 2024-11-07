package backend;

import java.util.*;
import java.awt.*;

public interface Shape {
    // set position
    public void setPosition(Point position);
    public Point getPosition();
    // update shape specific properties
    public void setProperties(Map<String, Double> properties);
    public Map<String, Double> getProperties();
    // colorise
    public void setColor(Color color);
    public Color getColor();
    public void setFillColor(Color color);
    public Color getFillColor();
    // redraw the shape on the canvas
    public void draw(Graphics canvas);
}
