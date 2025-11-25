package OrderMenu;

class MainDish extends MenuItem {
    public MainDish(String name, double price) {
        super(name, price, "Main Dish");
    }

    @Override
    public String getDescription() {
        return "Main Dish " + name;
    }
}