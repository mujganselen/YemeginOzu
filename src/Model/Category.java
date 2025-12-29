package Model;

public class Category {
    private int id;
    private String name;
    private String description;
    private int displayOrder;

    public Category() {}

    public Category(int id, String name, String description, int displayOrder) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.displayOrder = displayOrder;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    @Override
    public String toString() {
        return name;
    }
}