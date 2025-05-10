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
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SimpleTest {

    String email = "tnuoccalaeraton25@gmail.com";
    String password = "Test5Email25";
    WebDriver driver;
    String baseUrl = "https://www.periplus.com/";
    String newReleasePath = "index.php?route=product/category&anl=103";
    String checkoutPath = "checkout/cart";

    WebElement confirmedCheckout;

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
        driver.get(baseUrl + newReleasePath);

        // Wait until loading finish
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        // Get one of the products
        WebElement productList = driver.findElement(By.xpath("/html/body/section/div/div/div[3]/div[3]"));
        WebElement product = productList.findElement(By.className("single-product"));
        WebElement addToCart = product.findElement(By.className("addtocart"));
        wait.until(ExpectedConditions.elementToBeClickable(addToCart));

        addToCart.click();

        WebElement productName = product.findElement(By.className("title-product-cat"));
        WebElement productLink = productName.findElement(By.xpath(".//h3/a"));
        String productUrl = productLink.getDomProperty("href");
        System.out.println(productUrl);

        // Navigate to the website checkout page.
        driver.get(baseUrl + checkoutPath);

        // Wait until loading finish
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        // Get products from cart
        List<WebElement> checkoutProducts = driver.findElements(By.className("row-cart-product"));

        // Check if item exists
        boolean isExist = false;
        for (WebElement checkoutItem: checkoutProducts) {
            WebElement checkoutName = checkoutItem.findElement(By.className("product-name"));
            WebElement checkoutLink = checkoutName.findElement(By.xpath(".//a"));
            String checkoutUrl = checkoutLink.getDomProperty("href");

            Assert.assertNotNull(productUrl);
            if (productUrl.equals(checkoutUrl)) {
                System.out.println("Matching URL Found: " + checkoutUrl);
                isExist = true;
                confirmedCheckout = checkoutItem;
            }
        }

        Assert.assertTrue(isExist);
    }

    @AfterMethod
    public void closeBrowser() throws InterruptedException {

        // Remove the checkout item after 10 seconds
        TimeUnit.SECONDS.sleep(10);
        if (confirmedCheckout != null) {
            WebElement removeButton = confirmedCheckout.findElement(By.className("btn-cart-remove"));
            removeButton.click();
        }

        // Terminate the browser.
        driver.quit();
    }
}
