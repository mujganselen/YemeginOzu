import java.util.ArrayList;
import java.util.List;

abstract class MenuItem {
    protected String name;
    protected double basePrice;
    protected String category;
    protected List<String> ingredients;

    public MenuItem(String name, double basePrice, String category) {
        this.name = name;
        this.basePrice = basePrice;
        this.category = category;
        this.ingredients = new ArrayList<>();
    }

    public String getName() { return name; }
    public double getBasePrice() { return basePrice; }
    public String getCategory() { return category; }
    public List<String> getIngredients() { return new ArrayList<>(ingredients); }

    public abstract String getDescription();
}