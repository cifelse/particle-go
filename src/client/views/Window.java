package client.views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;

/**
 * The Window class is a JFrame that is used to display the simulation and control panels.
 */
public class Window extends JFrame {
    // Window Title
    public static final String TITLE = "Particle Go";

    // Window Size
    public static final int WIDTH = 970;
    public static final int HEIGHT = 720;

    // Executor Service
    private ExecutorService executorService;

    /**
     * The Window constructor is used to create a new Window. This defaults to create a Login Window
     * @param executor - Thread Executors
     * @param type - Type of Window to Display
     */
    public Window(ExecutorService executorService) {
        // Set the title of the window
        super(TITLE);

        // Set Executor Service
        this.executorService = executorService;

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the size of the window
        setSize(Window.WIDTH, Window.HEIGHT);

        // Set the window to be not resizable
        setResizable(false);
        
        // Set the layout of the window
        setLayout(new FlowLayout());
        
        // Set the window to be visible
        setVisible(true);

        // Set the Chosen UI as the Content
        setContentPane(new LoginUI());

        // Close everything when X is clicked
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                System.gc();
            }
        });
    }

    /**
     * The Window constructor is used to create a new Window. This creates a Client immediately
     * @param executorService - Thread Executors
     * @param ip - The IP Address of the Server
     * @param username - The unique identifier of the Player/Explorer
     */
    public Window(ExecutorService executorService, String ip, String username) {
        // Set the title of the window
        super(TITLE);

        // Set Executor Service
        this.executorService = executorService;

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the size of the window
        setSize(Window.WIDTH, Window.HEIGHT);

        // Set the window to be not resizable
        setResizable(false);
        
        // Set the layout of the window
        setLayout(new FlowLayout());
        
        // Set the window to be visible
        setVisible(true);

        setContentPane(new ClientUI(ip, username));

        // Close everything when X is clicked
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                executorService.shutdownNow();
                System.gc();
            }
        });
    }

    /**
     * The Main Client UI
     */
    private class ClientUI extends JSplitPane {
        public ClientUI(String ip, String username) {
            // Create the a Split Pane with specific orientation and divider location
            setOrientation(JSplitPane.HORIZONTAL_SPLIT);
            setDividerLocation(Screen.WIDTH);

            // Create the Side Panel
            SidePanel sidePanel = new SidePanel(ip, username);
            add(sidePanel, JSplitPane.RIGHT);

            // Create the Screen
            add(new Screen(sidePanel), JSplitPane.LEFT);
        }
    }

    /**
     * The Main Login UI
     */
    private class LoginUI extends Panel {
        InputField ip, username;

        public LoginUI() {
            super(new BorderLayout());

            add(new InputPanel(), BorderLayout.CENTER);

            add(new ButtonPanel(), BorderLayout.SOUTH);
        }

        /**
         * Below are the Main Input Configurations like Count, Speed, and Angle
         */
        private class InputPanel extends Panel {
            public InputPanel() {
                super(new GridLayout(2, 4, 0, 5));

                ip = addInputBar("Enter IP Address");

                username = addInputBar("Enter Username");
            }
        }

        /**
         * Below are the Button Configurations
         */
        private class ButtonPanel extends Panel {
            public ButtonPanel() {
                super(new GridLayout(1, 1));

                // The Login Button
                addButton("Login", new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Find the parent window of the button and Dispose it
                        SwingUtilities.windowForComponent((JButton) e.getSource()).dispose();

                        // Create a New Client Window
                        new Window(executorService, ip.getText(), username.getText());
                    }
                });
            }
        }
    }
}

