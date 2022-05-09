package tests;

import restaurant.*;

import java.io.File;
import java.util.Random;

public class OrderFileTest {

    public static void main(String[] args) {
        Menu menu = MenuUtil.loadMenuFromFile(new File("Test Menu.txt"));
        testSaveFile(menu);
        System.out.println("\n========================================\n");
        testLoadFile(menu);
    }

    private static void testSaveFile(Menu menu) {
        Random random = new Random();
        Order order = new Order(random.nextInt(10) + 1);
        for (int i = 0; i < 5; i++) {
            MenuScope menuScope = getRandomScope(menu, random);
            order.add(menuScope);
            order.incrementDishQuantity(menuScope, random.nextInt(11) - 5);
        }
        System.out.println("Order Created:");
        for (Dish dish : order.getDishes()) {
            System.out.println(dish);
        }
        OrderUtil.saveOrderOnFile(new File("Test Order.txt"), order);
        System.out.println("Order Saved Successfully as \"Test Order.txt\"");
    }

    private static void testLoadFile(Menu menu) {
        Order order = OrderUtil.loadOrderFromFile(new File("Test Order.txt"), menu);
        if (order == null) {
            System.err.println("Order not loaded");
            return;
        }
        for (Dish dish : order.getDishes()) {
            System.out.println(dish);
        }
    }

    private static MenuScope getRandomScope(Menu menu, Random random) {
        while (true) {
            int i = random.nextInt(menu.entries());
            MenuScope menuScopeFromID = menu.getMenuScopeFromID(i);
            if (menuScopeFromID != null) {
                return menuScopeFromID;
            }
        }
    }

}
