package pages;

import com.google.common.primitives.Ints;
import elements.ItemAttr;
import elements.Preloader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class CartPage {
    protected WebDriver driver;
    protected Wait<WebDriver> wait;

    private final static String pageUrl = "https://www.periplus.com/checkout/cart";
    private final static Duration defaultWait = Duration.ofSeconds(10);

    // <div class="row row-cart-product"></div>
    private final By checkoutItemsBy = By.className("row-cart-product");
    // <a class="btn btn-cart-remove" href="http://www.periplus.com/checkout/cart?remove=43518459">Remove</a>
    private final By removeButtonBy = By.className("btn-cart-remove");

    // <p class="product-name limit-lines"></p>
    private final By itemNameBy = By.className("product-name");
    // <a href="" style="font-size:15px;"></a>
    private final By itemLinkBy = By.xpath(".//a");
    // <div class="col-lg-10 col-9"></div>
    private final By itemCardBy = By.xpath("./../..");
    // <div class="row"></div>
    private final By itemPriceBy = By.xpath(".//div[3]");
    // <input type="text" class="input-number text-center" data-min="1" data-max="500" name="quantity[43518459]" value="3" size="3" id="qty_43518459" onchange="update_total(43518459)">
    private final By itemQuantityBy = By.className("input-number");

    public CartPage(WebDriver driver) {
        if (!pageUrl.equals(driver.getCurrentUrl())) {
            driver.get(pageUrl);
            Preloader.waitPreloader(driver);
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, defaultWait);
    }

    /**
     * Get item from cart
     *
     * @param product - product attributes to find
     * @return WebElement object
     */
    public WebElement getCartItem(ItemAttr product) {
        List<WebElement> cartItems = driver.findElements(checkoutItemsBy);

        for (WebElement cartItem : cartItems) {
            if (PagesHelper.compareItemWithProduct(saveAttributes(cartItem), product)) {
                return cartItem;
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
        wait.until(ExpectedConditions.elementToBeClickable(removeButton));
        removeButton.click();

        Preloader.waitPreloader(driver);
    }

    /**
     * Save attributes of item
     *
     * @param item - item which attributes to save
     * @return ItemAttr object
     */
    public ItemAttr saveAttributes(WebElement item) {
        WebElement itemName = item.findElement(itemNameBy);
        WebElement itemLink = itemName.findElement(itemLinkBy);
        WebElement itemCard = itemName.findElement(itemCardBy);
        WebElement itemPrice = itemCard.findElement(itemPriceBy);
        WebElement itemQuantity = itemCard.findElement(itemQuantityBy);

        String title = itemLink.getDomProperty("innerText");
        String url = itemLink.getDomProperty("href");
        String price = itemPrice.getDomProperty("innerText");
        int quantity = Optional.ofNullable(itemQuantity.getDomProperty("value"))
                .map(Ints::tryParse)
                .orElse(0);

        return new ItemAttr(title, url, price, quantity);
    }
}