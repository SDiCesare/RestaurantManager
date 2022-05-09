package restaurant;

public class Dish {

    /**
     * Convert a Dish into a string
     *
     * @param dish The Dish to convert
     */
    public static String toText(Dish dish) {
        return dish.scope.getId() + ";" + dish.quantity;
    }

    /**
     * Parse a string into a Dish associated to a {@link Menu Menu}
     *
     * @param text the string to parse
     * @param menu The menu associated with the dish
     */
    public static Dish fromText(String text, Menu menu) {
        String[] split = text.split(";");
        MenuScope menuScope = menu.getMenuScopeFromID(Integer.parseInt(split[0]));
        return new Dish(menuScope, Integer.parseInt(split[1]));
    }

    private final MenuScope scope;
    private int quantity;

    /**
     * The Constructor of the Dish Class.
     * Initialize a Dish with the specified scope and a quantity of 1.
     *
     * @param scope The {@link MenuScope MenuScope} to associate with this Dish
     */
    public Dish(MenuScope scope) {
        this(scope, 1);
    }

    /**
     * The Constructor of the Dish Class.
     * Initialize a Dish with the specified scope and quantity.
     *
     * @param scope    The {@link MenuScope MenuScope} to associate with this Dish
     * @param quantity The quantity of this Dish
     */
    public Dish(MenuScope scope, int quantity) {
        this.scope = scope;
        this.quantity = quantity;
    }

    /**
     * Increments this Dish quantity by 1.
     */
    public void incrementQuantity() {
        this.quantity++;
    }

    /**
     * Increments this Dish quantity by a specified amount.
     *
     * @param amount The quantity to add
     */
    public void incrementQuantity(int amount) {
        this.quantity += amount;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public MenuScope getScope() {
        return scope;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "scope=" + scope +
                ", quantity=" + quantity +
                '}';
    }
}
