package tests;

import restaurant.Menu;
import restaurant.MenuScope;
import restaurant.MenuUtil;

import java.io.File;
import java.util.Random;

public class MenuFileTest {

    public static void main(String[] args) {
        testSaveFile();
        System.out.println("\n========================================\n");
        testLoadFile();
    }

    private static void testLoadFile() {
        Menu menu = MenuUtil.loadMenuFromFile(new File("Test Menu.txt"));
        if (menu == null) {
            System.err.println("Menu not loaded");
            return;
        }
        for (MenuScope scope : menu.getMenu()) {
            System.out.println(scope);
        }
    }

    private static void testSaveFile() {
        Menu menu = new Menu();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            MenuScope scope = new MenuScope(i, randomText(random, 10), random.nextInt(1501) + 500);
            menu.add(scope);
        }
        System.out.println("Menu Created:");
        for (MenuScope scope : menu.getMenu()) {
            System.out.println(scope);
        }
        MenuUtil.saveMenuOnFile(new File("Test Menu.txt"), menu);
        System.out.println("Menu Saved Successfully as \"Test Menu.txt\"");
    }

    private static String randomText(Random random, int length) {
        String out = "";
        for (int i = 0; i < length; i++) {
            out += ((char) (random.nextInt('Z' - 'A' + 1) + 'A'));
        }
        return out;
    }

}
