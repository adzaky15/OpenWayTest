package testCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
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
        return newReleasesPage.getFirstItem();
    }
}
