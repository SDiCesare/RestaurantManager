package gui.panels;

import gui.buttons.CustomButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This Class represent the Main Menu of the Restaurant Manager.
 * It handles the close of the window, and the choice of the employee type.
 */
public class MainPanel extends JPanel {

    private static BufferedImage background;

    static {
        try {
            background = ImageIO.read(MainPanel.class.getClassLoader().getResourceAsStream("assets/background.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private CustomButton waiterSelectionButton;
    private CustomButton cookSelectionButton;
    private CustomButton chefSelectionButton;
    private CustomButton cashierSelectionButton;
    private CustomButton exitButton;

    /**
     * The Default Constructor of the {@link MainPanel MainMenu} class
     * The default size of this class in this program are 594x771
     */
    public MainPanel() {
        super();
        //223, 130, 150, 30)
        this.setLayout(null);
        this.waiterSelectionButton = addEmployeeButton("Waiter");
        this.waiterSelectionButton.setAction((e) -> {
            System.out.println("Waiter");
        });
        this.cookSelectionButton = addEmployeeButton("Cook");
        this.waiterSelectionButton.setAction((e) -> {
            System.out.println("Cook");
        });
        this.chefSelectionButton = addEmployeeButton("Chef");
        this.waiterSelectionButton.setAction((e) -> {
            System.out.println("Chef");
        });
        this.cashierSelectionButton = addEmployeeButton("Cashier");
        this.waiterSelectionButton.setAction((e) -> {
            System.out.println("Cashier");
        });
        this.exitButton = addEmployeeButton("Exit");
        this.exitButton.setAction((e) -> {
            Component root = SwingUtilities.getRoot(((Component) e.getSource()));
            root.dispatchEvent(new WindowEvent((Window) root, WindowEvent.WINDOW_CLOSING));
        });
    }

    /**
     * Used for taking trace of how much Employee Type are added to this panel
     *
     * @see #addEmployeeButton(String) for usage
     */
    private int employeeCount = 0;

    /**
     * Add An Employee Type Button Selection
     *
     * @param text The Name of the Employee Type
     * @return The {@link CustomButton CustomButton} associated with the Employee Type
     */
    private CustomButton addEmployeeButton(String text) {
        CustomButton button = new CustomButton(Color.LIGHT_GRAY, Color.YELLOW);
        button.setBounds(215, 180 + employeeCount * 100, 150, 30);
        button.setText(text);
        button.setFont(button.getFont().deriveFont(30.0f));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        this.add(button);
        employeeCount++;
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
