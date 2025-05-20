package pages;

import org.openqa.selenium.WebDriver;

public class IndexPage {
    protected WebDriver driver;
    String pageUrl = "https://www.periplus.com/_index_/index";

    public IndexPage(WebDriver driver){
        if (!pageUrl.equals(driver.getCurrentUrl())) {
            driver.get(pageUrl);
        }
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
