package restaurant;

import java.io.*;
import java.util.LinkedList;

public class OrderUtil {


    /**
     * Calculate and returns the name with the higher length in the Order
     *
     * @param order: The Order containing the MenuScopes
     * @return The higher name length from the MenuScopes
     */
    public static int getMaxNameLength(Order order) {
        int length = 0;
        for (Dish dish : order.getDishes()) {
            int l = dish.getScope().getName().length();
            if (l > length) {
                length = l;
            }
        }
        for (Dish dish : order.getCookedDishes()) {
            int l = dish.getScope().getName().length();
            if (l > length) {
                length = l;
            }
        }
        return length;
    }

    /**
     * Returns a list of orders that contains only the ones not empty
     *
     * @param menu: The menu that contains the Order dishes
     * @return A List of not empty orders
     */
    public static Order[] getActiveOrders(Menu menu) {
        LinkedList<Order> orders = new LinkedList<>();
        for (int i = 1; i <= 10; i++) {
            Order orderFromTable = getOrderFromTable(i, menu);
            if (orderFromTable.entries() > 0) {
                orders.add(orderFromTable);
            }
        }
        return orders.toArray(new Order[]{});
    }

    /**
     * Returns an order from a table based on a specific menu
     *
     * @param table: The table index from which its retrieved the order
     * @param menu:  The menu that contains the various dishes
     * @return The order of the table
     */
    public static Order getOrderFromTable(int table, Menu menu) {
        File file = new File("orders/table" + table + ".txt");
        if (!file.exists()) {
            return new Order(table);
        }
        return loadOrderFromFile(file, menu);
    }


    /**
     * Saves the order into a text file
     *
     * @param order: The order to save
     */
    public static void saveOrder(Order order) {
        if (order.getDishes().isEmpty() && order.getCookedDishes().isEmpty()) {//Avoiding Empty Order
            return;
        }
        File file = new File("orders/table" + order.getTableNumber() + ".txt");
        if (!file.getParentFile().exists()) {
            boolean mkdirs = file.getParentFile().mkdirs();
            if (!mkdirs) {
                throw new RuntimeException("Can't create orders directory!");
            }
        }
        saveOrderOnFile(file, order);
    }

    private static final String ORDER_SEPARATOR = "==COOKED==";

    /**
     * Save an Order as a text file
     *
     * @param file  The file in which the order is saved
     * @param order The order to save
     */
    public static void saveOrderOnFile(File file, Order order) {
        FileWriter w;
        BufferedWriter writer;
        try {
            w = new FileWriter(file);
            writer = new BufferedWriter(w);
            writer.write(order.getTableNumber() + "\n");
            for (Dish dish : order.getDishes()) {
                writer.write(Dish.toText(dish));
                writer.write('\n');
            }
            if (!order.getCookedDishes().isEmpty()) {
                writer.write(ORDER_SEPARATOR + "\n");
                for (Dish dish : order.getCookedDishes()) {
                    writer.write(Dish.toText(dish));
                    writer.write('\n');
                }
            }
            writer.close();
            w.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load an order from a file
     *
     * @param file The file in which the order is saved
     * @param menu The menu which are associated the {@link MenuScope MenuScopes} of the order
     * @return The order saved in the file
     */
    public static Order loadOrderFromFile(File file, Menu menu) {
        FileReader r = null;
        BufferedReader reader = null;
        try {
            r = new FileReader(file);
            reader = new BufferedReader(r);
            Order order = new Order();
            int tableNumber = Integer.parseInt(reader.readLine());
            order.setTableNumber(tableNumber);
            String ln = reader.readLine();
            while (ln != null && !ln.equals(ORDER_SEPARATOR)) {
                order.add(Dish.fromText(ln, menu));
                ln = reader.readLine();
            }
            if (ln != null) {
                ln = reader.readLine();
                while (ln != null) {
                    order.addCooked(Dish.fromText(ln, menu));
                    ln = reader.readLine();
                }
            }
            return order;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (r != null) {
                try {
                    r.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
