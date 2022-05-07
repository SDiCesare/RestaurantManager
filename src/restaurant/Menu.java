package restaurant;

import java.util.ArrayList;

public class Menu {

    private final ArrayList<MenuScope> menu;

    /**
     * The Default constructor of the class Menu
     */
    public Menu() {
        this.menu = new ArrayList<>();
    }

    /**
     * Adds a MenuScope on this menu
     *
     * @param scope The MenuScope to add in this Menu
     */
    public void add(MenuScope scope) {
        this.menu.add(scope);
    }

    /**
     * Returns a MenuScope based on the index of this Menu
     *
     * @param index The index of the MenuScope
     * @return The MenuScope at index. Null if the index is out of bounds
     */
    public MenuScope getMenuScope(int index) {
        if (index < 0 || index >= menu.size())
            return null;
        return this.menu.get(index);
    }

    /**
     * Returns a MenuScope based on his name
     *
     * @param name The name of the MenuScope
     * @return The MenuScope with that name. Null if the name is not found
     */
    public MenuScope getMenuScopeFromName(String name) {
        for (MenuScope scope : menu) {
            if (scope.getName().equals(name)) {
                return scope;
            }
        }
        return null;
    }

    /**
     * Returns a MenuScope based on his id
     *
     * @param id The id of the MenuScope
     * @return The MenuScope with that id. Null if the id is not found
     */
    public MenuScope getMenuScopeFromID(int id) {
        for (MenuScope scope : menu) {
            if (scope.getId() == id) {
                return scope;
            }
        }
        return null;
    }

    public ArrayList<MenuScope> getMenu() {
        return menu;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menu=" + menu +
                '}';
    }
}
