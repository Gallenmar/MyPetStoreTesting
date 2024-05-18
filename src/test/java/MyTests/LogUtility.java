package MyTests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogUtility {
    private static final String LOG_FILE = "Logs/test_logs.txt";

    static {
        // Ensure the log file is created or cleared at the start of the execution
        try {
            Path path = Paths.get(LOG_FILE);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String message) {
        System.out.println(message);
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(message + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public static void takeScreenshot(WebDriver driver, String testName) {
        try {
            // Wait for 2 seconds before taking the screenshot
            Thread.sleep(2000);

            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("Logs/" + testName + " screenshot.png"));
            log("Screenshot for " + testName + " saved successfully.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log("Thread was interrupted: " + e.getMessage());
        } catch (Exception e) {
            log("Failed to take screenshot: " + e.getMessage());
        }
    }
}
