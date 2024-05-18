package MyTests.PageObjects;

import MyTests.LogUtility;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static MyTests.PSTesNGlog.wait;

public class RegisterPage {
    private WebDriver driver;

    // Locators
    private By firstNameField = By.id("FirstName");
    private By lastNameField = By.id("LastName");
    private By emailField = By.id("Email");
    private By passwordField = By.id("CreatePassword");
    private By submitButton = By.xpath("//input[@type='submit']");
    private By errorElement = By.xpath("//div[@class='errors']//li");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public void fillInRegistrationForm(String firstName, String lastName, String email, String password) {
        try {
            driver.get("https://petstore.com/account/register");

            driver.findElement(firstNameField).sendKeys(firstName);
            LogUtility.log("Entered First Name: " + firstName);
            driver.findElement(lastNameField).sendKeys(lastName);
            LogUtility.log("Entered Last Name: " + lastName);
            driver.findElement(emailField).sendKeys(email);
            LogUtility.log("Entered Email: " + email);
            driver.findElement(passwordField).sendKeys(password);
            LogUtility.log("Entered Password: " + password);
            driver.findElement(submitButton).click();
            LogUtility.log("Clicked the submit button on the registration form.");
        } catch (NoSuchElementException e) {
            LogUtility.log("Error in form entry: Element not found - " + e.getMessage());
            throw new RuntimeException("Form element not found, cannot proceed with registration.", e);
        } catch (Exception e) {
            LogUtility.log("Unexpected error while filling the form: " + e.getMessage());
            throw e;  // Re-throw the exception to handle it in the calling method
        }
    }

    public static boolean checkForProtection() {
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
                Thread.sleep(2000);  // Wait for 0.5 seconds before checking again
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

    public String getErrorMessage() {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(errorElement));
        LogUtility.log("Error message received: " + errorMessage.getText());
        return errorMessage.getText();
    }
}
