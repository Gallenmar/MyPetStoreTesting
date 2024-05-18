package MyTests.PageObjects;

import MyTests.LogUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class BedsCollection {
    private WebDriver driver;

    // Locators
    private By firstEl = By.xpath("//*[@id=\"CollectionSection\"]/div[2]/div[1]/div/a/div[2]/div[1]");

    public BedsCollection(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    // order is either DESC or ASC for descending and ascending price order of items
    public void selectPriceOrder(String order) {
        driver.get("https://petstore.com/collections/beds");
        WebElement dropdownElement = driver.findElement(By.id("SortBy"));
        Select dropdown = new Select(dropdownElement);
        if (order.equalsIgnoreCase("DESC")){
            dropdown.selectByValue("price-descending");
        } else if (order.equalsIgnoreCase("ASC")){
            dropdown.selectByValue("price-ascending");
        }

    }

    public String getFirstElementText() {
        WebElement firstElement = driver.findElement(firstEl);
        String actual = firstElement.getText();
        LogUtility.log("First item after sorting by max price: " + actual);
        return actual;
    }
}
