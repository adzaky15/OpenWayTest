import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class SampleTest {

    String email = "tnuoccalaeraton25@gmail.com";
    String password = "Test5Email25";
    WebDriver driver;
    String baseUrl = "https://www.periplus.com/ ";

    @BeforeMethod
    public void setUpBrowser() {

        // Initialize ChromeDriver.
        driver = new ChromeDriver();

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

    }

    @AfterMethod
    public void closeBrowser() {

        // Terminate the browser.
//        driver.quit();
    }
}
