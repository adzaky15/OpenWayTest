package listeners;

import elements.ItemAttr;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestListener;
import org.testng.ITestResult;
import pages.CartPage;
import testCases.SimpleTest;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class SimpleTestListener implements ITestListener {
    private static final Logger logger = Logger.getLogger(SimpleTestListener.class.getName());

    String folderName = "Screenshots";

    @Override
    public void onTestFailure(ITestResult result) {
        String targetPath = "%s/%s_FAIL.png";

        captureAndSave(SimpleTest.driver, result, targetPath);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String targetPath = "%s/%s_PASS.png";

        captureAndSave(SimpleTest.driver, result, targetPath);
    }

    private void captureAndSave(WebDriver driver, ITestResult result, String targetPath) {
        String testName = result.getMethod().getMethodName();

        scrollToElem(driver, result);
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(String.format(targetPath, folderName, testName));

        createOrClearDir(testName);
        saveScreenshot(srcFile, destFile);
    }

    private void scrollToElem(WebDriver driver, ITestResult result) {
        ItemAttr targetAttr = (ItemAttr) result.getTestContext().getAttribute("chosenItem");
        if (targetAttr == null) {
            return;
        }

        WebElement targetElem = new CartPage(driver).getCartItem(targetAttr);
        if (targetElem == null) {
            return;
        }

        new Actions(driver).moveToElement(targetElem).perform();
    }

    private void createOrClearDir(String methodName) {
        File directory = new File(folderName);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                logger.info(String.format("Directory %s created successfully.", folderName));
            } else {
                logger.severe(String.format("Failed to create directory %s.", folderName));
            }
        } else {
            File[] files = directory.listFiles();
            if (files == null) {
                logger.severe(String.format("Directory %s is a file.", folderName));
                return;
            }

            for(File f: files) {
                String fileName = f.getName();
                if (fileName.startsWith(methodName + "_")) {
                    if (f.delete()) {
                        logger.info(String.format("Deleted %s.", fileName));
                    } else {
                        logger.severe(String.format("Unable to delete %s.", fileName));
                    }
                }
            }
        }
    }

    private void saveScreenshot(File srcFile, File destFile) {
        try {
            FileHandler.copy(srcFile, destFile);
        } catch (IOException e) {
            // Log the exception using the Java logger
            logger.severe("An error occurred:");
            logger.severe(e.toString());
        }
    }
}
