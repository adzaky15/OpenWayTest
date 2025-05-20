package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Preloader {
    private final static Duration defaultWait = Duration.ofSeconds(20);
    private final static By preloaderBy = By.className("preloader");

    public static void waitPreloader(WebDriver driver) {
        new WebDriverWait(driver, defaultWait).until(ExpectedConditions.invisibilityOfElementLocated(preloaderBy));
    }

    public static void waitPreloader(WebDriver driver, Duration waitTime) {
        new WebDriverWait(driver, waitTime).until(ExpectedConditions.invisibilityOfElementLocated(preloaderBy));
    }
}
