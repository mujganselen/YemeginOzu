package util;

import java.text.DecimalFormat;

public class PriceFormatter {
    private static final DecimalFormat formatter = new DecimalFormat("#,##0.00");

    public static String format(double price) {
        return formatter.format(price) + " TL";
    }

    public static String formatWithCurrency(double price, String currency) {
        return formatter.format(price) + " " + currency;
    }

    public static double parsePrice(String priceString) {
        try {
            String cleanPrice = priceString.replace("TL", "")
                    .replace(",", "")
                    .trim();
            return Double.parseDouble(cleanPrice);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}