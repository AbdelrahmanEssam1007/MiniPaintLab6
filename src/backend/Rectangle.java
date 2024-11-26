package backend;

import java.awt.*;

public class Rectangle extends AbsShape {
  public Rectangle() {
    super();
    getProperties().put("width", 0.0);
    getProperties().put("height", 0.0);
  }

  @Override
  public void draw(Graphics canvas) {
    canvas.setColor(getFillColor());
    canvas.fillRect(getPosition().x, getPosition().y, getProperties().get("width").intValue(), getProperties().get("height").intValue());
    canvas.setColor(getColor());
    canvas.drawRect(getPosition().x, getPosition().y, getProperties().get("width").intValue(), getProperties().get("height").intValue());
  }

  @Override
  public String stringRep() {
    return "Rectangle," + getProperties().get("width") + "," + getProperties().get("height") + "," + getPosition().x + "," + getPosition().y + "," + getColor().getRGB() + "," + getFillColor().getRGB();
  }
}
