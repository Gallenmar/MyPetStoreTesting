package MyTests;

import MyTests.LogUtility;
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
import java.util.UUID;

public class PSTesNGlog {
    private static final String TEST_EMAIL_SUFFIX = "@gmail.com";
    private static final String QUIETTIME_MAX_PRICE_ITEM = "QUIETTIME® DEFENDER GRAY COLOR GEO BOLSTERED ORTHOPEDIC SOFA WITH TEFLON FOR PET 40 INCH";
    private static final String QUIETTIME_MIN_PRICE_ITEM = "QUIETTIME® DELUXE FLEECE BOLSTER PET BED 22 INCH";
    private static final String SEARCH_QUERY = "MIGHTY® MEDIUM MICRO BALL DUCK DOG TOYS 5.5 X 9 X 12 INCH";
    private static final String EXPECTED_PRODUCT_NAME = "MIGHTY® MEDIUM MICRO BALL DUCK DOG TOYS 5.5 X 9 X 12 INCH";
    private static final String FIRST_PRODUCT_NAME = "MIGHTY® MEDIUM MICRO BALL DUCK DOG TOYS 5.5 X 9 X 12 INCH";
    private static final String SECOND_PRODUCT_NAME = "MIGHTY® MASSIVE BEAVER DOG TOYS 7 X 20 X 10.5 INCH";

    private static WebDriver driver;
    private static String emailName;
    private static String emailTail;
    private static String email;
    private static String password = "BobTesting";
    private static int waitingTime = 10;
    static WebDriverWait wait = null;

    @BeforeClass
    public static void setupClass() throws Exception {
        emailName = "Bob" + UUID.randomUUID().toString().substring(0, 8);
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

    @Test(priority = 1)
    public void testBasic() {
        LogUtility.log("testBasic");
        String expectedUrl = "https://petstore.com/";
        String currentUrl = driver.getCurrentUrl();
        LogUtility.log("Testing basic page load. Expected URL: " + expectedUrl + ", Actual URL: " + currentUrl);
        LogUtility.takeScreenshot(driver, "testBasic");

        Assert.assertTrue(currentUrl.equals(expectedUrl), "Expected URL: " + expectedUrl + ", Actual URL: " + currentUrl);
        LogUtility.log("Assertion passed for basic page load.");
    }

    @Test(priority = 2)
    public void testRegisteringAndRedirectToHome() throws Exception {
        LogUtility.log("testRegisteringAndRedirectToHome");
        registerWithEmail(email);
        dismissPopup();
        String expectedUrl = "https://petstore.com/";
        String currentUrl = driver.getCurrentUrl();
        LogUtility.log("Testing registration and redirect. Expected URL: " + expectedUrl + ", Actual URL: " + currentUrl);
        LogUtility.takeScreenshot(driver, "testRegisteringAndRedirectToHome");

        Assert.assertTrue(currentUrl.equals(expectedUrl), "Expected URL: " + expectedUrl + ", Actual URL: " + currentUrl);
        LogUtility.log("Assertion passed for registration and redirect.");
        // check here
    }

    @Test(priority = 3)
    public void testCorrectInfoAfterRegister() {
        LogUtility.log("testCorrectInfoAfterRegister");
        driver.get("https://petstore.com/account");
        WebElement userInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"MainContent\"]/div/div/div[2]/p[2]")));
        String userInfoText = userInfo.getText();
        LogUtility.log("Checked user info after registration. Found info: " + userInfoText);
        LogUtility.takeScreenshot(driver, "testCorrectInfoAfterRegister");
        Assert.assertTrue(userInfoText.contains("Bob B"), "Expected User Info not found: " + userInfoText);
    }

    @Test(priority = 4)
    public void testSort() {
        LogUtility.log("testSort");
        driver.get("https://petstore.com/collections/beds");

        // Locate the dropdown element by ID
        WebElement dropdownElement = driver.findElement(By.id("SortBy"));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("price-descending");

        WebElement firstElement = driver.findElement(By.xpath("//*[@id=\"CollectionSection\"]/div[2]/div[1]/div/a/div[2]/div[1]"));
        LogUtility.log("First item after sorting by max price: " + firstElement.getText());
        String actual = firstElement.getText();
        boolean isEqualIgnoreCase = QUIETTIME_MAX_PRICE_ITEM.equalsIgnoreCase(actual);
        Assert.assertTrue(isEqualIgnoreCase, "Max price sort failed.");
        LogUtility.takeScreenshot(driver, "testSortMaxPrice");

        dropdownElement = driver.findElement(By.id("SortBy"));
        dropdown = new Select(dropdownElement);
        dropdown.selectByValue("price-ascending");
        firstElement = driver.findElement(By.xpath("//*[@id=\"CollectionSection\"]/div[2]/div[1]/div/a/div[2]/div[1]"));
        LogUtility.log("First item after sorting by min price: " + firstElement.getText());
        actual = firstElement.getText();
        isEqualIgnoreCase = QUIETTIME_MAX_PRICE_ITEM.equalsIgnoreCase(actual);
        Assert.assertEquals(firstElement.getText(), QUIETTIME_MIN_PRICE_ITEM, "Min price sort failed.");
        LogUtility.takeScreenshot(driver, "testSortMinPrice");

        LogUtility.log("Sort test completed successfully.");
    }

    @Test(priority = 5)
    public void testSearch() {
        LogUtility.log("testSearch");
        try {
            // Navigate to the search button and click it.
            WebElement searchBtn = driver.findElement(By.xpath("//*[@id=\"shopify-section-header\"]/div[3]/div[2]/div/div/header/div[1]/div/div[3]/div/div/a[2]"));
            searchBtn.click();
            LogUtility.log("Clicked on the search button.");

            // Enter the search query into the search box and submit.
            WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"HeaderSearchForm\"]/input[2]"));
            searchBox.sendKeys(SEARCH_QUERY);
            searchBox.sendKeys(Keys.ENTER);
            LogUtility.log("Search submitted for: " + SEARCH_QUERY);

            // Capture the screenshot after performing the search
            LogUtility.takeScreenshot(driver, "testSearch");

            // Asserting the search results
            WebElement firstElement = driver.findElement(By.xpath("//*[@id=\"MainContent\"]/div/div/div/div/div[2]/div/div/a/div[2]/div[1]"));
            String actual = firstElement.getText();
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

    @Test(priority = 6)
    public void testCart() {
        LogUtility.log("testCart");
        try {
            // Adding the first product to the cart
            addProductToCart(FIRST_PRODUCT_NAME);
            WebElement cartElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"CartContainer\"]/div[1]/div/div/div/div[2]/div[1]/a")));
            boolean isFirstProductAdded = FIRST_PRODUCT_NAME.equalsIgnoreCase(cartElement.getText());
            LogUtility.log("First product added check: " + isFirstProductAdded);

            // Navigating back to homepage before adding the second product
            driver.get("https://petstore.com/");
            addProductToCart(SECOND_PRODUCT_NAME);
            cartElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"CartContainer\"]/div[1]/div/div/div/div[2]/div[1]/a")));
            boolean isSecondProductAdded = SECOND_PRODUCT_NAME.equalsIgnoreCase(cartElement.getText());
            LogUtility.log("Second product added check: " + isSecondProductAdded);

            // Checking if both items are in the cart
            int elementCount = driver.findElements(By.className("ajaxcart__row")).size();
            boolean correctCount = elementCount == 2;
            LogUtility.log("Correct number of items in cart: " + elementCount);

            // Asserting that both items are added and the cart count is correct
            Assert.assertTrue(isFirstProductAdded && isSecondProductAdded && correctCount, "The cart contents are not as expected.");
            LogUtility.log("Cart test passed.");
        } catch (Exception e) {
            LogUtility.log("Error during testCart: " + e.getMessage());
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }

    private void addProductToCart(String productName) {
        LogUtility.log("addProductToCart");
        WebElement searchBtn = driver.findElement(By.xpath("//*[@id=\"shopify-section-header\"]/div[3]/div[2]/div/div/header/div[1]/div/div[3]/div/div/a[2]"));
        searchBtn.click();
        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"HeaderSearchForm\"]/input[2]"));
        searchBox.sendKeys(productName);
        searchBox.sendKeys(Keys.ENTER);
        WebElement firstElement = driver.findElement(By.xpath("//*[@id=\"MainContent\"]/div/div/div/div/div[2]/div/div/a/div[2]/div[1]"));
        firstElement.click();
        WebElement addToCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("add-to-cart")));
        addToCart.click();
        LogUtility.log("Product added to cart: " + productName);
    }

    @Test(priority = 99)
    public void testNotUniqueEmail() {
        LogUtility.log("testNotUniqueEmail");
        try {
            // Navigate to the account page and attempt to log out
            driver.get("https://petstore.com/account");
            LogUtility.log("Navigated to account page.");

            try {
                WebElement logoutButton = driver.findElement(By.xpath("//*[@id=\"MainContent\"]/div/header/a"));
                logoutButton.click();
                LogUtility.log("Clicked logout button.");
            } catch (NoSuchElementException e) {
                LogUtility.log("No logout button found, possibly not logged in.");
            }

            // Attempt to register with an already used email
            registerWithEmail(email);
            LogUtility.log("Attempted to register with an email: " + email);

            // Check for error message
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='errors']//li")));
            String errorMessageText = errorMessage.getText();
            LogUtility.log("Error message received: " + errorMessageText);

            // Asserting the presence of the specific error message
            Assert.assertTrue(errorMessageText.contains("This email address is already associated with an account."), "Expected error message not found: " + errorMessageText);
            LogUtility.log("Assertion passed for unique email test.");

        } catch (Exception e) {
            LogUtility.log("Error during testNotUniqueEmail: " + e.getMessage());
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }

    @Test(priority = 100)
    public void testNotAllFieldsFilled() {
        LogUtility.log("testNotAllFieldsFilled");
        try {
            // Navigate to the sign-in page and attempt to access the registration form
            navigateToRegistrationForm();

            // Fill the registration form with incomplete details (missing password)
            fillInRegistrationForm("Bob", "B", "Bob123testing@gmail.com", "");

            // Submit the registration form
            WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
            submitButton.click();
            LogUtility.log("Submitted registration form with missing password field.");

            // Check for CAPTCHA presence and handle it if found
            if (checkForProtection()) {
                throw new Exception("Cloudflare protection encountered. Cannot continue testing.");
            }
            LogUtility.log("Registration attempted without encountering Cloudflare protection.");

            // Check for and validate the error message
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='errors']//li")));
            String errorMessageText = errorMessage.getText();
            LogUtility.log("Error message received: " + errorMessageText);

            // Asserting that the error message about the missing password appears
            Assert.assertTrue(errorMessageText.contains("Password can't be blank."), "Expected error message not found: " + errorMessageText);
            LogUtility.log("Assertion passed for missing password validation.");

        } catch (Exception e) {
            LogUtility.log("Error during testNotAllFieldsFilled: " + e.getMessage());
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }

    private void navigateToRegistrationForm() {
        WebElement signInLink = driver.findElement(By.xpath("//*[@id=\"shopify-section-header\"]/div[3]/div[2]/div/div/header/div[1]/div/div[3]/div/div/a[1]"));
        signInLink.click();
        LogUtility.log("Clicked on the sign-in link.");

        WebElement registerLink = driver.findElement(By.xpath("//*[@id=\"customer_register_link\"]"));
        registerLink.click();
        LogUtility.log("Accessed the registration form.");
    }

    private void fillInRegistrationFormWithEmail(String email) {
        fillInRegistrationForm("Bob", "B", email, password);
    }

    private void fillInRegistrationForm(String firstName, String lastName, String email, String password) {
        try {
            LogUtility.log("Filling in the registration form.");

            // Fill in the first name
            WebElement firstNameInput = driver.findElement(By.id("FirstName"));
            firstNameInput.clear();  // Clearing any pre-filled values
            firstNameInput.sendKeys(firstName);
            LogUtility.log("Entered First Name: " + firstName);

            // Fill in the last name
            WebElement lastNameInput = driver.findElement(By.id("LastName"));
            lastNameInput.clear();  // Clearing any pre-filled values
            lastNameInput.sendKeys(lastName);
            LogUtility.log("Entered Last Name: " + lastName);

            // Fill in the email
            WebElement emailInput = driver.findElement(By.id("Email"));
            emailInput.clear();  // Clearing any pre-filled values
            emailInput.sendKeys(email);
            LogUtility.log("Entered Email: " + email);

            // Fill in the password
            WebElement passwordInput = driver.findElement(By.id("CreatePassword"));
            passwordInput.clear();  // Clearing any pre-filled values
            passwordInput.sendKeys(password);
            LogUtility.log("Entered Password: " + password);

            // Click the submit button
            WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
            submitButton.click();
            LogUtility.log("Clicked the submit button on the registration form.");

        } catch (NoSuchElementException e) {
            LogUtility.log("Error in form entry: Element not found - " + e.getMessage());
            throw new RuntimeException("Form element not found, cannot proceed with registration.", e);
        } catch (Exception e) {
            LogUtility.log("Unexpected error while filling the form: " + e.getMessage());
            throw e;  // Re-throw the exception to handle it in the calling method
        }
    }


    private void registerWithEmail(String email) throws Exception {
        LogUtility.log("Attempting to register with email: " + email);
        driver.get("https://petstore.com/account/register");
        fillInRegistrationFormWithEmail(email);
        if (checkForProtection()) {
            throw new Exception("Cloudflare protection encountered. Cannot continue testing.");
        }
        LogUtility.log("Registration attempted without encountering Cloudflare protection.");
    }

    private static boolean checkForProtection() {
        LogUtility.log("Checking for Cloudflare protection.");
        checkForCaptcha();
        try {
            WebElement cloudflareCaptcha = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/h1")));
            String cloudflareCaptchaText = cloudflareCaptcha.getText();
            if ("Your connection needs to be verified before you can proceed".equals(cloudflareCaptchaText)) {
                LogUtility.log("Cloudflare Protection detected. Please check the box manually.");
                return true;
            }
        } catch (TimeoutException e) {
            LogUtility.log("No Cloudflare Protection detected. Test continues.");
        }
        return false;
    }

    private static void checkForCaptcha() {
        try {
            WebElement captchaElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"g-recaptcha\"]")));
            LogUtility.log("CAPTCHA detected. Please complete it manually.");
            // Ideally, this loop would not run indefinitely
            while (true) {
                Thread.sleep(2000);  // Wait for 5 seconds before checking again
                captchaElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"g-recaptcha\"]")));
                if (!captchaElement.isDisplayed()) {
                    LogUtility.log("CAPTCHA completed manually.");
                    break;
                }
            }
        } catch (TimeoutException e) {
            LogUtility.log("No CAPTCHA detected. Test continues.");
        } catch (InterruptedException e) {
            LogUtility.log("Interrupted while waiting for CAPTCHA: " + e.getMessage());
            Thread.currentThread().interrupt();  // Restore the interrupted status
        }
    }


    private void dismissPopup() {
        try {
            WebElement popup = driver.findElement(By.xpath("//*[@id='NewsletterPopup-newsletter-popup']/div/div/button"));
            popup.click();
            LogUtility.log("Popup dismissed.");
        } catch (NoSuchElementException e) {
            LogUtility.log("No popup to dismiss.");
        }
    }
}
