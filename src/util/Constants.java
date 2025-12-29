package util;

import java.awt.Color;
import java.awt.Font;

public class Constants {
    // Colors
    public static final Color PRIMARY_COLOR = new Color(255, 140, 0); // Orange
    public static final Color SECONDARY_COLOR = new Color(34, 139, 34); // Green
    public static final Color DANGER_COLOR = new Color(220, 53, 69); // Red
    public static final Color BACKGROUND_COLOR = new Color(255, 250, 240); // Cream
    public static final Color TEXT_COLOR = new Color(139, 69, 19); // Brown

    // Fonts
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 32);
    public static final Font SUBTITLE_FONT = new Font("Arial", Font.BOLD, 24);
    public static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 16);
    public static final Font SMALL_FONT = new Font("Arial", Font.PLAIN, 12);

    // Sizes
    public static final int BUTTON_HEIGHT = 50;
    public static final int LARGE_BUTTON_WIDTH = 250;
    public static final int MEDIUM_BUTTON_WIDTH = 180;

    // Messages
    public static final String APP_TITLE = "YemeğinÖzü - Restoran Sipariş Sistemi";
    public static final String EMPTY_CART_MESSAGE = "Sepetiniz boş! Lütfen ürün ekleyin.";
    public static final String ORDER_SUCCESS_MESSAGE = "Siparişiniz başarıyla alındı!";
    public static final String ORDER_ERROR_MESSAGE = "Sipariş kaydedilemedi. Lütfen tekrar deneyin.";

    // Database
    public static final String DB_URL = "jdbc:mysql://localhost:3306/yemeginozu_db";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "your_password"; // CHANGE THIS
}