package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class CartPage {
    protected WebDriver driver;
    String pageUrl = "https://www.periplus.com/checkout/cart";

    // <div class="row row-cart-product"></div>
    private final By checkoutItemsBy = By.className("row-cart-product");
    // <a class="btn btn-cart-remove" href="http://www.periplus.com/checkout/cart?remove=43518459">Remove</a>
    private final By removeButtonBy = By.className("btn-cart-remove");

    public CartPage(WebDriver driver){
        if (!pageUrl.equals(driver.getCurrentUrl())) {
            driver.get(pageUrl);
        }
        this.driver = driver;
    }

    /**
     * Get item from cart
     *
     * @param productUrl - change later
     * @return WebElement object
     */
    public WebElement getCartItem(String productUrl) {
        List<WebElement> checkoutItems = driver.findElements(checkoutItemsBy);

        for (WebElement checkoutItem: checkoutItems) {
            WebElement checkoutName = checkoutItem.findElement(By.className("product-name"));
            WebElement checkoutLink = checkoutName.findElement(By.xpath(".//a"));
            String checkoutUrl = checkoutLink.getDomProperty("href");

            Assert.assertNotNull(productUrl, "Target URL was null");
            if (productUrl.equals(checkoutUrl)) {
                System.out.println("Matching URL Found: " + checkoutUrl);
                return checkoutItem;
            }
        }

        return null;
    }

    /**
     * Remove item from cart
     *
     * @param item - item to remove
     */
    public void removeCartItem(WebElement item) {
        WebElement removeButton = item.findElement(removeButtonBy);
        removeButton.click();
    }
}
