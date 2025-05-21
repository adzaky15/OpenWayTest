package pages;

import elements.Preloader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object encapsulates the Login page.
 */
public class LoginPage {
    protected WebDriver driver;
    protected Wait<WebDriver> wait;

    private final static String pageUrl = "https://www.periplus.com/account/Login";
    private final static Duration defaultWait = Duration.ofSeconds(10);

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
        this.wait = new WebDriverWait(driver, defaultWait);
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
        WebElement submitBtn = driver.findElement(signinBy);
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
        submitBtn.click();

        Preloader.waitPreloader(driver);

        return new IndexPage(driver);
    }
}
