package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IndexPage {
    protected WebDriver driver;
    String pageUrl = "https://www.periplus.com/_index_/index";

    // <span id="nav-signin-text" class="nav-button-em"></span>
    public IndexPage(WebDriver driver){
        if (!pageUrl.equals(driver.getCurrentUrl())) {
            driver.get(pageUrl);
        }
        this.driver = driver;
    }

    public boolean verifyLogin() {
        WebElement loginLink = driver.findElement(By.id("nav-signin-text"));
        WebElement loginBtn = loginLink.findElement(By.xpath("./.."));
        String welcomeMessage = loginBtn.getDomProperty("innerText");

        return welcomeMessage != null && welcomeMessage.contains("Hi,");
    }
}
