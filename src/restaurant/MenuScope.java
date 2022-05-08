package restaurant;

public class MenuScope {

    /**
     * Parse a string into a MenuScope
     *
     * @param ln the string to parse
     */
    public static MenuScope fromText(String ln) {
        String[] split = ln.split(";");
        int id = Integer.parseInt(split[0]);
        String name = split[1];
        int cost = Integer.parseInt(split[2]);
        return new MenuScope(id, name, cost);
    }


    /**
     * Convert a MenuScope into a string
     *
     * @param scope The MenuScope to convert
     */
    public static String toText(MenuScope scope) {
        return scope.id + ";" + scope.name + ";" + scope.cost;
    }

    private final int id;
    private String name;

    private int cost;

    /**
     * Create a new MenuScope with his unique id, name and his cost
     *
     * @param id   The unique identifier of this MenuScope
     * @param name The name of this MenuScope
     * @param cost The cost of this MenuScope
     */
    public MenuScope(int id, String name, int cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "MenuScope{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MenuScope) {
            return ((MenuScope) obj).id == this.id;
        }
        return false;
    }
}
