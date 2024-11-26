package backend;

import java.awt.*;
import java.util.*;

public abstract class AbsShape implements Shape {
  private Point position;
  private Color color;
  private Color fillColor;
  private Map<String, Double> properties;
  private String name;

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
  public void setPosition(double x, double y) {
    this.position = new Point((int) x, (int) y);
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

  @Override
  public void setName(String name) {
    this.name = name;
  }
  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String toString () {
    StringBuilder s = new StringBuilder();
    s.append (this.getName());
    s.append (",");
    s.append (this.getColor().getRGB());
    s.append (",");
    s.append (this.getFillColor().getRGB());
    s.append (",");
    s.append (this.getPosition().getX());
    s.append (",");
    s.append (this.getPosition().getY());
    s.append (",");
    this.getProperties().forEach((key, value) -> {
      s.append (key);
      s.append (":");
      s.append (value);
      s.append (",");
    });
    s.append("\n");
    return s.toString();
  }

  @Override
  public void setShape(String s) {
    String [] shapeDetails = s.split(",", 0);
    this.setName(shapeDetails[0]);
    this.setColor(new Color(Integer.parseInt(shapeDetails[1])));
    this.setFillColor(new Color(Integer.parseInt(shapeDetails[2])));
    this.setPosition(Double.parseDouble(shapeDetails[3]), Double.parseDouble(shapeDetails[4]));
    for (int i = 5; i < shapeDetails.length; i ++) {
      String [] property = shapeDetails[i].split(":", 0);
      this.getProperties().put(property [0], Double.parseDouble(property [1]));
    }
  }
}
