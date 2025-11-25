package OrderMenu;

class Appetizer extends MenuItem {
    public Appetizer(String name, double price) {
        super(name, price, "OrderMenu.Appetizer");
    }

    @Override
    public String getDescription() {
        return "OrderMenu.Appetizer: " + name;
    }
}