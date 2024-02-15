package views;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
}
