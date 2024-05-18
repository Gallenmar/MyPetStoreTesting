package MyTests.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage {
    private WebDriver driver;

    // Locators
    private By addToCartButton = By.className("add-to-cart");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public void clickAddToCart() {
        driver.findElement(addToCartButton).click();
    }
}
