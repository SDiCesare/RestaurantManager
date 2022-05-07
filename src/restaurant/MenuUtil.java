package restaurant;

import java.io.*;

public class MenuUtil {

    /**
     * Save a Menu as a text file
     *
     * @param file The file in which the menu is saved
     * @param menu The menu to save
     */
    public static void saveMenuOnFile(File file, Menu menu) {
        FileWriter w;
        BufferedWriter writer;
        try {
            w = new FileWriter(file);
            writer = new BufferedWriter(w);
            for (MenuScope scope : menu.getMenu()) {
                writer.write(MenuScope.toText(scope) + "\n");
            }
            writer.close();
            w.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load a menu from a file
     *
     * @param file The file in which the menu is saved
     * @return The menu saved in the file
     */
    public static Menu loadMenuFromFile(File file) {
        FileReader r = null;
        BufferedReader reader = null;
        try {
            r = new FileReader(file);
            reader = new BufferedReader(r);
            Menu menu = new Menu();
            String ln = reader.readLine();
            while (ln != null) {
                menu.add(MenuScope.fromText(ln));
                ln = reader.readLine();
            }
            return menu;
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
