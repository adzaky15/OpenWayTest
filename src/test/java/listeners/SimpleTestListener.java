package listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testCases.SimpleTest;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class SimpleTestListener implements ITestListener {
    private static final Logger logger = Logger.getLogger(SimpleTestListener.class.getName());

    String folderName = "Screenshots";

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String targetPath = "%s/%s_FAIL.png";
        File srcFile = ((TakesScreenshot) SimpleTest.driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(String.format(targetPath, folderName, testName));

        createOrClearDir(testName);
        saveScreenshot(srcFile, destFile);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String targetPath = "%s/%s_SUCCESS.png";
        File srcFile = ((TakesScreenshot) SimpleTest.driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(String.format(targetPath, folderName, testName));

        createOrClearDir(testName);
        saveScreenshot(srcFile, destFile);
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
