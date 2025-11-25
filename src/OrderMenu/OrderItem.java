package OrderMenu;
import java.util.List;
import java.util.ArrayList;

class OrderItem {
    private MenuItem menuItem;
    private int quantity;
    private List<String> addedIngredients;
    private List<String> removedIngredients;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.addedIngredients = new ArrayList<>();
        this.removedIngredients = new ArrayList<>();
    }

    public MenuItem getMenuItem() { return menuItem; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public List<String> getAddedIngredients() { return addedIngredients; }
    public List<String> getRemovedIngredients() { return removedIngredients; }

    public void addIngredient(String ingredient) {
        addedIngredients.add(ingredient);
    }

    public void removeIngredient(String ingredient) {
        removedIngredients.add(ingredient);
    }

    public double getTotalPrice() {
        double price = menuItem.getBasePrice() * quantity;
        price += addedIngredients.size() * 20.0;
        return price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(menuItem.getName()).append(" x").append(quantity);
        if (!addedIngredients.isEmpty()) {
            sb.append(" [+").append(String.join(", ", addedIngredients)).append("]");
        }
        if (!removedIngredients.isEmpty()) {
            sb.append(" [-").append(String.join(", ", removedIngredients)).append("]");
        }
        return sb.toString();
    }
}
