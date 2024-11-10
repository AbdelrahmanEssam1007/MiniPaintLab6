package backend;

import java.awt.*;

public class Circle extends AbsShape {
  public Circle() {
    super();
    getProperties().put("radius", 0.0);
  }

  @Override
  public void draw(Graphics canvas) {
    canvas.setColor(getFillColor());
    canvas.fillOval(getPosition().x, getPosition().y, getProperties().get("radius").intValue()*2, getProperties().get("radius").intValue()*2);
    canvas.setColor(getColor());
    canvas.drawOval(getPosition().x, getPosition().y, getProperties().get("radius").intValue()*2, getProperties().get("radius").intValue()*2);
  }
}
