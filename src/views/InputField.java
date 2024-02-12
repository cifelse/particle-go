package views;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class InputField extends JTextField {
    private Shape shape;

    public InputField(int size) {
        super(size);
        setOpaque(false); // As suggested by @AVD in comment.
        setHorizontalAlignment(JTextField.CENTER);
        setText("0");
    }

    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
    }
    
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        }
        return shape.contains(x, y);
    }
}