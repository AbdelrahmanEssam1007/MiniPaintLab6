package backend;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DrawingEngineImplementation implements DrawingEngine {

  private final List <Shape> shapes;
  private static int numOfCircles = 0;
  private static int numOfRectangles = 0;
  private static int numOfSquares = 0;
  private static int numOfLineSegments = 0;

  public DrawingEngineImplementation() {
    this.shapes = new ArrayList<>();
  }

  @Override
  public void addShape (Shape shape) {
//        if (shape.getName() == null) {
    if (shape instanceof Circle) {
      DrawingEngineImplementation.numOfCircles ++;
      shape.setName("Circle " + DrawingEngineImplementation.numOfCircles);
    }
    else if (shape instanceof Rectangle) {
      DrawingEngineImplementation.numOfRectangles ++;
      shape.setName("Rectangle " + DrawingEngineImplementation.numOfRectangles);
    }
    else if (shape instanceof Square) {
      DrawingEngineImplementation.numOfSquares ++;
      shape.setName("Square " + DrawingEngineImplementation.numOfSquares);
    }
    else {
      DrawingEngineImplementation.numOfLineSegments ++;
      shape.setName("Line " + DrawingEngineImplementation.numOfLineSegments);
    }
//        }
    this.shapes.add(shape);
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

  public void reset() {
    shapes.clear();
    numOfCircles = 0;
    numOfRectangles = 0;
    numOfSquares = 0;
    numOfLineSegments = 0;
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
