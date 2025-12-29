package Patterns.Factory;

import Model.*;

public abstract class MenuItemFactory {
    public abstract MenuItem createMenuItem(String name, String description, double price);

    protected Category getCategory() {
        return null; // Override in subclasses
    }
}