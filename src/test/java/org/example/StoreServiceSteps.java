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

    @Given("the following products exist in warehouse:")
    public void the_following_products_exist_in_warehouse(List<Map<String, String>> products) {
        for (Map<String, String> product : products) {
            String name = product.get("Product");
            int amount = Integer.parseInt(product.get("Initial Amount"));
            storeService.addNewProduct(name, 0.0);
            storeService.addProductAmount(storeService.getProduct(name), amount);
        }
    }

    @When("I create check with the following sale requests:")
    public void i_create_check_with_the_following_sale_requests(List<Map<String, String>> saleRequests) {
        List<StoreService.SaleRequest> requests = convertToSaleRequests(saleRequests);
        storeService.createCheck(requests);
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

    private List<StoreService.SaleRequest> convertToSaleRequests(List<Map<String, String>> saleRequests) {
        List<StoreService.SaleRequest> requests = new ArrayList<>();
        for (Map<String, String> request : saleRequests) {
            String saleRequestString = request.get("Sale Requests");
            String[] parts = saleRequestString.split(", ");
            for (String part : parts) {
                String[] pair = part.split(":");
                String productName = pair[0];
                int amount = Integer.parseInt(pair[1]);
                requests.add(new StoreService.SaleRequest(storeService.getProduct(productName), amount));
            }
        }
        return requests;
    }
}
