package frontend;
import backend.*;
import backend.Rectangle;
import backend.Shape;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 *
 * @author Ryzen 2700
 */
public class MiniPaint extends javax.swing.JFrame {

    private final Color transparent = new Color(0,0,0,0);
    private DrawingEngineImplementation engine;
    private Map<String,Integer> shapeCounter = new HashMap<>();

   public MiniPaint() {
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    initComponents();
    setTitle("Vector Drawing Application");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    this.engine = new DrawingEngineImplementation();

    // Set background color for the canvas
    canvas.setBackground(Color.WHITE);

    // Add action listener for the Circle button
    circleButton.addActionListener(e -> addCircle());
    LineSegmentButton.addActionListener(e -> addLineSegment());
    squareButton.addActionListener(e -> addSquare());
    rectangleButton.addActionListener(e -> addRectangle());
    deleteButton.addActionListener(e -> deleteShape());
    coloriseButton.addActionListener(e -> coloriseShape());
}

    // Method to add circle shape to the engine
    private void addCircle() {
        try {
            int radius = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Enter the radius of the circle"));
            Map<String, Double> properties = new HashMap<>();
            Circle c = new Circle();
            c.setColor(Color.BLACK);
            c.setFillColor(transparent);

            String input = javax.swing.JOptionPane.showInputDialog("Enter the x and y position of the circle (x,y)");
            String[] coordinates = input.split(",");
            int x = Integer.parseInt(coordinates[0].trim());
            int y = Integer.parseInt(coordinates[1].trim());
            c.setPosition(new Point(x, y));

            properties.put("radius", (double) radius);
            c.setProperties(properties);
            engine.addShape(c);

            int count = shapeCounter.getOrDefault("Circle", 0) +1;
            shapeCounter.put("Circle", count);
            SwingUtilities.invokeLater(() -> {
                ShapesBox.addItem("Circle " + count);
            });
            engine.refresh(canvas.getGraphics());
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    private void addLineSegment(){
        try {
            int x = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Enter the x1 of the line segment").trim());
            int y = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Enter the y1 of the line segment").trim());
            int x2 = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Enter the x2 of the line segment").trim());
            int y2 = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Enter the y2 of the line segment").trim());
            Map<String, Double> properties = new HashMap<>();
            LineSegment l = new LineSegment();
            l.setColor(Color.BLACK);
            l.setPosition(new Point(x, y));

            properties.put("x2", (double) x2);
            properties.put("y2", (double) y2);
            l.setProperties(properties);
            engine.addShape(l);

            int count = shapeCounter.getOrDefault("LineSegment", 0) +1;
            shapeCounter.put("LineSegment", count);

            SwingUtilities.invokeLater(() -> {
                ShapesBox.addItem("Line Segment " + count);
            });
            engine.refresh(canvas.getGraphics());
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    private void addSquare() {
        try {
            int side = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Enter the side of the square"));
            Map<String, Double> properties = new HashMap<>();
            Square s = new Square();
            s.setColor(Color.BLACK);
            s.setFillColor(transparent);

            String input = javax.swing.JOptionPane.showInputDialog("Enter the x and y position of the square (x,y)");
            String[] coordinates = input.split(",");
            int x = Integer.parseInt(coordinates[0].trim());
            int y = Integer.parseInt(coordinates[1].trim());
            s.setPosition(new Point(x, y));

            properties.put("side", (double) side);
            s.setProperties(properties);
            engine.addShape(s);

            int count = shapeCounter.getOrDefault("Square", 0) +1;
            shapeCounter.put("Square", count);

            SwingUtilities.invokeLater(() -> {
                ShapesBox.addItem("Square " + count);
            });
            engine.refresh(canvas.getGraphics());
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    private void addRectangle() {
        try {
            int width = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Enter the width of the rectangle"));
            int height = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Enter the height of the rectangle"));
            Map<String, Double> properties = new HashMap<>();
            Rectangle r = new Rectangle();
            r.setColor(Color.BLACK);
            r.setFillColor(transparent);

            String input = javax.swing.JOptionPane.showInputDialog("Enter the x and y position of the rectangle (x,y)");
            String[] coordinates = input.split(",");
            int x = Integer.parseInt(coordinates[0].trim());
            int y = Integer.parseInt(coordinates[1].trim());
            r.setPosition(new Point(x, y));

            properties.put("width", (double) width);
            properties.put("height", (double) height);
            r.setProperties(properties);
            engine.addShape(r);

            int count = shapeCounter.getOrDefault("Rectangle", 0) +1;
            shapeCounter.put("Rectangle", count);

            SwingUtilities.invokeLater(() -> {
                ShapesBox.addItem("Rectangle " + count);
            });
            engine.refresh(canvas.getGraphics());
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    private void deleteShape() {
        try {
            int index = ShapesBox.getSelectedIndex();
            engine.removeShape(engine.getShapes()[index]);
            ShapesBox.removeItemAt(index);
            canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            engine.refresh(canvas.getGraphics());
        } catch (ArrayIndexOutOfBoundsException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "No shape selected");
        }
    }

    private void coloriseShape() {
        try {
            int index = ShapesBox.getSelectedIndex();
            Color color = JColorChooser.showDialog(this, "Choose a color", Color.BLACK);
            engine.getShapes()[index].setColor(color);
            Color fillcolor = JColorChooser.showDialog(this, "Choose a fill color", Color.BLACK);
            engine.getShapes()[index].setColor(color);
            engine.getShapes()[index].setFillColor(fillcolor);
            canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            engine.refresh(canvas.getGraphics());
        } catch (ArrayIndexOutOfBoundsException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "No shape selected");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        circleButton = new javax.swing.JButton();
        LineSegmentButton = new javax.swing.JButton();
        squareButton = new javax.swing.JButton();
        rectangleButton = new javax.swing.JButton();
        coloriseButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        ShapesBox = new javax.swing.JComboBox<>();
        canvas = new java.awt.Canvas();
        label1 = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        circleButton.setText("Circle");
        circleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                circleButtonActionPerformed(evt);
            }
        });

        LineSegmentButton.setText("LineSegment");

        squareButton.setText("Square");

        rectangleButton.setText("Rectangle");

        coloriseButton.setText("Colorise");

        deleteButton.setText("Delete");

        ShapesBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShapesBoxActionPerformed(evt);
            }
        });

        label1.setText("Choose Shapes");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(coloriseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(ShapesBox, 0, 170, Short.MAX_VALUE)
                    .addComponent(label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
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
    }// </editor-fold>//GEN-END:initComponents

    private void circleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_circleButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_circleButtonActionPerformed

    private void ShapesBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShapesBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ShapesBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MiniPaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MiniPaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MiniPaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MiniPaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MiniPaint().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LineSegmentButton;
    private javax.swing.JComboBox<String> ShapesBox;
    private java.awt.Canvas canvas;
    private javax.swing.JButton circleButton;
    private javax.swing.JButton coloriseButton;
    private javax.swing.JButton deleteButton;
    private java.awt.Label label1;
    private javax.swing.JButton rectangleButton;
    private javax.swing.JButton squareButton;
    // End of variables declaration//GEN-END:variables
}
