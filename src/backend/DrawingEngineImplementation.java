package backend;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingEngineImplementation implements DrawingEngine{

  private List<Shape> shapes;

  public DrawingEngineImplementation() {
    this.shapes = new ArrayList<>();
  }

  @Override
  public void addShape(Shape shape) {
    shapes.add(shape);
  }

  @Override
  public void removeShape(Shape shape) {
    shapes.remove(shape);
  }

  @Override
  public Shape[] getShapes() {
    return shapes.toArray(new Shape[0]);
  }

  @Override
  public void refresh(Graphics canvas) {
    for (Shape shape : shapes) {
      shape.draw(canvas);
    }
  }

}
