package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class StoreServiceJUnitTest {

    private StoreService storeService;

    @BeforeEach
    void setUp() {
        storeService = new StoreService();
        storeService.addNewProduct("Apple", 0.5);
        storeService.addNewProduct("Banana", 0.3);
        storeService.addProductAmount(storeService.getProduct("Apple"), 1);
        storeService.addProductAmount(storeService.getProduct("Banana"), 150);
    }

    @Test
    @DisplayName("Test createCheck with wrong sale method")
    void testCreateCheck() {
        assumeFalse(storeService == null);
        assumeFalse(storeService.getProduct("Apple") == null);
        assumeFalse(storeService.getProduct("Banana") == null);
        assumeTrue(storeService.getProductNumber("Apple") < 5);
        StoreService.SaleRequest saleRequest1 = new StoreService.SaleRequest(storeService.getProduct("Apple"), 5);
        StoreService.SaleRequest saleRequest2 = new StoreService.SaleRequest(storeService.getProduct("Banana"), 10);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            storeService.createCheck(List.of(saleRequest1, saleRequest2));
        });
        assertThat(exception).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("Parametrized test for addProductAmount method with Assumptions and AssertJ")
    @CsvSource({
            "Apple, 50",
            "Banana, 100",
            "Apple, 200"
    })
    void testParametrizedAddProductAmount(String productName, int quantity) {
        assumeFalse(storeService.getProduct(productName) == null);
        assumeTrue(quantity > 0);
        int initialStock = storeService.getProductNumber(productName);
        storeService.addProductAmount(storeService.getProduct(productName), quantity);
        assertThat(storeService.getProductNumber(productName)).isEqualTo(initialStock + quantity);
    }

}
