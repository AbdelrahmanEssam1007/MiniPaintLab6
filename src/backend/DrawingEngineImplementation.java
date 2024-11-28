package backend;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DrawingEngineImplementation implements DrawingEngine {

  private final List <Shape> shapes;
  private static int CircleNum = 0;
  private static int RectNum = 0;
  private static int SquareNum = 0;
  private static int LineSegNum = 0;

  public DrawingEngineImplementation() {
    this.shapes = new ArrayList<>();
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
  @Override
  public void addShape (Shape shape) {
    if (shape instanceof Circle) {
      DrawingEngineImplementation.CircleNum++;
      shape.setName("Circle " + DrawingEngineImplementation.CircleNum);
    }
    else if (shape instanceof Rectangle) {
      DrawingEngineImplementation.RectNum++;
      shape.setName("Rectangle " + DrawingEngineImplementation.RectNum);
    }
    else if (shape instanceof Square) {
      DrawingEngineImplementation.SquareNum++;

      shape.setName("Square " + DrawingEngineImplementation.SquareNum);
    }
    else {
      DrawingEngineImplementation.LineSegNum++;
      shape.setName("Line " + DrawingEngineImplementation.LineSegNum);
    }
    this.shapes.add(shape);
  }

  public void reset() {
    shapes.clear();
    CircleNum = 0;
    RectNum = 0;
    SquareNum = 0;
    LineSegNum = 0;
  }

  public void saveToFile (String path) {
    try {
      FileWriter fileWriter = new FileWriter (path);
      for (Shape shape : shapes) {
        fileWriter.write(shape.toString());
      }
      fileWriter.close();
    }
    catch (IOException e) {

    }
  }

  public void readFromFile(String path) {
    try {
      File file = new File (path);
      Scanner sc = new Scanner(file);
      String line;

      while (sc.hasNextLine()) {
        line = sc.nextLine();
        Shape shape;
        if (line.contains("Circle")) {
          shape = new Circle();
        }
        else if (line.contains("Rectangle")) {
          shape = new Rectangle();
        }
        else if (line.contains("Square")) {
          shape = new Square();
        }
        else {
          shape = new LineSegment();
        }
        shape.setShape(line);
        this.addShape(shape);
      }
      sc.close();
    }
    catch (FileNotFoundException e) {

    }
  }
}
