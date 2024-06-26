package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SeleniumTests {
    private WebDriver driver;

    @BeforeAll
    static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get("https://rozetka.com.ua/ua/");
    }

    @Test
    void testPresenceOfCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement miniGuide = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("header-cart__button")));
        assertNotNull(miniGuide, "Cart was not found");
        assertTrue(miniGuide.isDisplayed(), "Cart is not displayed");
    }

    @Test
    void testSearchPresence() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("search")));
        assertNotNull(search, "Search not found");
        assertTrue(search.isDisplayed(), "Search is not displayed");
    }

    @Test
    public void testSearch() {
        WebElement searchBar = driver.findElement(By.name("search"));
        String searchQuery = "laptop";
        searchBar.clear();
        searchBar.sendKeys(searchQuery);
        searchBar.sendKeys(Keys.RETURN);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> searchResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("goods-tile")));
        assertFalse(searchResults.isEmpty(), "No search results found.");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
