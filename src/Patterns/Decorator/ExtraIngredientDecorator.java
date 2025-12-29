package Patterns.Decorator;

import Model.*;

public class ExtraIngredientDecorator extends OrderItemDecorator {
    private Ingredient ingredient;

    public ExtraIngredientDecorator(OrderItem orderItem, Ingredient ingredient) {
        super(orderItem);
        this.ingredient = ingredient;
        orderItem.addIngredient(ingredient);
    }

    @Override
    public double getPrice() {
        return orderItem.getTotalPrice();
    }

    @Override
    public String getDescription() {
        return orderItem.getMenuItem().getName() + " + " + ingredient.getName();
    }
}

