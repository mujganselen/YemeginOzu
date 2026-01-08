// src/Database/DatabaseConnection.java
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/yemeginozu_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Kartal3400.";

    private DatabaseConnection() {
        openConnection();
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) instance = new DatabaseConnection();
        return instance;
    }

    private void openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Database connection established successfully!");
        } catch (ClassNotFoundException e) {
            this.connection = null;
            System.err.println(" MySQL Driver not found! Add mysql-connector-j to classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            this.connection = null;
            System.err.println(" Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                openConnection();
            }
        } catch (SQLException e) {
            System.err.println(" Connection check failed: " + e.getMessage());
            openConnection();
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println(" Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println(" Error closing connection: " + e.getMessage());
        }
    }
}
