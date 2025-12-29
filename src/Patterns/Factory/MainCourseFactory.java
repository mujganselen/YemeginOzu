package Patterns.Factory;

import Model.*;
public class MainCourseFactory extends MenuItemFactory {
    private Category category;

    public MainCourseFactory(Category category) {
        this.category = category;
    }

    @Override
    public MenuItem createMenuItem(String name, String description, double price) {
        return new MenuItem(0, name, description, price, category);
    }

    @Override
    protected Category getCategory() {
        return category;
    }
}
