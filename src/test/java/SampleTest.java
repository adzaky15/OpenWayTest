import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class SampleTest {

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

        // Ensure that the login link is present.
        WebElement loginLink = driver.findElement(By.xpath("/html/body/header/div[2]/div/div[1]/div[3]/div/div[3]/a"));
        Assert.assertTrue(loginLink.isDisplayed(), "The login link was not found.");

        // Click the login link.
        loginLink.click();

        // Ensure that the login page has email, password, and submit.
        WebElement emailField = driver.findElement(By.xpath("/html/body/div[1]/div/div/form/div/div/div/table/tbody/tr[2]/td/input"));
        Assert.assertTrue(emailField.isDisplayed(), "The email field was not found.");
        WebElement passwordField = driver.findElement(By.xpath("/html/body/div[1]/div/div/form/div/div/div/table/tbody/tr[4]/td/input"));
        Assert.assertTrue(passwordField.isDisplayed(), "The password field was not found.");
        WebElement submitLogin = driver.findElement(By.xpath("/html/body/div/div/div/form/div/div/div/table/tbody/tr[5]/td/input"));
        Assert.assertTrue(submitLogin.isDisplayed(), "The login button was not found.");

        // Perform login
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        submitLogin.click();

        // Ensure that the home link is present.
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/header/div[3]/div/div/div/div/div/nav/div/div/ul/li[2]/a")));
        WebElement homeLink = driver.findElement(By.xpath("/html/body/header/div[3]/div/div/div/div/div/nav/div/div/ul/li[2]/a"));
        Assert.assertTrue(homeLink.isDisplayed(), "The home link was not found.");

        // Navigate to the website new releases page.
        driver.get(newReleasesUrl);

        // Ensure that the item add to cart is present.
        WebElement addToCart = driver.findElement(By.xpath("/html/body/section/div/div/div[3]/div[3]/div[1]/div/div[2]/div[5]/div/div/a[2]"));
        Assert.assertTrue(addToCart.isDisplayed(), "The login link was not found.");

        // Click the item add to cart.
        addToCart.click();

    }

    @AfterMethod
    public void closeBrowser() {

        // Terminate the browser.
//        driver.quit();
    }
}
