package MyTests.PageObjects;

import MyTests.LogUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Instant;
import static MyTests.PSTesNGlog.wait;

public class AccountPage {
    private WebDriver driver;

    // Locators
    private By userInfo = By.xpath("//*[@id=\"MainContent\"]/div/div/div[2]/p[2]");
    private By logoutButton = By.xpath("//*[@id=\"MainContent\"]/div/header/a");
    private By errorElement = By.xpath("//div[@class='errors']//li");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public String getUserInfo() {
        driver.get("https://petstore.com/account");
        return driver.findElement(userInfo).getText();
    }

    public void clickLogout() {
        driver.get("https://petstore.com/account");
        driver.findElement(logoutButton).click();
    }
}
