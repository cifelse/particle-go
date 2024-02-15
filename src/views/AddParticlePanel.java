package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import models.Particle;
import models.Resources;

public class AddParticlePanel extends Panel {

    private static final String PANEL_TITLE = "Add Particles";

    private static final int DEFAULT_FORM = 1;

    private final JComboBox<String> forms = new JComboBox<>(new String[] {"Form 1", "Form 2", "Form 3"});

    private int form = DEFAULT_FORM;

    private InputField x, y, count, speed, angle, start, end;

    private Resources resources;

    private Panel formPanel;
    
    public AddParticlePanel(Resources resources) {
        // Call the Panel constructor
        super(PANEL_TITLE, new BorderLayout()); 
        
        // Set the Resources
        this.resources = resources;

        add(new InputPanel(), BorderLayout.NORTH);

        add(new ButtonPanel(), BorderLayout.SOUTH);

        formPanel = new FormPanel(DEFAULT_FORM);

        add(formPanel, BorderLayout.CENTER);

        forms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(formPanel);
                form = forms.getSelectedIndex() + 1;
                formPanel = new FormPanel(form);
                add(formPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
    }

    /**
     * Below are the Main Input Configurations like Count, Speed, and Angle
    */
    private class InputPanel extends Panel {
        public InputPanel() {
            super(new GridLayout(4, 2, 0, 5));

            x = addInputBar("X", 10);

            y = addInputBar("Y", 10);

            speed = addInputBar("Speed", 10);

            angle = addInputBar("Angle", 10);
        }
    }

    /**
    * Below are the Button Configurations
    */
    private class ButtonPanel extends Panel {
        public ButtonPanel() {
            super(new GridLayout(1, 2));

            addButton("Add Particles", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int start = Integer.parseInt(AddParticlePanel.this.start.getText());
                    int end = Integer.parseInt(AddParticlePanel.this.end.getText());

                    resources.addParticle(new Particle(x.getText(), y.getText(), speed.getText(), angle.getText()));
                }
            });
    
            addButton("Clear Particles", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resources.clearParticles();
                }
            });
        }
    }

    private class FormPanel extends Panel {
        public static final String PANEL_TITLE = "Add in Batches";

        public FormPanel(int _form) {
            super(PANEL_TITLE, new BorderLayout());

            // Set the Form
            form = _form;

            // Add the Dropdown Menu
            add(forms, BorderLayout.NORTH);

            // Set the Labels
            String firstLabel, endLabel;

            switch (form) {
                case 2:
                    firstLabel = "Start Θ";
                    endLabel = "End Θ";
                    break;
                case 3:
                    
                    firstLabel = "Start Velocity";
                    endLabel = "End Velocity";
                    break;
                default: 
                    firstLabel = "Start Point";
                    endLabel = "End Point";
                    break;
            }

            // Add the Form Input Panel
            add(new FormInputPanel(firstLabel, endLabel), BorderLayout.CENTER);            
        }

        public class FormInputPanel extends Panel {
            public FormInputPanel(String startLabel, String endLabel) {
                super(new GridLayout(3, 2, 0, 5));

                count = addInputBar("Count (N)", 10);

                start = addInputBar(startLabel, 10);

                end = addInputBar(endLabel, 10);
            }
        }
    }
}
