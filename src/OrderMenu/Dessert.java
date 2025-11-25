package OrderMenu;

class Dessert extends MenuItem {
    public Dessert(String name, double price) {
        super(name, price, "OrderMenu.Dessert");
    }

    @Override
    public String getDescription() {
        return "OrderMenu.Dessert: " + name;
    }
}