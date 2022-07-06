package main;

import gui.MainFrame;

import java.io.File;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        File menuFile;
        String menuFilePath = getMenuPathFromOptions(args);
        if (menuFilePath == null) {
            System.out.println("Using Default Menu file");
            menuFile = new File("Menu.txt");
        } else {
            menuFile = new File(menuFilePath);
        }
        MainFrame mainFrame = new MainFrame(menuFile);
        mainFrame.setVisible(true);
    }

    private static String getMenuPathFromOptions(String[] args) {
        String menu1 = getOptionValue("-m", args);
        String menu2 = getOptionValue("-menu", args);
        return menu1 == null ? menu2 : menu1;
    }

    private static String getOptionValue(String option, String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(option)) {
                if (i == args.length - 1) {
                    return null;
                }
                return args[i + 1];
            }
        }
        return null;
    }

}
