package Database.DAO;

import Database.DatabaseConnection;
import Model.Category;
import OrderMenu.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {
    private Connection connection;
    private CategoryDAO categoryDAO;
    private IngredientDAO ingredientDAO;

    public MenuItemDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.categoryDAO = new CategoryDAO();
        this.ingredientDAO = new IngredientDAO();
    }

    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        String query = "SELECT * FROM menu_items WHERE is_available = TRUE";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                MenuItem item = createMenuItemFromResultSet(rs);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<MenuItem> getMenuItemsByCategory(int categoryId) {
        List<MenuItem> items = new ArrayList<>();
        String query = "SELECT * FROM menu_items WHERE category_id = ? AND is_available = TRUE";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                MenuItem item = createMenuItemFromResultSet(rs);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    private MenuItem createMenuItemFromResultSet(ResultSet rs) throws SQLException {
        Category category = categoryDAO.getCategoryById(rs.getInt("category_id"));
        MenuItem item = new MenuItem(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("base_price"),
                category
        );
        item.setAvailable(rs.getBoolean("is_available"));
        item.setImagePath(rs.getString("image_path"));

        // Load default ingredients
        item.setDefaultIngredients(ingredientDAO.getIngredientsForMenuItem(item.getId()));

        return item;
    }
}