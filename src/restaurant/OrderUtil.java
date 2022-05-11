package restaurant;

import java.io.*;

public class OrderUtil {

    public static Order getOrderFromTable(int table, Menu menu) {
        File file = new File("orders/table" + table + ".txt");
        if (!file.exists()) {
            return new Order(table);
        }
        return loadOrderFromFile(file, menu);
    }


    public static void sendOrder(Order order, Menu menu) {
        File file = new File("orders/table" + order.getTableNumber() + ".txt");
        if (!file.getParentFile().exists()) {
            boolean mkdirs = file.getParentFile().mkdirs();
            if (!mkdirs) {
                throw new RuntimeException("Can't create orders directory!");
            }
        }
        saveOrderOnFile(file, order);
        /*System.out.println("Combining");
        Order orderFromTable = getOrderFromTable(order.getTableNumber(), menu);
        order.combine(orderFromTable);
        saveOrderOnFile(file, order);*/
    }

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
                Dish cooked = order.getCooked(dish.getScope());
                writer.write(Dish.toText(dish));
                if (cooked != null) {
                    writer.write("|" + Dish.toText(cooked));
                }
                writer.write('\n');
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
            while (ln != null) {
                if (ln.contains("|")) { //Has Cooked Dishes
                    String[] split = ln.split("\\|");
                    order.add(Dish.fromText(split[0], menu));
                    order.addCooked(Dish.fromText(split[1], menu));
                } else {
                    order.add(Dish.fromText(ln, menu));
                }
                ln = reader.readLine();
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
