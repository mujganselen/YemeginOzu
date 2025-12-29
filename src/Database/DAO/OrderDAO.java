package Database.DAO;

import Database.DatabaseConnection;
import Model.*;

import java.sql.*;

public class OrderDAO {
    private Connection connection;

    public OrderDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public boolean saveOrder(Order order) {
        String orderQuery = "INSERT INTO orders (order_number, order_type, total_price, status) VALUES (?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            // Insert order
            try (PreparedStatement pstmt = connection.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, order.getOrderNumber());
                pstmt.setString(2, order.getOrderType().name());
                pstmt.setDouble(3, order.getTotalPrice());
                pstmt.setString(4, order.getStatus().name());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);
                    order.setId(orderId);

                    // Insert order items
                    saveOrderItems(orderId, order.getItems());
                }
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveOrderItems(int orderId, java.util.List<OrderItem> items) throws SQLException {
        String itemQuery = "INSERT INTO order_items (order_id, menu_item_id, quantity, unit_price, customization_note) VALUES (?, ?, ?, ?, ?)";
        String customQuery = "INSERT INTO order_item_customizations (order_item_id, ingredient_id, action) VALUES (?, ?, ?)";

        try (PreparedStatement itemStmt = connection.prepareStatement(itemQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement customStmt = connection.prepareStatement(customQuery)) {

            for (OrderItem item : items) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, item.getMenuItem().getId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.setDouble(4, item.getUnitPrice());
                itemStmt.setString(5, item.getCustomizationNote());
                itemStmt.executeUpdate();

                ResultSet rs = itemStmt.getGeneratedKeys();
                if (rs.next()) {
                    int itemId = rs.getInt(1);

                    // Save customizations
                    for (Ingredient ing : item.getAddedIngredients()) {
                        customStmt.setInt(1, itemId);
                        customStmt.setInt(2, ing.getId());
                        customStmt.setString(3, "ADD");
                        customStmt.addBatch();
                    }

                    for (Ingredient ing : item.getRemovedIngredients()) {
                        customStmt.setInt(1, itemId);
                        customStmt.setInt(2, ing.getId());
                        customStmt.setString(3, "REMOVE");
                        customStmt.addBatch();
                    }

                    customStmt.executeBatch();
                }
            }
        }
    }
}
