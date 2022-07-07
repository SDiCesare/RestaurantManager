package restaurant;

import java.util.ArrayList;

public class Order {

    private int tableNumber;
    private final ArrayList<Dish> dishes;
    private final ArrayList<Dish> cookedDishes;

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
    public Order(int tableNumber, ArrayList<Dish> order) {
        this.tableNumber = tableNumber;
        if (order != null) {
            this.dishes = order;
        } else {
            this.dishes = new ArrayList<>();
        }
        this.cookedDishes = new ArrayList<>();
    }

    /**
     * Check if the Order contains a Dish
     *
     * @param dish: The dish to find
     * @return True if the dish is presents in this order, False otherwise
     */
    public boolean contains(Dish dish) {
        return this.dishes.contains(dish);
    }

    /**
     * Check if the Order contains a MenuScope
     *
     * @param scope: The scope to find
     * @return True if the scope is presents in this order, False otherwise
     */
    public boolean contains(MenuScope scope) {
        for (Dish dish : this.dishes) {
            if (dish.getScope().equals(scope)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a new Dish with a MenuScope and a quantity of 1
     *
     * @param scope: The MenuScope of the dish
     */
    public void add(MenuScope scope) {
        this.add(scope, 1);
    }

    /**
     * Adds a new Dish with a MenuScope and a quantity
     *
     * @param scope:    The MenuScope of the Dish
     * @param quantity: The quantity of the Dish
     */
    public void add(MenuScope scope, int quantity) {
        this.dishes.add(new Dish(scope, quantity));
    }

    /**
     * Adds a Dish to this Order.
     * If a Dish with the same MenuScope is present, it will add his quantity to the present Dish
     *
     * @param dish: The Dish to add
     */
    public void add(Dish dish) {
        int index = this.indexOf(dish);
        if (index == -1) {
            this.dishes.add(dish);
        } else {
            this.dishes.get(index).incrementQuantity(dish.getQuantity());
        }
    }

    /**
     * Adds a Cooked Dish to this Order.
     * If a Dish with the same MenuScope is present, it will add his quantity to the present Dish
     *
     * @param dish: The Dish to add
     */
    public void addCooked(Dish dish) {
        for (Dish d : this.cookedDishes) {
            if (d.getScope().equals(dish.getScope())) {
                d.incrementQuantity(dish.getQuantity());
                return;
            }
        }
        this.cookedDishes.add(dish);
    }

    /**
     * Remove a MenuScope from this Order if present.
     *
     * @param scope: The MenuScope to remove
     */
    public void remove(MenuScope scope) {
        for (int i = 0; i < this.entries(); i++) {
            Dish dish = this.dishes.get(i);
            if (dish.getScope().equals(scope)) {
                this.dishes.remove(dish);
                return;
            }
        }
    }

    /**
     * Remove a Dish from this Order if present.
     *
     * @param dish: The Dish to remove
     */
    public void remove(Dish dish) {
        if (this.indexOf(dish) != -1) {
            this.dishes.remove(this.indexOf(dish));
        }
    }

    /**
     * Get the index of a MenuScope in this Order.
     * If the MenuScope is not present, a value of -1 is returned.
     *
     * @param scope: The MenuScope to find
     * @return The index of the scope
     */
    public int indexOf(MenuScope scope) {
        for (int i = 0; i < this.entries(); i++) {
            Dish dish = this.dishes.get(i);
            if (dish.getScope().equals(scope)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Get the index of a Dish in this Order.
     * If the Dish is not present, a value of -1 is returned.
     *
     * @param dish: The Dish to find
     * @return The index of the scope
     */
    public int indexOf(Dish dish) {
        int index = this.dishes.indexOf(dish);
        if (index != -1) {
            return index;
        }
        for (int i = 0; i < this.entries(); i++) {
            Dish d = this.dishes.get(i);
            if (d.getScope().equals(dish.getScope())) {
                return i;
            }
        }
        return index;
    }

    /**
     * Gets the Dish in the index position of the order.
     *
     * @param index: The position of the Dish in the order.
     * @return The Dish at a given index
     */
    public Dish get(int index) {
        return this.dishes.get(index);
    }

    /**
     * Gets the Dish with a MenuScope
     *
     * @param scope: The MenuScope of the dish
     * @return The Dish with that MenuScope
     */
    public Dish get(MenuScope scope) {
        for (Dish dish : this.dishes) {
            if (dish.getScope().equals(scope)) {
                return dish;
            }
        }
        return null;
    }

    /**
     * Increments the quantity of a dish by an amount
     *
     * @param dish:   The Dish to which the quantity is incremented
     * @param amount: The quantity to increment
     */
    public void incrementQuantity(Dish dish, int amount) {
        if (amount < 0) {
            throw new RuntimeException("Invalid negative Incrementation!");
        }
        int index = this.indexOf(dish);
        this.dishes.get(index).incrementQuantity(amount);
    }

    /**
     * Increments the quantity of a dish with a MenuScope by an amount
     *
     * @param scope:  The MenuScope of the dish
     * @param amount: The quantity to increment
     */
    public void incrementQuantity(MenuScope scope, int amount) {
        if (amount < 0) {
            throw new RuntimeException("Invalid negative Incrementation!");
        }
        int index = this.indexOf(scope);
        this.dishes.get(index).incrementQuantity(amount);
    }

    /**
     * Decrements the quantity of a dish by an amount
     *
     * @param dish:   The Dish to which the quantity is incremented
     * @param amount: The quantity to increment
     */
    public void decrementQuantity(Dish dish, int amount) {
        int index = this.indexOf(dish);
        Dish d = this.dishes.get(index);
        d.decrementQuantity(amount);
        if (d.getQuantity() <= 0) {
            this.remove(d);
        }
    }

    /**
     * Decrements the quantity of a dish with a MenuScope by an amount
     *
     * @param scope:  The MenuScope of the dish
     * @param amount: The quantity to decrement
     */
    public void decrementQuantity(MenuScope scope, int amount) {
        int index = this.indexOf(scope);
        Dish d = this.dishes.get(index);
        d.decrementQuantity(amount);
        if (d.getQuantity() <= 0) {
            this.remove(d);
        }
    }

    /**
     * Cooks 1 Dish with the given MenuScope
     *
     * @param scope: The MenuScope of the Dish
     */
    public void cook(MenuScope scope) {
        decrementQuantity(scope, 1);
        for (Dish cookedDish : this.cookedDishes) {
            if (cookedDish.getScope().equals(scope)) {
                cookedDish.incrementQuantity(1);
                return;
            }
        }
        this.cookedDishes.add(new Dish(scope, 1));
    }

    /**
     * Cooks 1 Dish
     *
     * @param dish: The Dish to cook
     */
    public void cook(Dish dish) {
        decrementQuantity(dish, 1);
        for (Dish cookedDish : this.cookedDishes) {
            if (cookedDish.getScope().equals(dish.getScope())) {
                cookedDish.incrementQuantity(1);
                return;
            }
        }
        this.cookedDishes.add(new Dish(dish.getScope(), 1));
    }

    public int entries() {
        return this.dishes.size();
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public ArrayList<Dish> getCookedDishes() {
        return cookedDishes;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * Gets the total spent by the table.
     * It sums up all the costs of the cooked dishes
     *
     * @return The total spent
     */
    public int getTotal() {
        int total = 0;
        for (Dish dish : this.cookedDishes) {
            int cost = dish.getScope().getCost() * dish.getQuantity();
            total += cost;
        }
        return total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "tableNumber=" + tableNumber +
                ", dishes=" + dishes +
                ", cookedDishes=" + cookedDishes +
                '}';
    }

}
