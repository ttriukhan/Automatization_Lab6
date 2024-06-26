package org.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class StoreServiceSteps {

    private final StoreService storeService = new StoreService();
    private double totalCost;

    @Given("the following products exist in warehouse:")
    public void the_following_products_exist_in_warehouse(List<Map<String, String>> products) {
        for (Map<String, String> product : products) {
            String name = product.get("Product");
            double cost = Double.parseDouble(product.get("Cost"));
            int amount = Integer.parseInt(product.get("Initial Amount"));
            storeService.addNewProduct(name, cost);
            storeService.addProductAmount(storeService.getProduct(name), amount);
        }
    }

    @When("I create check with the following sale requests:")
    public void i_create_check_with_the_following_sale_requests(List<Map<String, String>> saleRequests) {
        List<StoreService.SaleRequest> requests = new ArrayList<>();
        for (Map<String, String> sale : saleRequests) {
            String product = sale.get("Product");
            int amount = Integer.parseInt(sale.get("Amount"));
            requests.add(new StoreService.SaleRequest(storeService.getProduct(product), amount));
        }
        totalCost = storeService.createCheck(requests).total;
    }

    @Then("the warehouse should have the following amounts:")
    public void the_warehouse_should_have_the_following_amounts(List<Map<String, String>> expectedAmounts) {
        expectedAmounts.forEach(expected -> {
            String productName = expected.get("Product");
            int expectedAmount = Integer.parseInt(expected.get("Expected Amount"));
            int actualAmount = storeService.getProductNumber(productName);
            assertThat(actualAmount).isEqualTo(expectedAmount);
        });
    }

    @Then("check totalCost should be {double}")
    public void check_totalCost_should_be(double expectedTotalCost) {
        assertThat(totalCost).isEqualTo(expectedTotalCost);
    }
}
