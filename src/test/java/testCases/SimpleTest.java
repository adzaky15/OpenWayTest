package testCases;

import elements.ItemAttr;
import listeners.SimpleTestListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import pages.CartPage;
import pages.NewReleasesPage;

@Listeners(SimpleTestListener.class)
public class SimpleTest {
    String email = "tnuoccalaeraton25@gmail.com";
    String password = "Test5Email25";

    public static WebDriver driver; // exposed to listener
    String baseUrl = "https://www.periplus.com/";

    ItemAttr tempItemAttr; // Item to remove later

    @BeforeMethod
    public void setUpBrowser() {

        // Initialize ChromeDriver.
        driver = new ChromeDriver();

        // Maximize the browser window size.
        driver.manage().window().maximize();

        // Navigate to the website.
        driver.get(baseUrl);
    }

    @Test(description = "add item to cart from new releases page")
    public void addToCartTest(ITestContext context) {
        // given
        SimpleTestHelper.validLoginSequence(driver, email, password);
        WebElement product = SimpleTestHelper.getProductNotInCart(driver);

        // when
        NewReleasesPage newReleasesPage = new NewReleasesPage(driver);
        newReleasesPage.addItemToCart(product);

        // then
        ItemAttr productAttr = newReleasesPage.saveAttributes(product);
        CartPage cartPage = new CartPage(driver);
        WebElement item = cartPage.getCartItem(productAttr);
        Assert.assertNotNull(item, "Matching item was not found");

        tempItemAttr = cartPage.saveAttributes(item);
        context.setAttribute("chosenItem", tempItemAttr); // pass to listener
        Assert.assertEquals(productAttr.title, tempItemAttr.title, "Product title does not match");
        Assert.assertEquals(productAttr.url, tempItemAttr.url, "Product url does not match");
        Assert.assertEquals(productAttr.price, tempItemAttr.price, "Product price does not match");
        Assert.assertEquals(productAttr.quantity, tempItemAttr.quantity, "Product quantity does not match");
    }

    @AfterMethod
    public void closeBrowser() {
        // Remove the recently added cart item
        if (tempItemAttr != null) {
            SimpleTestHelper.removeItemByAttr(driver, tempItemAttr);
            tempItemAttr = null;
        }

        // Terminate the browser.
        driver.quit();
    }
}
