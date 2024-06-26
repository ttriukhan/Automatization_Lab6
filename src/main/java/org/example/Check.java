package org.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Check {
    List<Sale> sales;
    Date date;
    double total;

    public Check() {
        this.sales = new ArrayList<>();
        this.date = new Date();
        this.total = 0;
    }

    public void addSale(Sale sale) {
        if (sale != null) {
            this.sales.add(sale);
            this.total += sale.total;
        }
    }

    public void calculateTotal() {
        this.total = 0;
        for (Sale sale : sales) {
            this.total += sale.total;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Check Date: ").append(date).append("\n");
        for (Sale sale : sales) {
            sb.append(sale).append("\n");
        }
        sb.append("Total: ").append(total);
        return sb.toString();
    }
}
