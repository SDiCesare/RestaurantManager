package gui;

import gui.panels.AbstractPanel;
import gui.panels.MainPanel;
import restaurant.Menu;
import restaurant.MenuUtil;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * The Main Frame of the RestaurantManager
 */
public class MainFrame extends JFrame {

    private AbstractPanel content;
    private Menu menu;
    private File menuFile;

    /**
     * Creates a new MinFrame with a menu loaded from a file
     *
     * @param menuFile: The file that contains the Menu of the restaurant
     */
    public MainFrame(File menuFile) {
        super("Restaurant Manager");
        this.menu = MenuUtil.loadMenuFromFile(menuFile);
        this.menuFile = menuFile;
        this.setSize(600, 800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        content = new MainPanel();
        this.add(content);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                saveMenu();
            }
        });
    }

    public void saveMenu() {
        MenuUtil.saveMenuOnFile(menuFile, MainFrame.this.menu);
    }

    public Menu getMenu() {
        return menu;
    }

    /**
     * Sets the showed panel on this Frame and refresh it.
     *
     * @param content: The new content to display
     */
    public void setContent(AbstractPanel content) {
        this.remove(this.content);
        this.content = content;
        this.add(this.content);
        this.repaint();
        this.revalidate();
    }
}
