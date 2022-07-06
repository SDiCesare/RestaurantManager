package restaurant;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class OrderUtil {


    public static boolean availableOrder(Order order) {
        File file = new File("orders/table" + order.getTableNumber() + ".txt");
        boolean exists = file.exists();
        if (!exists) {
            file.delete();
        }
        return exists;
    }

    public static void stampAndDeleteOrder(Order order) {
        String receipt = "";
        int nameLength = OrderUtil.getMaxNameLength(order);
        int totalLength = nameLength + 15;
        float total = 0.0f;
        receipt += String.format("Someone's Restaurant%nVia Something in Somewhere(SM)%n");
        DateTimeFormatter fileDateFormat = DateTimeFormatter.ofPattern("HH_mm_ss_dd_MM_yyyy");
        DateTimeFormatter receiptDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        receipt += String.format("%s%n%n", receiptDateFormat.format(now));
        receipt += String.format("%s %d%n%n", "Table N°", order.getTableNumber());
        receipt += String.format("%-" + nameLength + "s %s %11s%n%n", "Name", "Q", "Costs");
        for (Dish dish : order.getCookedDishes()) {
            int cost = dish.getScope().getCost();
            cost *= dish.getQuantity();
            total += (cost / 100.f);
            receipt += String.format("%-" + nameLength + "s X%d %10.2f$%n", dish.getScope().getName(), dish.getQuantity(), (cost / 100.f));
        }
        String separator = new String(new char[totalLength]).replace("\0", "=");
        receipt += String.format("%n%s%n%-" + (nameLength + 3) + "s %10.2f$%n", separator, "TOTAL", total);
        FileWriter w;
        BufferedWriter writer;
        File file = new File("receipts/table" + order.getTableNumber() + "__" + fileDateFormat.format(now) + ".txt");
        if (!file.getParentFile().exists()) {
            boolean mkdirs = file.getParentFile().mkdirs();
            if (!mkdirs) {
                throw new RuntimeException("Can't create receipts directory!");
            }
        }
        try {
            w = new FileWriter(file);
            writer = new BufferedWriter(w);
            writer.write(receipt);
            writer.write("\n");
            writer.close();
            w.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        File orderFile = new File("orders/table" + order.getTableNumber() + ".txt");
        boolean delete = orderFile.delete();
        if (!delete) {
            System.out.println("Can't delete order file!");
        }
    }

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
     * Returns a list of completed orders
     *
     * @param menu: The menu that contains the Order dishes
     * @return A List of completed orders
     */
    public static Order[] getCompletedOrders(Menu menu) {
        LinkedList<Order> orders = new LinkedList<>();
        for (int i = 1; i <= 10; i++) {
            Order orderFromTable = getOrderFromTable(i, menu);
            if (orderFromTable.getCookedDishes().size() > 0) {
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


    public static void sendOrder(Order order, Menu menu) {
        if (order.getDishes().isEmpty()) { // Don't Send Empty Orders
            return;
        }
        Order actualOrder = getOrderFromTable(order.getTableNumber(), menu);
        for (Dish dish : order.getDishes()) {
            actualOrder.add(dish);
        }
        saveOrder(actualOrder);
    }

    /**
     * Saves the order into a text file
     *
     * @param order: The order to save
     */
    public static void saveOrder(Order order) {
        if (order.getDishes().isEmpty() && order.getCookedDishes().isEmpty()) {//Avoiding Empty Orders
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
