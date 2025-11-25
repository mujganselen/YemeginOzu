package OrderMenu;

class MenuItemFactory {
    public static MenuItem createMenuItem(String category, String name, double price) {
        switch (category.toLowerCase()) {
            case "maindish":
                return new MainDish(name, price);
            case "beverage":
                return new Beverage(name, price);
            case "dessert":
                return new Dessert(name, price);
            case "appetizer":
                return new Appetizer(name, price);
            default:
                throw new IllegalArgumentException("Unknown: " + category);
        }
    }
}