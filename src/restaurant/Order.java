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
     * TODO Doc
     */
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

    public void add(MenuScope scope) {
        this.add(scope, 1);
    }

    public void add(MenuScope scope, int quantity) {
        this.dishes.add(new Dish(scope, quantity));
    }

    public void add(Dish dish) {
        this.dishes.add(dish);
    }

    public void addCooked(Dish dish) {
        for (Dish d : this.cookedDishes) {
            if (d.getScope().equals(dish.getScope())) {
                d.incrementQuantity(dish.getQuantity());
                return;
            }
        }
        this.cookedDishes.add(dish);
    }

    public void remove(MenuScope scope) {
        for (int i = 0; i < this.entries(); i++) {
            Dish dish = this.dishes.get(i);
            if (dish.getScope().equals(scope)) {
                this.dishes.remove(dish);
                return;
            }
        }
    }

    public void remove(Dish dish) {
        this.dishes.remove(this.indexOf(dish));
    }

    public int indexOf(MenuScope scope) {
        for (int i = 0; i < this.entries(); i++) {
            Dish dish = this.dishes.get(i);
            if (dish.getScope().equals(scope)) {
                return i;
            }
        }
        return -1;
    }

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

    public Dish get(int index) {
        return this.dishes.get(index);
    }

    public Dish get(MenuScope scope) {
        for (Dish dish : this.dishes) {
            if (dish.getScope().equals(scope)) {
                return dish;
            }
        }
        return null;
    }

    public void incrementQuantity(Dish dish, int amount) {
        if (amount < 0) {
            throw new RuntimeException("Invalid negative Incrementation!");
        }
        int index = this.indexOf(dish);
        this.dishes.get(index).incrementQuantity(amount);
    }

    public void incrementQuantity(MenuScope scope, int amount) {
        if (amount < 0) {
            throw new RuntimeException("Invalid negative Incrementation!");
        }
        int index = this.indexOf(scope);
        this.dishes.get(index).incrementQuantity(amount);
    }

    public void decrementQuantity(Dish dish, int amount) {
        int index = this.indexOf(dish);
        Dish d = this.dishes.get(index);
        d.decrementQuantity(amount);
        if (d.getQuantity() <= 0) {
            this.remove(d);
        }
    }

    public void decrementQuantity(MenuScope scope, int amount) {
        int index = this.indexOf(scope);
        Dish d = this.dishes.get(index);
        d.decrementQuantity(amount);
        if (d.getQuantity() <= 0) {
            this.remove(d);
        }
    }

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
