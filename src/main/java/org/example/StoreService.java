package org.example;

import java.util.ArrayList;
import java.util.List;

public class StoreService {
    private List<Product> products;
    private Warehouse warehouse;
    private List<Check> checks;

    public StoreService() {
        this.products = new ArrayList<>();
        this.warehouse = new Warehouse();
        this.checks = new ArrayList<>();
    }

    public void addProductAmount(Product product, int quantity) {
        warehouse.addProduct(product, quantity);
    }

    public void addNewProduct(String name, double cost) {
        Product product = new Product(name, cost);
        products.add(product);
        warehouse.addProduct(product, 0);
    }

    public void deleteProduct(Product product) {
        products.remove(product);
        warehouse.removeProduct(product);
    }

    public Product getProduct(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public Check createCheck(List<SaleRequest> saleRequests) {
        Check check = new Check();
        for (SaleRequest request : saleRequests) {
            if (warehouse.hasProduct(request.product, request.quantity)) {
                Sale sale = new Sale(request.product, request.quantity);
                check.addSale(sale);
                warehouse.reduceProduct(request.product, request.quantity);
            } else {
                throw new IllegalArgumentException("Not enough " + request.product.getName() + " in stock.");
            }
        }
        check.calculateTotal();
        checks.add(check);
        return check;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StoreService:\n");
        sb.append(warehouse).append("\n");
        sb.append("Checks:\n");
        for (Check check : checks) {
            sb.append(check).append("\n");
        }
        return sb.toString();
    }

    public int getProductNumber(String product) {
        return warehouse.getProductNumber(getProduct(product));
    }
    public int getProductsNumber() {
        return products.size();
    }


    public static class SaleRequest {
        Product product;
        int quantity;

        public SaleRequest(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
    }
}
