package backend;

import java.awt.*;

public class Square extends AbsShape {
  public Square() {
    super();
    getProperties().put("side", 0.0);
  }

  @Override
  public void draw(Graphics canvas) {
    canvas.setColor(getFillColor());
    canvas.fillRect(getPosition().x, getPosition().y, getProperties().get("side").intValue(), getProperties().get("side").intValue());
    canvas.setColor(getColor());
    canvas.drawRect(getPosition().x, getPosition().y, getProperties().get("side").intValue(), getProperties().get("side").intValue());
  }
}
