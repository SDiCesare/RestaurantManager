package tests;

import gui.panels.WaiterPanel;
import restaurant.MenuUtil;

import javax.swing.*;
import java.io.File;

public class WaiterPanelTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.add(new WaiterPanel(MenuUtil.loadMenuFromFile(new File("Test Menu.txt"))));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(600, 800);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
