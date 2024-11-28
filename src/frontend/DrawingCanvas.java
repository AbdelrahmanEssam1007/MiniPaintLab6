package frontend;
import backend.DrawingEngineImplementation;
import javax.swing.*;
import java.awt.*;

public class DrawingCanvas extends JPanel {
    private final DrawingEngineImplementation engine;

    public DrawingCanvas(DrawingEngineImplementation engine) {
        this.engine = engine;
        this.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        engine.refresh(g);
    }
}
