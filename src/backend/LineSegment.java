package backend;

import java.awt.*;

public class LineSegment extends AbsShape {
  public LineSegment() {
    super();
    getProperties().put("x2", 0.0);
    getProperties().put("y2", 0.0);
  }

  @Override
  public void draw(Graphics canvas) {
    canvas.setColor(getColor());
    canvas.drawLine(getPosition().x, getPosition().y, getProperties().get("x2").intValue(), getProperties().get("y2").intValue());
  }

  @Override
  public String stringRep() {
    return "LineSegment," + getProperties().get("x2") + "," + getProperties().get("y2") + "," + getPosition().x + "," + getPosition().y + "," + getColor().getRGB();
  }
}
