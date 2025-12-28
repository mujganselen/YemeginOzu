package Model;

public class Ingredient {
    private int id;
    private String name;
    private double extraPrice;
    private boolean removable;

    public Ingredient() {}

    public Ingredient(int id, String name, double extraPrice, boolean removable) {
        this.id = id;
        this.name = name;
        this.extraPrice = extraPrice;
        this.removable = removable;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getExtraPrice() { return extraPrice; }
    public void setExtraPrice(double extraPrice) { this.extraPrice = extraPrice; }

    public boolean isRemovable() { return removable; }
    public void setRemovable(boolean removable) { this.removable = removable; }

    @Override
    public String toString() {
        return name + (extraPrice > 0 ? " (+" + extraPrice + " TL)" : "");
    }
}
