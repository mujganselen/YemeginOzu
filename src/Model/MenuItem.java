package Model;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private int id;
    private String name;
    private String description;
    private double basePrice;
    private Category category;
    private boolean available;
    private String imagePath;
    private List<Ingredient> defaultIngredients;

    public MenuItem() {
        this.defaultIngredients = new ArrayList<>();
    }

    public MenuItem(int id, String name, String description, double basePrice, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.category = category;
        this.available = true;
        this.defaultIngredients = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public List<Ingredient> getDefaultIngredients() { return defaultIngredients; }
    public void setDefaultIngredients(List<Ingredient> ingredients) {
        this.defaultIngredients = ingredients;
    }
    public void addDefaultIngredient(Ingredient ingredient) {
        this.defaultIngredients.add(ingredient);
    }

    @Override
    public String toString() {
        return name + " - " + basePrice + " TL";
    }
}