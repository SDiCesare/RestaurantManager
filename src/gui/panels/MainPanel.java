package gui.panels;

import gui.MainFrame;
import gui.buttons.TextButtonHighlighted;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * This Class represent the Main Menu of the Restaurant Manager.
 * It handles the close of the window, and the choice of the employee type.
 */
public class MainPanel extends AbstractPanel {

    private TextButtonHighlighted waiterSelectionButton;
    private TextButtonHighlighted cookSelectionButton;
    private TextButtonHighlighted chefSelectionButton;
    private TextButtonHighlighted cashierSelectionButton;
    private TextButtonHighlighted exitButton;

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
            Component root = getRoot(((Component) e.getSource()));
            if (root instanceof MainFrame) {
                ((MainFrame) root).setContent(new WaiterPanel(((MainFrame) root).getMenu()));
            }
        });
        this.cookSelectionButton = addEmployeeButton("Cook");
        this.cookSelectionButton.setAction((e) -> {
            Component root = getRoot(((Component) e.getSource()));
            if (root instanceof MainFrame) {
                ((MainFrame) root).setContent(new CookPanel(((MainFrame) root).getMenu()));
            } else {
                throw new RuntimeException("Root must be an instance of MainFrame");
            }
        });
        this.chefSelectionButton = addEmployeeButton("Chef");
        this.chefSelectionButton.setAction((e) -> {
            Component root = getRoot(((Component) e.getSource()));
            if (root instanceof MainFrame) {
                ((MainFrame) root).setContent(new ChefPanel(((MainFrame) root).getMenu()));
            } else {
                throw new RuntimeException("Root must be an instance of MainFrame");
            }
        });
        this.cashierSelectionButton = addEmployeeButton("Cashier");
        this.cashierSelectionButton.setAction((e) -> {
            Component root = getRoot(((Component) e.getSource()));
            if (root instanceof MainFrame) {
                ((MainFrame) root).setContent(new CashierPanel(((MainFrame) root).getMenu()));
            } else {
                throw new RuntimeException("Root must be an instance of MainFrame");
            }
        });
        this.exitButton = addEmployeeButton("Exit");
        this.exitButton.setAction((e) -> {
            Component root = SwingUtilities.getRoot(((Component) e.getSource()));
            root.dispatchEvent(new WindowEvent((Window) root, WindowEvent.WINDOW_CLOSING));
        });
    }

    private Component getRoot(Component source) {
        return SwingUtilities.getRoot(source);
    }

    @Override
    String getBackgroundAssets() {
        return "assets/background.png";
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
     * @return The {@link TextButtonHighlighted CustomButton} associated with the Employee Type
     */
    private TextButtonHighlighted addEmployeeButton(String text) {
        TextButtonHighlighted button = new TextButtonHighlighted(Color.LIGHT_GRAY, Color.YELLOW);
        button.setBounds(215, 180 + employeeCount * 100, 150, 30);
        button.setText(text);
        button.setFont(button.getFont().deriveFont(30.0f));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        this.add(button);
        employeeCount++;
        return button;
    }
}
