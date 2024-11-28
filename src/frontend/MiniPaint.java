package frontend;

import backend.*;
import backend.Rectangle;
import backend.Shape;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * @author Abdelrahman Essam
 */
public class MiniPaint extends JFrame {

  private final Color transparent = new Color(0, 0, 0, 0);
  private final DrawingEngineImplementation engine = new DrawingEngineImplementation();

  public MiniPaint() {
    //initialisation and frame settings
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    setTitle("Vector Drawing Application");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    initComponents();
    canvas.setBackground(Color.WHITE);
    updateShapeBox();

    // Action listeners for the buttons
    circleButton.addActionListener(e -> addCircle());
    LineSegmentButton.addActionListener(e -> addLineSegment());
    squareButton.addActionListener(e -> addSquare());
    rectangleButton.addActionListener(e -> addRectangle());
    deleteButton.addActionListener(e -> deleteShape());
    coloriseButton.addActionListener(e -> coloriseShape());
    saveButton.addActionListener(e -> write());
    loadButton.addActionListener(e -> read());
    moveButton.addActionListener(e -> move());
    resizeButton.addActionListener(e -> resize());
    canvas.repaint();
  }

  private void resize() {
    try {
      int index = ShapesBox.getSelectedIndex();
      String input;
      double size;
      Shape selectedShape = engine.getShapes()[index];
      switch (selectedShape.getClass().getSimpleName()) {
        case "Circle":
        {
          input = JOptionPane.showInputDialog("Enter the new radius of the circle");
          size = Double.parseDouble(input);
          if (size <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid input");
            return;
          }
          selectedShape.setProperties(Map.of("radius", size));
          break;
        }
        case "Square":
        {
          input = JOptionPane.showInputDialog("Enter the new side of the square");
          size = Double.parseDouble(input);
          if (size <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid input");
            return;
          }
          selectedShape.setProperties(Map.of("side", size));
          break;
        }
        case "Rectangle":
        {
          input = JOptionPane.showInputDialog("Enter the new width of the rectangle");
          size = Double.parseDouble(input);
          if (size <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid input");
            return;
          }
          String input2 = JOptionPane.showInputDialog("Enter the new height of the rectangle");
          double size2 = Double.parseDouble(input2);
          if (size2 <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid input");
            return;
          }
          selectedShape.setProperties(Map.of("width", size, "height", size2));
          break;
        }
        case "LineSegment":
        {
          JOptionPane.showMessageDialog(this, "Line segments cannot be resized");
          return;
        }
      }
      canvas.repaint();
      engine.refresh(canvas.getGraphics());
    } catch (ArrayIndexOutOfBoundsException e) {
      JOptionPane.showMessageDialog(this, "No shape selected");
    }
  }

  private void move() {
    try {
      int index = ShapesBox.getSelectedIndex();
      Shape selectedShape = engine.getShapes()[index];
      if (selectedShape.getClass().getSimpleName().equals("LineSegment")) {
        String Linepos1 = JOptionPane.showInputDialog("Enter the x1 and y1 position of the line segment (x1,y1)");
        String Linepos2 = JOptionPane.showInputDialog("Enter the x2 and y2 position of the line segment (x2,y2)");
        String[] coordinates1 = Linepos1.split(",");
        String[] coordinates2 = Linepos2.split(",");
        selectedShape.setPosition(Integer.parseInt(coordinates1[0].trim()), Integer.parseInt(coordinates1[1].trim()));
        selectedShape.setProperties(Map.of("x2", Double.parseDouble(coordinates2[0].trim()), "y2", Double.parseDouble(coordinates2[1].trim())));
        canvas.repaint();
        return;
      }
      String input = JOptionPane.showInputDialog("Enter the x and y position of the shape (x,y)");
      String[] coordinates = input.split(",");
      int x = Integer.parseInt(coordinates[0].trim());
      int y = Integer.parseInt(coordinates[1].trim());
      selectedShape.setPosition(x, y);
      canvas.repaint();
      engine.refresh(canvas.getGraphics());

    } catch (ArrayIndexOutOfBoundsException e) {
      JOptionPane.showMessageDialog(this, "No shape selected");
    }

  }

  private void updateShapeBox() {
    if (ShapesBox.getItemCount() == 0) {
      ShapesBox.addItem("Choose Shape");
    } else if (ShapesBox.getItemAt(0).equals("Choose Shape") && ShapesBox.getItemCount() > 1) {
      ShapesBox.removeItemAt(0);
    }
  }

  private void write() {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showSaveDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      String path = fileChooser.getSelectedFile().getAbsolutePath();
      engine.saveToFile(path);
    }
  }

  private void read() {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      engine.reset();
      ShapesBox.removeAllItems();
      canvas.repaint();
      String path = fileChooser.getSelectedFile().getAbsolutePath();
      engine.readFromFile(path);
      for (Shape s : engine.getShapes()) {
        ShapesBox.addItem(s.getName());
      }
      updateShapeBox();
      engine.refresh(canvas.getGraphics());
    }
  }

  private void addCircle() {
    try {
      int radius = Integer.parseInt(JOptionPane.showInputDialog("Enter the radius of the circle").trim());
      if (radius <= 0) {
        JOptionPane.showMessageDialog(this, "Invalid input");
        return;
      }
      Map<String, Double> properties = new HashMap<>();
      Circle c = new Circle();
      c.setColor(Color.BLACK);
      c.setFillColor(transparent);

      String input = JOptionPane.showInputDialog("Enter the x and y position of the circle (x,y)");
      String[] coordinates = input.split(",");
      int x = Integer.parseInt(coordinates[0].trim());
      int y = Integer.parseInt(coordinates[1].trim());
      c.setPosition(new Point(x, y));

      properties.put("radius", (double) radius);
      c.setProperties(properties);
      engine.addShape(c);


      SwingUtilities.invokeLater(() -> {
        ShapesBox.addItem(c.getName());
        updateShapeBox();
      });
      engine.refresh(canvas.getGraphics());
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid input");
    }
  }

  private void addLineSegment() {
    try {
      String Linepos1 = JOptionPane.showInputDialog("Enter the x1 and y1 position of the line segment (x1,y1)");
      String Linepos2 = JOptionPane.showInputDialog("Enter the x2 and y2 position of the line segment (x2,y2)");
      String[] coordinates1 = Linepos1.split(",");
      String[] coordinates2 = Linepos2.split(",");
      LineSegment l = new LineSegment();
      l.setColor(Color.BLACK);
      l.setPosition(Integer.parseInt(coordinates1[0].trim()), Integer.parseInt(coordinates1[1].trim()));
      l.setProperties(Map.of("x2", Double.parseDouble(coordinates2[0].trim()), "y2", Double.parseDouble(coordinates2[1].trim())));
      engine.addShape(l);

      SwingUtilities.invokeLater(() -> {
        ShapesBox.addItem(l.getName());
        updateShapeBox();
      });
      engine.refresh(canvas.getGraphics());
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid input");
    }
  }

  private void addSquare() {
    try {
      int side = Integer.parseInt(JOptionPane.showInputDialog("Enter the side of the square").trim());
      if (side <= 0) {
        JOptionPane.showMessageDialog(this, "Invalid input");
        return;
      }
      Map<String, Double> properties = new HashMap<>();
      Square s = new Square();
      s.setColor(Color.BLACK);
      s.setFillColor(transparent);

      String input = JOptionPane.showInputDialog("Enter the x and y position of the square (x,y)");
      String[] coordinates = input.split(",");
      int x = Integer.parseInt(coordinates[0].trim());
      int y = Integer.parseInt(coordinates[1].trim());
      s.setPosition(new Point(x, y));

      properties.put("side", (double) side);
      s.setProperties(properties);
      engine.addShape(s);
      SwingUtilities.invokeLater(() -> {
        ShapesBox.addItem(s.getName());
        updateShapeBox();
      });
      engine.refresh(canvas.getGraphics());
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid input");
    }
  }

  private void addRectangle() {
    try {
      int width = Integer.parseInt(JOptionPane.showInputDialog("Enter the width of the rectangle").trim());
      if (width <= 0) {
        JOptionPane.showMessageDialog(this, "Invalid input");
        return;
      }
      int height = Integer.parseInt(JOptionPane.showInputDialog("Enter the height of the rectangle").trim());
      if (height <= 0) {
        JOptionPane.showMessageDialog(this, "Invalid input");
        return;
      }
      Map<String, Double> properties = new HashMap<>();
      Rectangle r = new Rectangle();
      r.setColor(Color.BLACK);
      r.setFillColor(transparent);

      String input = JOptionPane.showInputDialog("Enter the x and y position of the rectangle (x,y)");
      String[] coordinates = input.split(",");
      int x = Integer.parseInt(coordinates[0].trim());
      int y = Integer.parseInt(coordinates[1].trim());
      r.setPosition(new Point(x, y));

      properties.put("width", (double) width);
      properties.put("height", (double) height);
      r.setProperties(properties);
      engine.addShape(r);

      SwingUtilities.invokeLater(() -> {
        ShapesBox.addItem(r.getName());
        updateShapeBox();
      });
      engine.refresh(canvas.getGraphics());
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid input");
    }
  }

  private void deleteShape() {
    try {
      int index = ShapesBox.getSelectedIndex();
      engine.removeShape(engine.getShapes()[index]);
      ShapesBox.removeItemAt(index);
      canvas.repaint();
      engine.refresh(canvas.getGraphics());
      SwingUtilities.invokeLater(() -> {
        updateShapeBox();
      });
    } catch (ArrayIndexOutOfBoundsException e) {
      JOptionPane.showMessageDialog(this, "No shape selected");
    }
  }

  private void coloriseShape() {
    try {
      int index = ShapesBox.getSelectedIndex();
      Color color = JColorChooser.showDialog(this, "Choose a color", Color.BLACK);
      if (color != null) {
        engine.getShapes()[index].setColor(color);
      }
      Color fillcolor = JColorChooser.showDialog(this, "Choose a fill color", Color.BLACK);
      if (fillcolor != null) {
        engine.getShapes()[index].setFillColor(fillcolor);
      }
      canvas.repaint();
      engine.refresh(canvas.getGraphics());
    } catch (ArrayIndexOutOfBoundsException e) {
      JOptionPane.showMessageDialog(this, "No shape selected");
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
    label1 = new java.awt.Label();
    resizeButton = new javax.swing.JButton();
    moveButton = new javax.swing.JButton();
    saveButton = new javax.swing.JButton();
    loadButton = new javax.swing.JButton();
    canvas = new DrawingCanvas(engine);

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

    resizeButton.setText("Resize");

    moveButton.setText("Move");

    saveButton.setText("Save");

    loadButton.setText("Load");

    canvas.setBackground(new java.awt.Color(255, 255, 255));

    javax.swing.GroupLayout canvasLayout = new javax.swing.GroupLayout(canvas);
    canvas.setLayout(canvasLayout);
    canvasLayout.setHorizontalGroup(canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
    canvasLayout.setVerticalGroup(canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 335, Short.MAX_VALUE));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(21, 21, 21).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(coloriseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addComponent(ShapesBox, 0, 170, Short.MAX_VALUE).addComponent(label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(resizeButton).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(moveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(saveButton).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(loadButton))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(circleButton).addGap(130, 130, 130).addComponent(LineSegmentButton).addGap(130, 130, 130).addComponent(squareButton).addGap(132, 132, 132).addComponent(rectangleButton)).addComponent(canvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));
    layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(circleButton).addComponent(LineSegmentButton).addComponent(squareButton).addComponent(rectangleButton)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(canvas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()).addGroup(layout.createSequentialGroup().addGap(67, 67, 67).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(saveButton).addComponent(loadButton)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2, 2, 2).addComponent(ShapesBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(coloriseButton).addComponent(deleteButton)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(resizeButton).addComponent(moveButton)).addGap(58, 58, 58)));

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
      for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(MiniPaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(MiniPaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(MiniPaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(MiniPaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        new MiniPaint().setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton LineSegmentButton;
  private javax.swing.JComboBox<String> ShapesBox;
  private DrawingCanvas canvas;
  private javax.swing.JButton circleButton;
  private javax.swing.JButton coloriseButton;
  private javax.swing.JButton deleteButton;
  private java.awt.Label label1;
  private javax.swing.JButton loadButton;
  private javax.swing.JButton moveButton;
  private javax.swing.JButton rectangleButton;
  private javax.swing.JButton resizeButton;
  private javax.swing.JButton saveButton;
  private javax.swing.JButton squareButton;
  // End of variables declaration//GEN-END:variables
}
