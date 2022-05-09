package restaurant;

import java.io.*;

public class OrderUtil {

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
                writer.write(Dish.toText(dish) + "\n");
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
                order.add(Dish.fromText(ln, menu));
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
