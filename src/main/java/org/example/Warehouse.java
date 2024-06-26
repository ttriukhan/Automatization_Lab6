package org.example;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private Map<Product, Integer> stock;

    public Warehouse() {
        this.stock = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        stock.merge(product, quantity, Integer::sum);
    }

    public void reduceProduct(Product product, int quantity) {
        int currentQuantity = stock.getOrDefault(product, 0);
        stock.put(product, currentQuantity - quantity);
    }

    public boolean hasProduct(Product product, int quantity) {
        return stock.getOrDefault(product, 0) >= quantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Warehouse:\n");
        for (Map.Entry<Product, Integer> entry : stock.entrySet()) {
            sb.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    public void removeProduct(Product product) {
        stock.remove(product);
    }

    public int getProductNumber(Product product) {
        return stock.get(product);
    }
}
