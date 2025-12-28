package Patterns.Observer;

import OrderMenu.*;

public class KitchenObserver implements OrderObserver {
    private String kitchenName;

    public KitchenObserver(String kitchenName) {
        this.kitchenName = kitchenName;
    }

    @Override
    public void onOrderPlaced(Order order) {
        System.out.println("[" + kitchenName + "] YENİ SİPARİŞ: " + order.getOrderNumber());
        System.out.println("Sipariş Tipi: " + order.getOrderType());
        System.out.println("Ürün Sayısı: " + order.getItems().size());
        System.out.println("Toplam: " + order.getTotalPrice() + " TL");
        System.out.println("─────────────────────────────────");
    }

    @Override
    public void onOrderStatusChanged(Order order) {
        System.out.println("[" + kitchenName + "] SİPARİŞ DURUM DEĞİŞİMİ: " +
                order.getOrderNumber() + " -> " + order.getStatus());
    }
}
