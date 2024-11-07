package backend;

import java.awt.*;

public interface DrawingEngine {
    // manage shapes
    public void addShape(Shape shape);
    public void removeShape(Shape shape);
    // return the created shapes
    public Shape[] getShapes();
    // redraw all shapes on the canvas
    public void refresh(Graphics canvas);
}
