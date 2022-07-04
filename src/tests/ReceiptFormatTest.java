package tests;

import restaurant.*;

import java.io.File;

public class ReceiptFormatTest {

    public static void main(String[] args) {
        Menu menu = MenuUtil.loadMenuFromFile(new File("Test Menu.txt"));
        Order order = OrderUtil.getOrderFromTable(8, menu);
        String receipt = "";
        int nameLength = OrderUtil.getMaxNameLength(order);
        int totalLength = nameLength + 15;
        float total = 0.0f;
        receipt += String.format("Ike's Restaurant%nVia Something in Somewhere(SM)%n%n");
        receipt += String.format("%-" + nameLength + "s %s %11s%n%n", "Name", "Q", "Costs");
        for (Dish dish : order.getCookedDishes()) {
            int cost = dish.getScope().getCost();
            cost *= dish.getQuantity();
            total += (cost / 100.f);
            receipt += String.format("%-" + nameLength + "s X%d %10.2f$%n", dish.getScope().getName(), dish.getQuantity(), (cost / 100.f));
        }
        String separator = new String(new char[totalLength]).replace("\0", "=");
        receipt += String.format("%n%s%n%-" + (nameLength + 3) + "s %10.2f$%n", separator, "TOTAL", total);
        System.out.println(receipt);
    }

}
