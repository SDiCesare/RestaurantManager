package restaurant;

import java.util.LinkedList;

public class Order {

    private int tableNumber;
    private LinkedList<Dish> dishes;

    /**
     * The Constructor of the class Order.
     * Initialize an Empty Order, not associated with any table.
     * This Constructor should be used only for retrieving an order from a file.
     */
    public Order() {
        this(-1, null);
    }

    /**
     * The Constructor of the class Order.
     * Initialize an Order based on the Table Number, and with an empty list of {@link Dish Dishes}.
     *
     * @param tableNumber The Table Number
     */
    public Order(int tableNumber) {
        this(tableNumber, null);
    }

    /**
     * The Constructor of the class Order.
     * Initialize an Order based on a precedent list of {@link Dish Dishes} and the table number.
     *
     * @param tableNumber The Table Number
     * @param order       The list of Dishes of this order
     */
    public Order(int tableNumber, LinkedList<Dish> order) {
        this.tableNumber = tableNumber;
        if (order != null) {
            this.dishes = order;
        } else {
            this.dishes = new LinkedList<>();
        }
    }

    /**
     * Gets a Dish from his index on the {@link #dishes dishes} of this Order.
     *
     * @param index The index of the dish in this Order's list
     * @return The dish at the specified index
     */
    public Dish get(int index) {
        if (index < 0 || index >= this.dishes.size()) {
            return null;
        }
        return this.dishes.get(index);
    }

    /**
     * Gets a Dish from his associated {@link MenuScope MenuScope}.
     *
     * @param scope The MenuScope associated with the dish
     * @return The dish with the specified MenuScope
     */
    public Dish get(MenuScope scope) {
        for (Dish dish : this.dishes) {
            if (dish.getScope().equals(scope)) {
                return dish;
            }
        }
        return null;
    }

    public LinkedList<Dish> getDishes() {
        return dishes;
    }

    /**
     * Adds a Dish with the specified {@link MenuScope MenuScope} and a quantity of 1
     *
     * @param scope The MenuScope to add
     */
    public void add(MenuScope scope) {
        this.add(scope, 1);
    }

    /**
     * Adds a Dish with the specified {@link MenuScope MenuScope}
     * If this order has this specified MenuScope in it, it will add the quantity to the scope.
     *
     * @param scope    The MenuScope to add
     * @param quantity The MenuScope quantity to add
     */
    public void add(MenuScope scope, int quantity) {
        for (Dish dish : this.dishes) {
            if (dish.getScope().equals(scope)) {
                dish.incrementQuantity(quantity);
                return;
            }
        }
        this.dishes.add(new Dish(scope, quantity));
    }

    /**
     * Adds a Dish to this order.
     * If the dish is already in this Order, the quantity of that dish are incremented.
     *
     * @param d The dish to add
     */
    public void add(Dish d) {
        for (Dish dish : this.dishes) {
            if (dish.getScope().equals(d.getScope())) {
                dish.incrementQuantity(d.getQuantity());
                return;
            }
        }
        this.dishes.add(d);
    }

    /**
     * Decrements by 1 the quantity associated with a {@link MenuScope MenuScope} on this order.
     *
     * @param scope The MenuScope to which the quantity is decremented
     */
    public void decrementDishQuantity(MenuScope scope) {
        this.incrementDishQuantity(scope, -1);
    }

    /**
     * Increments by 1 the quantity associated with a {@link MenuScope MenuScope} on this order.
     *
     * @param scope The MenuScope to which the quantity is decremented
     */
    public void incrementDishQuantity(MenuScope scope) {
        this.incrementDishQuantity(scope, 1);
    }

    /**
     * Increments by an amount the quantity associated with a {@link MenuScope MenuScope} on this order.
     *
     * @param scope  The MenuScope to which the quantity is decremented
     * @param amount The increment
     */
    public void incrementDishQuantity(MenuScope scope, int amount) {
        for (Dish dish : this.dishes) {
            if (dish.getScope().equals(scope)) {
                dish.incrementQuantity(amount);
            }
        }
    }

    /**
     * Removes a {@link MenuScope MenuScope} from this order
     *
     * @param scope The MenuScope to remove
     */
    public void remove(MenuScope scope) {
        int index = -1;
        for (int i = 0; i < this.dishes.size(); i++) {
            Dish dish = this.dishes.get(i);
            if (dish.getScope().equals(scope)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            this.dishes.remove(index);
        }
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
}
