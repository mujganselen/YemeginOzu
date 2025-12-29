package util;

public class ValidationUtils {

    public static boolean isValidPrice(double price) {
        return price >= 0 && price <= 10000;
    }

    public static boolean isValidQuantity(int quantity) {
        return quantity > 0 && quantity <= 99;
    }

    public static boolean isValidOrderNumber(String orderNumber) {
        return orderNumber != null &&
                !orderNumber.isEmpty() &&
                orderNumber.matches("ORD-\\d{8}-\\d{4}");
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public static boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        return phone.matches("^0?[5][0-9]{9}$");
    }
}
