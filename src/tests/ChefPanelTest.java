package tests;

import gui.panels.ChefPanel;
import restaurant.MenuUtil;

import javax.swing.*;
import java.io.File;

public class ChefPanelTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.add(new ChefPanel(MenuUtil.loadMenuFromFile(new File("Test Menu.txt"))));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(600, 800);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
