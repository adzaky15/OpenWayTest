package testCases;

import elements.ItemAttr;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.CartPage;
import pages.IndexPage;
import pages.LoginPage;
import pages.NewReleasesPage;

public class SimpleTestHelper {
    public static void validLoginSequence(WebDriver driver, String email, String password) {
        IndexPage indexPage = new LoginPage(driver).loginValidUser(email, password);
        Assert.assertTrue(indexPage.verifyLogin(), "login sequence failed");
    }

    public static WebElement getItemNotInCart(WebDriver driver) {
        NewReleasesPage newReleasesPage = new NewReleasesPage(driver);
        WebElement product = newReleasesPage.getFirstItem();
//        removeItemByAttr(driver, newReleasesPage.saveAttributes(product));

        return product;
    }

    public static void removeItemByAttr(WebDriver driver, ItemAttr itemAttr) {
        CartPage cartPage = new CartPage(driver);
        WebElement item = cartPage.getCartItem(itemAttr);
        if (item != null) {
            cartPage.removeCartItem(item);
        }
    }
}
