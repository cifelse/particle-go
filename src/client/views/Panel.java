package client.views;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.Shape;
import java.awt.Graphics;
import java.awt.geom.RoundRectangle2D;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

/**
 * The Panel class is a JPanel that is used to create a panel with a title.
 */
public abstract class Panel extends JPanel {
    /**
     * The Panel constructor is used to create a new Panel with no title.
     */
    public Panel() {
        setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    /**
     * The Panel constructor is used to create a new Panel with no title.
     * @param mgr - The layout manager to be used
     */
    public Panel(LayoutManager mgr) {
        setLayout(mgr);
        setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    /**
     * The Panel constructor is used to create a new Panel with a title.
     * @param mgr - The layout manager to be used
     * @param panelTitle - The title of the panel
     */
    public Panel(String panelTitle, LayoutManager mgr) {
        setLayout(mgr);
        setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 1), panelTitle));
    }

    /**
     * The addInputBar method is used to add an input bar to the panel.
     * @param label - The label to be added to the input bar
     * @param size - The size of the input bar
     */
    public InputField addInputBar(String label, int size) {
        add(new JLabel(label + ": ", JLabel.CENTER));
        InputField temp = new InputField(size);
        add(temp);
        return temp;
    }

    /**
     * The addInputBar method is used to add an input bar to the panel.
     * @param label - The label to be added to the input bar
     * @param size - The size of the input bar
     */
    public InputField addInputBar(String label) {
        add(new JLabel(label + ": ", JLabel.CENTER));
        InputField temp = new InputField(10);
        add(temp);
        return temp;
    }

    /**
     * The addButton method is used to add a button to the panel.
     * @param label - The label to be added to the button
     * @param listener - The listener to be added to the button
     */
    public JButton addButton(String label, ActionListener listener) {
        JButton temp = new JButton(label);
        temp.addActionListener(listener);
        add(temp);
        return temp;
    }

    public JLabel addLabel(String label) {
        JLabel temp = new JLabel("<html>" + label + "</html>");
        add(temp, BorderLayout.NORTH);
        return temp;
    }

    /**
     * The InputField class is a JTextField that is used to create a rounded input field.
     */
    public class InputField extends JTextField {
        private Shape shape;

        public InputField(int size) {
            super(size);
            setOpaque(false); // As suggested by @AVD in comment.
            setHorizontalAlignment(JTextField.CENTER);
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
}

