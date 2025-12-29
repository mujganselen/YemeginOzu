package Patterns.Command;

import Model.*;

public class AddItemCommand implements Command {
    private Order order;
    private OrderItem item;

    public AddItemCommand(Order order, OrderItem item) {
        this.order = order;
        this.item = item;
    }

    @Override
    public void execute() {
        order.addItem(item);
    }

    @Override
    public void undo() {
        order.removeItem(item);
    }
}
