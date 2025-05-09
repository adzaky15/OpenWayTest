import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

public class SimpleTest {

    String email = "tnuoccalaeraton25@gmail.com";
    String password = "Test5Email25";
    WebDriver driver;
    String baseUrl = "https://www.periplus.com/ ";
    String newReleasesUrl = "https://www.periplus.com/index.php?route=product/category&anl=103";

    @BeforeMethod
    public void setUpBrowser() {

        // Initialize ChromeDriver.
        driver = new ChromeDriver();

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Maximize the browser window size.
        driver.manage().window().maximize();

        // Navigate to the website.
        driver.get(baseUrl);
    }

    @Test
    public void sampleTest() {

        // Get the login link and click it.
        WebElement loginLink = driver.findElement(By.id("nav-signin-text"));
        loginLink.click();

        // Fill email and password, and then submit.
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement submitLogin = driver.findElement(By.id("button-login"));
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        submitLogin.click();

        // Wait until navbar is present.
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("nav-inner")));

        // Navigate to the website new releases page.
        driver.get(newReleasesUrl);

        // Wait until loading finish
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        // Get one of the products
        WebElement container = driver.findElement(By.xpath("/html/body/section/div/div/div[3]/div[3]"));
        WebElement product = container.findElement(By.className("single-product"));
        WebElement addToCart = product.findElement(By.className("addtocart"));
        wait.until(ExpectedConditions.elementToBeClickable(addToCart));

        addToCart.click();

    }

    @AfterMethod
    public void closeBrowser() {

        // Terminate the browser.
//        driver.quit();
    }
}
