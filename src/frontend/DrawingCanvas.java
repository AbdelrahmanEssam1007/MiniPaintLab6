package frontend;

import backend.DrawingEngineImplementation;

import java.awt.*;

public class DrawingCanvas extends Canvas {
    private DrawingEngineImplementation engine;

    public DrawingCanvas(DrawingEngineImplementation engine) {
        this.engine = engine;
        setSize(800, 480);  // Set canvas size
    }

    @Override
    public void paint(Graphics g) {
        // Call the DrawingEngine to refresh and draw all shapes
        engine.refresh(g);
    }
}
