package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IndexPage {
    protected WebDriver driver;
    String pageUrl = "https://www.periplus.com/_index_/index";
    String welcomeMessagePrefix = "Hi,";

    // <span id="nav-signin-text" class="nav-button-em"></span>
    private final By loginLinkBy = By.id("nav-signin-text");
    // <span class="nav-button-title"></span>
    private final By loginBtnBy = By.xpath("./..");

    public IndexPage(WebDriver driver){
        if (!pageUrl.equals(driver.getCurrentUrl())) {
            driver.get(pageUrl);
        }
        this.driver = driver;
    }

    /**
     * Verify if login successful
     *
     * @return true if success
     */
    public boolean verifyLogin() {
        WebElement loginLink = driver.findElement(loginLinkBy);
        WebElement loginBtn = loginLink.findElement(loginBtnBy);
        String welcomeMessage = loginBtn.getDomProperty("innerText");

        return welcomeMessage != null && welcomeMessage.contains(welcomeMessagePrefix);
    }
}
