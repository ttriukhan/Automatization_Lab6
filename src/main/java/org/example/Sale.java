package org.example;

class Sale {
    Product product;
    int number;
    double total;

    public Sale(Product product, int number) {
        this.product = product;
        this.number = number;
        this.total = product.getCost() * number;
    }

    @Override
    public String toString() {
        return product.getName() + " x " + number + " = " + total;
    }
}
