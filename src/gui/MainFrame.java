package gui;

import gui.panels.MainPanel;
import restaurant.Menu;
import restaurant.MenuUtil;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class MainFrame extends JFrame {

    private JPanel content;
    private Menu menu;

    public MainFrame() {
        super("Restaurant Manager");
        this.menu = MenuUtil.loadMenuFromFile(new File("Test Menu.txt"));
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
                MenuUtil.saveMenuOnFile(new File("Test Menu.txt"), MainFrame.this.menu);
            }
        });
    }

    public Menu getMenu() {
        return menu;
    }

    public void setContent(JPanel content) {
        this.remove(this.content);
        this.content = content;
        this.add(this.content);
        this.repaint();
        this.revalidate();
    }
}
