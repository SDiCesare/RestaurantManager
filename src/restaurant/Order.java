package restaurant;

import java.util.LinkedList;

public class Order {

    private int tableNumber;
    private LinkedList<Dish> dishes;
    private LinkedList<Dish> cookedDishes;

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
        this.cookedDishes = new LinkedList<>();
    }

    /**
     * Cooks a single {@link MenuScope MenuScope} in this order.
     *
     * @param scope The MenuScope cooked
     */
    public void cook(MenuScope scope) {
        this.cook(scope, 1);
    }

    /**
     * Cooks a certain quantity of {@link MenuScope MenuScope} in this order.
     *
     * @param scope    The MenuScope cooked.
     * @param quantity The quantity of scopes cooked.
     */
    public void cook(MenuScope scope, int quantity) {
        if (!this.contains(scope)) {
            return;
        }
        this.decrementDishQuantity(scope, quantity);
        for (Dish dish : this.cookedDishes) {
            if (dish.getScope().equals(scope)) {
                dish.incrementQuantity(quantity);
                return;
            }
        }
        this.cookedDishes.add(new Dish(scope, quantity));
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

    public Dish getCooked(MenuScope scope) {
        for (Dish dish : this.cookedDishes) {
            if (dish.getScope().equals(scope)) {
                return dish;
            }
        }
        return null;
    }

    public int indexOf(MenuScope scope) {
        for (int i = 0; i < this.dishes.size(); i++) {
            if (this.dishes.get(i).getScope().equals(scope)) {
                return i;
            }
        }
        return -1;
    }

    public LinkedList<Dish> getDishes() {
        return dishes;
    }

    public LinkedList<Dish> getCookedDishes() {
        return cookedDishes;
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
     *
     * @param dish The dish to add
     */
    public void add(Dish dish) {
        if (!this.dishes.contains(dish)) {
            this.dishes.add(dish);
        }
    }

    public void addCooked(Dish dish) {
        if (!this.cookedDishes.contains(dish)) {
            this.cookedDishes.add(dish);
        }
    }

    /**
     * Decrements by 1 the quantity associated with a {@link MenuScope MenuScope} on this order.
     *
     * @param scope The MenuScope to which the quantity is decremented
     */
    public void decrementDishQuantity(MenuScope scope) {
        this.decrementDishQuantity(scope, 1);
    }

    /**
     * Decrements by a certain amount the quantity associated with a {@link MenuScope MenuScope} on this order.
     *
     * @param scope    The MenuScope to which the quantity is decremented.
     * @param quantity The amount to decrement
     */
    public void decrementDishQuantity(MenuScope scope, int quantity) {
        this.incrementDishQuantity(scope, -quantity);
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

    public void incrementQuantity(Dish dish, int amount) {
        int index = this.dishes.indexOf(dish);
        this.dishes.get(index).incrementQuantity(amount);
    }

    public boolean contains(Dish dish) {
        return this.dishes.contains(dish);
    }

    public boolean contains(MenuScope scope) {
        for (Dish dish : this.dishes) {
            if (dish.getScope().equals(scope)) {
                return true;
            }
        }
        return false;
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


    public void combine(Order order) {
        for (Dish dish : order.dishes) {
            if (this.contains(dish)) {
                this.incrementQuantity(dish, dish.getQuantity());
            } else {
                this.dishes.add(dish);
            }
        }
        for (Dish dish : order.cookedDishes) {
            if (this.cookedDishes.contains(dish)) {
                this.cook(dish.getScope(), dish.getQuantity());
            } else {
                this.cookedDishes.add(dish);
            }
        }
    }

    /**
     * Removes a {@link Dish Dish} from this order
     *
     * @param dish The dish to remove
     */
    public void remove(Dish dish) {
        this.dishes.remove(dish);
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int entries() {
        return this.dishes.size();
    }

    @Override
    public String toString() {
        return "Order{" +
                "tableNumber=" + tableNumber +
                ", dishes=" + dishes +
                '}';
    }

}
