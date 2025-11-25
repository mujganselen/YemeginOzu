package OrderMenu;

class Beverage extends MenuItem {
    public Beverage(String name, double price) {
        super(name, price, "OrderMenu.Beverage");
    }

    @Override
    public String getDescription() {
        return "OrderMenu.Beverage: " + name;
    }
}