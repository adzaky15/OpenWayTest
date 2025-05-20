package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.IndexPage;
import pages.LoginPage;

public class SimpleTestHelper {
    public static void validLoginSequence(WebDriver driver, String email, String password) {
        IndexPage indexPage = new LoginPage(driver).loginValidUser(email, password);
        Assert.assertTrue(indexPage.verifyLogin(), "login sequence failed");
    }
}
