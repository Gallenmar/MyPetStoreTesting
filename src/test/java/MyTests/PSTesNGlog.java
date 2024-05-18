package MyTests;

import MyTests.LogUtility;
import MyTests.PageObjects.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class PSTesNGlog {
    private static final String TEST_EMAIL_SUFFIX = "@gmail.com";
    private static final String QUIETTIME_MAX_PRICE_ITEM = "QUIETTIME® DEFENDER GRAY COLOR GEO BOLSTERED ORTHOPEDIC SOFA WITH TEFLON FOR PET 40 INCH";
    private static final String QUIETTIME_MIN_PRICE_ITEM = "QUIETTIME® DELUXE FLEECE BOLSTER PET BED 22 INCH";
    private static final String SEARCH_QUERY = "MIGHTY® MEDIUM MICRO BALL DUCK DOG TOYS 5.5 X 9 X 12 INCH";
    private static final String EXPECTED_PRODUCT_NAME = "MIGHTY® MEDIUM MICRO BALL DUCK DOG TOYS 5.5 X 9 X 12 INCH";
    private static final String FIRST_PRODUCT_NAME = "MIGHTY® MEDIUM MICRO BALL DUCK DOG TOYS 5.5 X 9 X 12 INCH";
    private static final String SECOND_PRODUCT_NAME = "MIGHTY® MASSIVE BEAVER DOG TOYS 7 X 20 X 10.5 INCH";
    private static final String NAME = "Bob";
    private static final String LASTNAME = "B";
    private static final String PASSWORD = "Testing123!";

    private static WebDriver driver;
    private static String emailName;
    private static String emailTail;
    private static String email;
    private static String password = "BobTesting";
    private static int waitingTime = 10;
    public static WebDriverWait wait = null;

    @BeforeClass
    public static void setupClass() throws Exception {
        emailName = NAME + UUID.randomUUID().toString().substring(0, 8);
        emailTail = TEST_EMAIL_SUFFIX;
        email = emailName + emailTail;
        LogUtility.log("Setup started - Email used in this test is: " + email);
        LogUtility.log("Other variables:");
        LogUtility.log("QUIETTIME_MAX_PRICE_ITEM: "+QUIETTIME_MAX_PRICE_ITEM);
        LogUtility.log("QUIETTIME_MIN_PRICE_ITEM: "+QUIETTIME_MIN_PRICE_ITEM);
        LogUtility.log("SEARCH_QUERY: "+SEARCH_QUERY);
        LogUtility.log("EXPECTED_PRODUCT_NAME"+EXPECTED_PRODUCT_NAME);
        LogUtility.log("FIRST_PRODUCT_NAME: "+FIRST_PRODUCT_NAME);
        LogUtility.log("SECOND_PRODUCT_NAME: "+SECOND_PRODUCT_NAME);
        LogUtility.log("");

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nick\\My Drive\\.MY\\.Uni\\Uni4\\Test\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.petstore.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitingTime));
        LogUtility.log("WebDriver initialized and website loaded.");
        LogUtility.log("");
    }

    @AfterMethod
    public void cleanup() {
        driver.get("https://www.petstore.com/");
        LogUtility.log("Cleanup - Browser reset to home page.");
        LogUtility.log("");
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
            LogUtility.log("Teardown - WebDriver closed.");
        }
    }

    @Test
    public void testBasic() {
        LogUtility.log("testBasic");

        String expectedUrl = "https://petstore.com/";
        String currentUrl = driver.getCurrentUrl();
        LogUtility.log("Testing basic page load. Expected URL: " + expectedUrl + ", Actual URL: " + currentUrl);
        LogUtility.takeScreenshot(driver, "testBasic");

        Assert.assertTrue(currentUrl.equals(expectedUrl), "Expected URL: " + expectedUrl + ", Actual URL: " + currentUrl);
        LogUtility.log("Assertion passed for basic page load.");
    }

    @Test
    // todo delete priorities
    public void testCorrectInfoAfterRegister() throws Exception {
        LogUtility.log("testCorrectInfoAfterRegister");
        AccountPage accountPage = new AccountPage(driver);
        String expectedInfoText = NAME+" "+LASTNAME;
        String tmpEmail =  emailName + "info" + emailTail;

        registerWithEmail(tmpEmail);
        String userInfoText = accountPage.getUserInfo();

        LogUtility.log("Checked user info after registration. Found info: " + userInfoText);
        LogUtility.takeScreenshot(driver, "testCorrectInfoAfterRegister");

        Assert.assertTrue(userInfoText.contains(expectedInfoText), "Expected User Info not found: " + userInfoText);
    }

    @Test
    public void testSort() {
        LogUtility.log("testSort");
        BedsCollection bedsCollection = new BedsCollection(driver);
        String actual;

        bedsCollection.selectPriceOrder("desc");
        actual = bedsCollection.getFirstElementText();
        boolean isEqualIgnoreCase = QUIETTIME_MAX_PRICE_ITEM.equalsIgnoreCase(actual);
        Assert.assertTrue(isEqualIgnoreCase, "Max price sort failed.");
        LogUtility.takeScreenshot(driver, "testSortMaxPrice");

        bedsCollection.selectPriceOrder("asc");
        actual = bedsCollection.getFirstElementText();
        isEqualIgnoreCase = QUIETTIME_MAX_PRICE_ITEM.equalsIgnoreCase(actual);
        Assert.assertEquals(actual, QUIETTIME_MIN_PRICE_ITEM, "Min price sort failed.");

        LogUtility.takeScreenshot(driver, "testSortMinPrice");
        LogUtility.log("Sort test completed successfully.");
    }

    @Test
    public void testSearch() {
        LogUtility.log("testSearch");
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        try {
            homePage.clickSearchButton();
            searchPage.enterSearchQuery(SEARCH_QUERY);
            LogUtility.takeScreenshot(driver, "testSearch");

            // Asserting the search results
            String actual = searchPage.getFirstResultText();
            boolean isEqualIgnoreCase = EXPECTED_PRODUCT_NAME.equalsIgnoreCase(actual);
            LogUtility.log("Expected product name: " + EXPECTED_PRODUCT_NAME + ", Actual product name: " + actual);

            // Perform the assertion
            Assert.assertTrue(isEqualIgnoreCase, "The search result is not as expected.");
            LogUtility.log("Search test passed.");
        } catch (Exception e) {
            LogUtility.log("Error during testSearch: " + e.getMessage());
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }

    @Test
    public void testCart() {
        LogUtility.log("testCart");
        CartPage cartPage = new CartPage(driver);
        String firstCartElementText;
        try {
            // Adding the first product to the cart
            addProductToCart(FIRST_PRODUCT_NAME);
            firstCartElementText = cartPage.getFirstCartItemName();
            boolean isFirstProductAdded = FIRST_PRODUCT_NAME.equalsIgnoreCase(firstCartElementText);
            LogUtility.log("First product added check: " + isFirstProductAdded);

            // Adding the second product to the cart
            addProductToCart(SECOND_PRODUCT_NAME);
            firstCartElementText = cartPage.getFirstCartItemName();
            boolean isSecondProductAdded = SECOND_PRODUCT_NAME.equalsIgnoreCase(firstCartElementText);
            LogUtility.log("Second product added check: " + isSecondProductAdded);

            // Checking if two items are in the cart
            int elementCount = cartPage.getCartItemCount();
            boolean correctCount = elementCount == 2;
            LogUtility.log("Number of items in cart: " + elementCount);

            // Asserting that both items are added and the cart count is correct
            Assert.assertTrue(isFirstProductAdded && isSecondProductAdded && correctCount, "The cart contents are not as expected.");
            LogUtility.log("Cart test passed.");
        } catch (Exception e) {
            LogUtility.log("Error during testCart: " + e.getMessage());
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }

    @Test
    public void testNotAllFieldsFilled() {
        LogUtility.log("testNotAllFieldsFilled");
        try {
            String tmpEmail =  emailName + "missingField" + emailTail;
            LogUtility.log("Attempting to register with email: " + tmpEmail);
            RegisterPage registerPage = new RegisterPage(driver);

            //fillInRegistrationFormWithEmail(email);
            registerPage.fillInRegistrationForm(NAME, LASTNAME, tmpEmail, "");
            if (registerPage.checkForProtection()) {
                throw new Exception("Cloudflare protection encountered. Cannot continue testing.");
            }
            LogUtility.log("Registration attempted without encountering Cloudflare protection.");
            dismissPopup();

            // Check for and validate the error message
            String errorMessageText = registerPage.getErrorMessage();

            // Asserting that the error message about the missing password appears
            Assert.assertTrue(errorMessageText.contains("Password can't be blank."), "Expected error message not found: " + errorMessageText);
            LogUtility.log("Assertion passed for missing password validation.");

        } catch (Exception e) {
            LogUtility.log("Error during testNotAllFieldsFilled: " + e.getMessage());
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }


    @Test(priority = 1)
    public void testRegisteringAndRedirectToHome() throws Exception {
        LogUtility.log("testRegisteringAndRedirectToHome");

        registerWithEmail(email);

        String expectedUrl = "https://petstore.com/";
        String currentUrl = driver.getCurrentUrl();

        LogUtility.log("Testing registration and redirect. Expected URL: " + expectedUrl + ", Actual URL: " + currentUrl);
        LogUtility.takeScreenshot(driver, "testRegisteringAndRedirectToHome");

        Assert.assertTrue(currentUrl.equals(expectedUrl), "Expected URL: " + expectedUrl + ", Actual URL: " + currentUrl);
        LogUtility.log("Assertion passed for registration and redirect.");
    }

    @Test(priority = 2)
    public void testNotUniqueEmail() {
        LogUtility.log("testNotUniqueEmail");
        RegisterPage registerPage = new RegisterPage(driver);
        try {
            // Attempt to register with an already used email
            registerWithEmail(email);
            LogUtility.log("Attempted to register with an email: " + email);

            // Check for error message
            String errorMessageText = registerPage.getErrorMessage();

            // Asserting the presence of the specific error message
            Assert.assertTrue(errorMessageText.contains("This email address is already associated with an account."), "Expected error message not found: " + errorMessageText);
            LogUtility.log("Assertion passed for unique email test.");

        } catch (Exception e) {
            LogUtility.log("Error during testNotUniqueEmail: " + e.getMessage());
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }

    private void addProductToCart(String productName) throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.clickSearchButton();
        searchPage.enterSearchQuery(productName);
        searchPage.clickFirstResult();
        productPage.clickAddToCart();

        LogUtility.log("Product added to cart: " + productName);
    }

    private void registerWithEmail(String email) throws Exception {
        LogUtility.log("Attempting to register with email: " + email);
        RegisterPage registerPage = new RegisterPage(driver);

        //fillInRegistrationFormWithEmail(email);
        registerPage.fillInRegistrationForm(NAME, LASTNAME, email, PASSWORD);
        if (registerPage.checkForProtection()) {
            throw new Exception("Cloudflare protection encountered. Cannot continue testing.");
        }
        LogUtility.log("Registration attempted without encountering Cloudflare protection.");
        dismissPopup();
    }

    private void dismissPopup() {

        if (isElementVisible(driver, By.xpath("//*[@id='NewsletterPopup-newsletter-popup']/div/div/button"))) {
            WebElement popup =  driver.findElement(By.xpath("//*[@id='NewsletterPopup-newsletter-popup']/div/div/button"));
            popup.click();
            LogUtility.log("Element found and action taken.");
        } else {
            LogUtility.log("Element not found, continuing.");
        }
    }

    private static boolean isElementVisible(WebDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
