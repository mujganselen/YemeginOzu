
import controller.*;
import OrderMenu.*;
import model.*;
import patterns.strategy.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════");
        System.out.println("🍽️  YEMEĞİNÖZÜ - Restaurant Management System");
        System.out.println("════════════════════════════════════════\n");

        // Test 1: Database Connection
        testDatabaseConnection();

        // Test 2: Menu Loading
        testMenuLoading();

        // Test 3: Order Creation
        testOrderCreation();

        // Test 4: Strategy Pattern
        testPricingStrategies();

        System.out.println("\n✅ TÜM TESTLER TAMAMLANDI!");
        System.out.println("════════════════════════════════════════");
    }

    private static void testDatabaseConnection() {
        System.out.println("🔍 TEST 1: Database Connection");
        System.out.println("─────────────────────────────────────");

        RestaurantController controller = new RestaurantController();
        List<Category> categories = controller.getAllCategories();

        if (categories.isEmpty()) {
            System.out.println("⚠️  Uyarı: Kategoriler yüklenemedi!");
            System.out.println("   Database schema çalıştırıldı mı?");
        } else {
            System.out.println("✅ " + categories.size() + " kategori yüklendi:");
            for (Category cat : categories) {
                System.out.println("   - " + cat.getName());
            }
        }
        System.out.println();
    }

    private static void testMenuLoading() {
        System.out.println("🔍 TEST 2: Menu Loading");
        System.out.println("─────────────────────────────────────");

        RestaurantController controller = new RestaurantController();
        List<MenuItem> items = controller.getAllMenuItems();

        if (items.isEmpty()) {
            System.out.println("⚠️  Uyarı: Menü yüklenemedi!");
        } else {
            System.out.println("✅ " + items.size() + " ürün yüklendi:");
            for (MenuItem item : items) {
                System.out.println("   - " + item.getName() +
                        " (" + item.getCategory() + ") - " +
                        item.getBasePrice() + " TL");
            }
        }
        System.out.println();
    }

    private static void testOrderCreation() {
        System.out.println("🔍 TEST 3: Order Creation");
        System.out.println("─────────────────────────────────────");

        // SENİN OrderBuilder pattern'ini kullanıyoruz!
        OrderBuilder builder = new OrderBuilder();
        builder.setServiceType("dine-in");

        // Dummy menu items
        MenuItem pizza = MenuItemFactory.createMenuItem("maindish", "Pizza Margherita", 120.0);
        MenuItem cola = MenuItemFactory.createMenuItem("beverage", "Kola", 15.0);

        builder.addMenuItem(pizza, 2);
        builder.addIngredient("Peynir");
        builder.addMenuItem(cola, 1);

        Order order = builder.build();

        System.out.println("✅ Sipariş oluşturuldu:");
        System.out.println("   Sipariş No: " + order.getOrderId());
        System.out.println("   Servis Tipi: " + order.getServiceType());
        System.out.println("   Ürün Sayısı: " + order.getItems().size());
        System.out.println("   Toplam: " + order.getTotalPrice() + " TL");
        System.out.println("\n   Detaylar:");
        for (OrderItem item : order.getItems()) {
            System.out.println("   - " + item);
        }
        System.out.println();
    }

    private static void testPricingStrategies() {
        System.out.println("🔍 TEST 4: Pricing Strategies");
        System.out.println("─────────────────────────────────────");

        // Dummy order
        OrderBuilder builder = new OrderBuilder();
        MenuItem pizza = MenuItemFactory.createMenuItem("maindish", "Pizza", 100.0);
        builder.addMenuItem(pizza, 1);
        Order order = builder.build();

        double originalPrice = order.getTotalPrice();
        System.out.println("   Orijinal Fiyat: " + originalPrice + " TL");

        // Test strategies
        PricingStrategy regular = new RegularPricing();
        System.out.println("   " + regular.getStrategyName() + ": " +
                regular.calculatePrice(order) + " TL");

        PricingStrategy discount10 = new DiscountPricing(10);
        System.out.println("   " + discount10.getStrategyName() + ": " +
                discount10.calculatePrice(order) + " TL");

        PricingStrategy discount20 = new DiscountPricing(20);
        System.out.println("   " + discount20.getStrategyName() + ": " +
                discount20.calculatePrice(order) + " TL");

        PricingStrategy happyHour = new HappyHourPricing();
        System.out.println("   " + happyHour.getStrategyName() + ": " +
                happyHour.calculatePrice(order) + " TL");
        System.out.println();
    }
}
