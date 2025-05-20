package testCases;

import elements.Preloader;
import elements.ProductCard;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CartPage;
import pages.NewReleasesPage;

import java.util.concurrent.TimeUnit;

public class SimpleTest {
    String email = "tnuoccalaeraton25@gmail.com";
    String password = "Test5Email25";

    WebDriver driver;
    String baseUrl = "https://www.periplus.com/";

    WebElement tempChosenItem; // Item to remove later

    @BeforeMethod
    public void setUpBrowser() {

        // Initialize ChromeDriver.
        driver = new ChromeDriver();

        // Maximize the browser window size.
        driver.manage().window().maximize();

        // Navigate to the website.
        driver.get(baseUrl);
    }

    @Test(description = "add to cart from new releases")
    public void addToCartTest() {
        SimpleTestHelper.validLoginSequence(driver, email, password);
        WebElement product = SimpleTestHelper.getItemNotInCart(driver);

        NewReleasesPage newReleasesPage = new NewReleasesPage(driver);
        ProductCard productCard = newReleasesPage.saveAttributes(product);
        newReleasesPage.addItemToCart(product);

        CartPage cartPage = new CartPage(driver);
        Preloader.waitPreloader(driver); // this might be needed
        tempChosenItem = cartPage.getCartItem(productCard);
        Assert.assertNotNull(tempChosenItem, "Matching item was not found");
    }

    @AfterMethod
    public void closeBrowser() throws InterruptedException {
        // Remove the checkout item after 10 seconds
        TimeUnit.SECONDS.sleep(10);
        if (tempChosenItem != null) {
            CartPage cartPage = new CartPage(driver);
            cartPage.removeCartItem(tempChosenItem);
        }
        tempChosenItem = null;

        // Terminate the browser.
        driver.quit();
    }
}
