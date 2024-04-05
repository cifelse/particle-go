package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;

import javax.swing.JComboBox;

import models.Particle;
import models.Resources;

public class AddParticlePanel extends Panel {
    // The Title of the Panel
    private static final String PANEL_TITLE = "Add Particles";

    // The Default Form
    private static final int DEFAULT_FORM = 1;

    // The interval for spawning the particles
    private static final int INTERVAL = 100;

    // The Dropdown Menu for the Forms
    private final JComboBox<String> forms = new JComboBox<>(new String[] {"Form 1", "Form 2", "Form 3"});

    // Set the Form to Default
    private int form = DEFAULT_FORM;

    // The Input Fields
    private InputField x, y, count, speed, angle, start, start2, end, end2;

    private Resources resources;

    private ExecutorService executor;

    // The Panel for the Batch Addition (Form 1, 2, 3)
    private Panel addBatchPanel;
    
    public AddParticlePanel(ExecutorService executor, Resources resources) {
        // Call the Panel constructor
        super(PANEL_TITLE, new BorderLayout()); 
        
        // Set the Resources
        this.resources = resources;

        // Set the Executor
        this.executor = executor;

        /**
         * First Portion of the Add Particles Panel is the
         * Single Particle Addition. This adds that settings
         * to the Control Panel.
         */
        add(new AddSinglePanel(), BorderLayout.NORTH);

        addBatchPanel = new AddBatchPanel(DEFAULT_FORM);

        /**
         * Second Portion of the Add Particles Panel is the
         * Batch Particle Addition. This adds that settings
         * to the Control Panel.
         */
        add(addBatchPanel, BorderLayout.CENTER);

        forms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the Batch Panel
                remove(addBatchPanel);

                // Set the Form
                form = forms.getSelectedIndex() + 1;

                // Create the new Batch Panel according to the Form
                addBatchPanel = new AddBatchPanel(form);

                // Add the Batch Panel
                add(addBatchPanel, BorderLayout.CENTER);

                // Revalidate and Repaint the Panel
                revalidate();
                repaint();
            }
        });
    }

    /**
     * The Panel that contains the Single Particle Addition
     */
    private class AddSinglePanel extends Panel {
        public AddSinglePanel() {
            super(new BorderLayout());

            add(new InputPanel(), BorderLayout.NORTH);

            add(new ButtonPanel(), BorderLayout.SOUTH);
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
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    resources.addParticle(new Particle(x.getText(), y.getText(), speed.getText(), angle.getText()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
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
    }

    /**
     * The Panel that contains the Batch Particle Addition
     */
    private class AddBatchPanel extends Panel {
        public static final String PANEL_TITLE = "Add in Batches";

        public AddBatchPanel(int _form) {
            super(PANEL_TITLE, new BorderLayout());

            // Set the Form
            form = _form;

            // Add the Dropdown Menu
            add(forms, BorderLayout.NORTH);

            // Set the Labels
            String firstLabel, endLabel;

            // Add the Form Input Panel
            switch (form) {
                case 2:
                    firstLabel = "Start Θ";
                    endLabel = "End Θ";
                    add(new FormInputPanel(firstLabel, endLabel), BorderLayout.CENTER);   
                    break;
                case 3:
                    firstLabel = "Start Velocity";
                    endLabel = "End Velocity";
                    add(new FormInputPanel(firstLabel, endLabel), BorderLayout.CENTER);   
                    break;
                default: 
                    add(new FormInputPanel(), BorderLayout.CENTER);   
                    break;
            }
            
            // Add the Form Button Panel
            add(new FormButtonPanel(), BorderLayout.SOUTH);
        }

        public class FormInputPanel extends Panel {
            public FormInputPanel(String startLabel, String endLabel) {
                super(new GridLayout(3, 2, 0, 5));

                count = addInputBar("Count (N)", 10);

                start = addInputBar(startLabel, 10);

                end = addInputBar(endLabel, 10);
            }

            public FormInputPanel() {
                super(new BorderLayout());

                add(new SpecialFormInputA(), BorderLayout.NORTH);

                add(new SpecialFormInputB(), BorderLayout.SOUTH);
            }

            private class SpecialFormInputA extends Panel {
                public SpecialFormInputA() {
                    super(new GridLayout(1, 2, 0, 5));

                    count = addInputBar("Count (N)", 10);
                }
            }

            private class SpecialFormInputB extends Panel {
                public SpecialFormInputB() {
                    super(new GridLayout(2, 4, 0, 5));

                    start = addInputBar("<html>X<sub>1</sub</html>", 5);

                    start2 = addInputBar("<html>Y<sub>1</sub</html>", 5);

                    end = addInputBar("<html>X<sub>2</sub</html>", 5);

                    end2 = addInputBar("<html>Y<sub>2</sub</html>", 5);
                }
            }
        }

        /**
        * Below are the Button Configurations for the Form Panel
        */
        private class FormButtonPanel extends Panel {
            public FormButtonPanel() {
                super(new GridLayout(1, 1));

                addButton("Add Particles in Batches", new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    float start = Float.parseFloat(AddParticlePanel.this.start.getText());
                                    float end = Float.parseFloat(AddParticlePanel.this.end.getText());
                                    int count = Integer.parseInt(AddParticlePanel.this.count.getText());

                                    // Get the Final Values to avoid the final modifier
                                    int finalX = Integer.parseInt(x.getText());
                                    int finalY = Integer.parseInt(y.getText());
                                    float finalSpeed = Float.parseFloat(speed.getText());
                                    float finalAngle = Float.parseFloat(angle.getText());

                                    switch (form) {
                                        // Add the Special Form 2
                                        case 2:
                                            executor.execute(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        float diff = (end - start) / (count - 1);
                                                        float currentAngle = start;

                                                        for (int i = count; i > 0; i--) {
                                                            synchronized(resources) {
                                                                resources.addParticle(new Particle(finalX, finalY, finalSpeed, currentAngle));
                                                                System.out.println(currentAngle);
                                                                currentAngle += diff;
                                                                Thread.sleep(INTERVAL);
                                                            }
                                                        }
                                                    }
                                                    catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                            break;
                                        // Add the Special Form 3
                                        case 3:
                                            executor.execute(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        float step = (end - start) / (count - 1);
                                                        float currentSpeed = start;
                                                        
                                                        for (int i = count; i > 0; i--) {
                                                            synchronized(resources) {
                                                                resources.addParticle(new Particle(finalX, finalY, currentSpeed, finalAngle));
                                                                currentSpeed += step;
                                                                Thread.sleep(INTERVAL);
                                                            }
                                                        }
                                                    }
                                                    catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                            break;

                                        // Add the Special Form 1 or the Default Form 
                                        default:
                                            executor.execute(new Runnable() {
                                                @Override
                                                public void run(){
                                                    try {
                                                        float x1 = Integer.parseInt(AddParticlePanel.this.start.getText());
                                                        float y1 = Integer.parseInt(AddParticlePanel.this.start2.getText());
                                                        float x2 = Integer.parseInt(AddParticlePanel.this.end.getText());
                                                        float y2 = Integer.parseInt(AddParticlePanel.this.end2.getText());
                                                        
                                                        //Get start and end points of each particle
                                                        int[][] particles = new int[count][2];

                                                        //Determine sthe change between x and y coordinates
                                                        float dx = (float) (x2 - x1) / (count);
                                                        float dy = (float) (y2 - y1) / (count);

                                                        //Incrementally increments dx and dy per particle
                                                        for (int i = 0; i < count; i++) {
                                                            particles[i][0] = (int) (x1 + i * dx);
                                                            particles[i][1] = (int) (y1 + i * dy);
                                                        }

                                                        for (int i = 0; i < count; i++) {
                                                            synchronized(resources) {
                                                                resources.addParticle(new Particle(particles[i][0], particles[i][1], finalSpeed, finalAngle));
                                                                Thread.sleep(INTERVAL);
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                            break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }
        }
    }
}
