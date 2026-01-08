package Database.DAO;
import Database.DatabaseConnection;

import Model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories ORDER BY display_order";

        Connection connection = DatabaseConnection.getInstance().getConnection();

        if (connection == null) {
            throw new IllegalStateException(
                    "Database connection is NULL. Check URL/DB name, MySQL service, mysql-connector-j."
            );
        }

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("display_order")
                );
                categories.add(category);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to load categories: " + e.getMessage(), e);
        }

        return categories;
    }

    public Category getCategoryById(int id) {
        String query = "SELECT * FROM categories WHERE id = ?";

        Connection connection = DatabaseConnection.getInstance().getConnection();
        if (connection == null) {
            throw new IllegalStateException(
                    "Database connection is NULL. Check URL/DB name, MySQL service, mysql-connector-j."
            );
        }

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Category(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("display_order")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to load category by id: " + e.getMessage(), e);
        }

        return null;
    }
}
