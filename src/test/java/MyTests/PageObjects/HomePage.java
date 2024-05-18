package MyTests.PageObjects;

import MyTests.LogUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    private WebDriver driver;

    // Locators
    private By searchButton = By.xpath("//*[@id=\"shopify-section-header\"]/div[3]/div[2]/div/div/header/div[1]/div/div[3]/div/div/a[2]");
    private By accountButton = By.xpath("//*[@id=\"shopify-section-header\"]/div[3]/div[2]/div/div/header/div[1]/div/div[3]/div/div/a[1]");
//    private By collectionsLink = By.linkText("Collections");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://www.petstore.com/");
    }

    // Methods
    public void clickSearchButton() {
        driver.get("https://www.petstore.com/");
        driver.findElement(searchButton).click();
        LogUtility.log("Clicked on the search button.");
    }

    public void clickAccountButton() {
        driver.get("https://www.petstore.com/");
        driver.findElement(accountButton).click();
    }

//    public void navigateToCollections() {
//        driver.findElement(collectionsLink).click();
//    }
}
