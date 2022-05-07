package gui;

import gui.panels.MainPanel;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Restaurant Manager");
        this.setSize(600, 800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        MainPanel comp = new MainPanel();
        this.add(comp);
    }

}
