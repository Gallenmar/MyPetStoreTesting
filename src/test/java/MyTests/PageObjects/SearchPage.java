package MyTests.PageObjects;

import MyTests.LogUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchPage {
    private WebDriver driver;

    // Locators
    private By searchBox = By.xpath("//*[@id=\"HeaderSearchForm\"]/input[2]");
    private By firstResult = By.xpath("//*[@id=\"MainContent\"]/div/div/div/div/div[2]/div/div/a/div[2]/div[1]");

    public SearchPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public void enterSearchQuery(String query) {
        WebElement searchBoxElement = driver.findElement(searchBox);
        searchBoxElement.sendKeys(query);
        searchBoxElement.sendKeys(Keys.ENTER);
        LogUtility.log("Search submitted for: " + query);
    }

    public String getFirstResultText() {
        return driver.findElement(firstResult).getText();
    }

    public void clickFirstResult(){
        driver.findElement(firstResult).click();
    }
}
