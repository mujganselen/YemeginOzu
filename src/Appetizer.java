class Appetizer extends MenuItem {
    public Appetizer(String name, double price) {
        super(name, price, "Appetizer");
    }

    @Override
    public String getDescription() {
        return "Appetizer: " + name;
    }
}