package testCases;

import elements.Preloader;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;

public class SimpleTestHelper {
    public static WebDriver validLoginSequence(WebDriver driver, String email, String password) {
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.loginValidUser(email, password).getDriver();
    }
}
