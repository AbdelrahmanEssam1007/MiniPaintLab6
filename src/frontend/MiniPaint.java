package frontend;
import backend.Circle;
import backend.DrawingEngineImplementation;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class MiniPaint extends javax.swing.JFrame {

    private DrawingEngineImplementation engine;
    private DrawingCanvas canvas;

    public MiniPaint() {
        this.engine = new DrawingEngineImplementation();
        this.canvas = new DrawingCanvas(engine);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        initComponents();
        setTitle("Vector Drawing Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        canvas.setBackground(Color.white);
        circleButton.addActionListener(e -> addCircle());
    }

    private void addCircle() {
        try {
            int radius = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Enter the radius of the circle"));
            Map<String, Double> properties = new HashMap<>();
            Circle c = new Circle();
            c.setColor(Color.black);
            c.setFillColor(Color.white);

            String input = javax.swing.JOptionPane.showInputDialog("Enter the x and y position of the circle (x,y)");
            String[] coordinates = input.split(",");
            int x = Integer.parseInt(coordinates[0].trim()); // trim() to remove any extra spaces
            int y = Integer.parseInt(coordinates[1].trim());
            c.setPosition(new Point(x, y));

            properties.put("radius", (double) radius);
            c.setProperties(properties);
            engine.addShape(c); // Add circle to the engine

            // Update ShapesBox to show new shape
            SwingUtilities.invokeLater(() -> {
                ShapesBox.addItem("Circle " + engine.getShapes().length); // Add the shape to the combo box
            });

            // Repaint the canvas to show the new shape
            canvas.repaint();
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    private void initComponents() {

        circleButton = new javax.swing.JButton();
        LineSegmentButton = new javax.swing.JButton();
        squareButton = new javax.swing.JButton();
        rectangleButton = new javax.swing.JButton();
        coloriseButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        ShapesBox = new javax.swing.JComboBox<>();
        canvas = new DrawingCanvas(engine);  // Use DrawingCanvas for custom drawing

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        circleButton.setText("Circle");

        LineSegmentButton.setText("LineSegment");

        squareButton.setText("Square");

        rectangleButton.setText("Rectangle");

        coloriseButton.setText("Colorise");

        deleteButton.setText("Delete");

        ShapesBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ShapesBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShapesBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(coloriseButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(deleteButton))
                                        .addComponent(ShapesBox, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(circleButton)
                                                .addGap(130, 130, 130)
                                                .addComponent(LineSegmentButton)
                                                .addGap(130, 130, 130)
                                                .addComponent(squareButton)
                                                .addGap(132, 132, 132)
                                                .addComponent(rectangleButton))
                                        .addComponent(canvas, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(circleButton)
                                        .addComponent(LineSegmentButton)
                                        .addComponent(squareButton)
                                        .addComponent(rectangleButton))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(ShapesBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(coloriseButton)
                                                        .addComponent(deleteButton))
                                                .addGap(91, 91, 91))
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(canvas, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())))
        );

        pack();
    }

    private void ShapesBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MiniPaint().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton LineSegmentButton;
    private javax.swing.JComboBox<String> ShapesBox;
    private javax.swing.JButton circleButton;
    private javax.swing.JButton coloriseButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton rectangleButton;
    private javax.swing.JButton squareButton;
    // End of variables declaration
}
