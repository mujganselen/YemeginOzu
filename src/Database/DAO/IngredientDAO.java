package Database.DAO;
import Database.DatabaseConnection;

import Model.Ingredient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {
    private Connection connection;

    public IngredientDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT * FROM ingredients";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Ingredient ingredient = new Ingredient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("extra_price"),
                        rs.getBoolean("is_removable")
                );
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    public List<Ingredient> getIngredientsForMenuItem(int menuItemId) {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT i.* FROM ingredients i " +
                "JOIN menu_item_ingredients mii ON i.id = mii.ingredient_id " +
                "WHERE mii.menu_item_id = ? AND mii.is_default = TRUE";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, menuItemId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Ingredient ingredient = new Ingredient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("extra_price"),
                        rs.getBoolean("is_removable")
                );
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }
}

