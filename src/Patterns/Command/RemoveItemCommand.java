package Patterns.Command;

import Model.*;

public class RemoveItemCommand implements Command {
    private Order order;
    private OrderItem item;

    public RemoveItemCommand(Order order, OrderItem item) {
        this.order = order;
        this.item = item;
    }

    @Override
    public void execute() {
        order.removeItem(item);
    }

    @Override
    public void undo() {
        order.addItem(item);
    }
}