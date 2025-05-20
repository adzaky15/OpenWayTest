package pages;

import elements.ItemAttr;
import elements.Preloader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage {
    protected WebDriver driver;
    String pageUrl = "https://www.periplus.com/checkout/cart";

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

    public CartPage(WebDriver driver) {
        if (!pageUrl.equals(driver.getCurrentUrl())) {
            driver.get(pageUrl);
            Preloader.waitPreloader(driver);
        }
        this.driver = driver;
    }

    /**
     * Get item from cart
     *
     * @param product - change later
     * @return WebElement object
     */
    public WebElement getCartItem(ItemAttr product) {
        List<WebElement> cartItems = driver.findElements(checkoutItemsBy);

        for (WebElement cartItem : cartItems) {
            ItemAttr item = saveAttributes(cartItem);
            if (compareItemWithProduct(item, product)) {
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
        removeButton.click();

        Preloader.waitPreloader(driver);
    }

    /**
     * Save attributes of product
     *
     * @param item - product which attributes to save
     * @return ProductCard object
     */
    private ItemAttr saveAttributes(WebElement item) {
        WebElement itemName = item.findElement(itemNameBy);
        WebElement itemLink = itemName.findElement(itemLinkBy);
        WebElement itemPrice = itemName.findElement(itemCardBy).findElement(itemPriceBy);

        String title = itemLink.getDomProperty("innerText");
        String url = itemLink.getDomProperty("href");
        String price = itemPrice.getDomProperty("innerText");

        return new ItemAttr(title, url, price);
    }

    /**
     * Compare cart item attributes with product attributes
     *
     * @param cartItem    - item attributes from cart
     * @param itemAttr - product attributes to compare
     * @return true if equal
     */
    private boolean compareItemWithProduct(ItemAttr cartItem, ItemAttr itemAttr) {
        return nonEmptyEqual(cartItem.title, itemAttr.title) &&
                nonEmptyEqual(cartItem.url, itemAttr.url);
    }

    /**
     * Compare two non-empty string
     *
     * @param s1 - first string
     * @param s2 - second string
     * @return true if equal
     */
    private boolean nonEmptyEqual(String s1, String s2) {
        return !s1.isEmpty() && !s2.isEmpty() && s1.equals(s2);
    }
}