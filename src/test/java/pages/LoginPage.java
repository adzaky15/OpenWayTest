package pages;

import elements.Preloader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object encapsulates the Login page.
 */
public class LoginPage {
    protected WebDriver driver;
    String pageUrl = "https://www.periplus.com/account/Login";

    // <input name="email" value="" type="email" required="">
    private final By emailBy = By.name("email");
    // <input name="password" value="" type="password" id="ps" required="">
    private final By passwordBy = By.name("password");
    // <input type="submit" value="Login" id="button-login" class="buton">
    private final By signinBy = By.id("button-login");


    public LoginPage(WebDriver driver){
        if (!pageUrl.equals(driver.getCurrentUrl())) {
            driver.get(pageUrl);
            Preloader.waitPreloader(driver);
        }
        this.driver = driver;
    }

    /**
     * Login as valid user
     *
     * @param email - valid email
     * @param password - valid password
     * @return AccountPage object
     */
    public IndexPage loginValidUser(String email, String password) {
        driver.findElement(emailBy).sendKeys(email);
        driver.findElement(passwordBy).sendKeys(password);
        driver.findElement(signinBy).click();

        Preloader.waitPreloader(driver);

        return new IndexPage(driver);
    }
}
