package OrderMenu;

class Beverage extends MenuItem {
    public Beverage(String name, double price) {
        super(name, price, "Beverage");
    }

    @Override
    public String getDescription() {
        return "Beverage: " + name;
    }
}