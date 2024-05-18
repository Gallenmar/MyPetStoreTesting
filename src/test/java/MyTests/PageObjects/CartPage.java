package MyTests.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CartPage {
    private WebDriver driver;

    // Locators
    private By cartItems = By.className("ajaxcart__row");
    private By cartItemName = By.xpath("//*[@id=\"CartContainer\"]/div[1]/div/div/div/div[2]/div[1]/a");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public int getCartItemCount() {
        return driver.findElements(By.className("ajaxcart__row")).size();
    }

    public String getFirstCartItemName() throws InterruptedException {
        Thread.sleep(2000);
        return driver.findElement(cartItemName).getText();
    }
}
