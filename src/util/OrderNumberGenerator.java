package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderNumberGenerator {
    private static OrderNumberGenerator instance;
    private AtomicInteger counter;
    private DateTimeFormatter formatter;

    private OrderNumberGenerator() {
        this.counter = new AtomicInteger(1);
        this.formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    }

    public static synchronized OrderNumberGenerator getInstance() {
        if (instance == null) {
            instance = new OrderNumberGenerator();
        }
        return instance;
    }

    public String generateOrderNumber() {
        String date = LocalDateTime.now().format(formatter);
        int orderNum = counter.getAndIncrement();
        return String.format("ORD-%s-%04d", date, orderNum);
    }

    public void resetCounter() {
        counter.set(1);
    }
}
