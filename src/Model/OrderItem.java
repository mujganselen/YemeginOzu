package Model;
import java.util.ArrayList;
import java.util.List;

public class OrderItem {
    private int id;
    private MenuItem menuItem;
    private int quantity;
    private double unitPrice;
    private List<Ingredient> addedIngredients;
    private List<Ingredient> removedIngredients;
    private String customizationNote;

    public OrderItem(MenuItem menuItem) {
        this.menuItem = menuItem;
        this.quantity = 1;
        this.unitPrice = menuItem.getBasePrice();
        this.addedIngredients = new ArrayList<>();
        this.removedIngredients = new ArrayList<>();
    }

    public double getTotalPrice() {
        double total = unitPrice * quantity;
        for (Ingredient ing : addedIngredients) {
            total += ing.getExtraPrice() * quantity;
        }
        return total;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public MenuItem getMenuItem() { return menuItem; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void incrementQuantity() { this.quantity++; }
    public void decrementQuantity() { if (this.quantity > 1) this.quantity--; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public List<Ingredient> getAddedIngredients() { return addedIngredients; }
    public void addIngredient(Ingredient ingredient) {
        this.addedIngredients.add(ingredient);
    }

    public List<Ingredient> getRemovedIngredients() { return removedIngredients; }
    public void removeIngredient(Ingredient ingredient) {
        this.removedIngredients.add(ingredient);
    }

    public String getCustomizationNote() { return customizationNote; }
    public void setCustomizationNote(String note) { this.customizationNote = note; }
}
