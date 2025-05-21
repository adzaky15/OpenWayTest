package pages;

import elements.Preloader;
import elements.ItemAttr;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class NewReleasesPage {
    protected WebDriver driver;
    protected Wait<WebDriver> wait;

    private final static String pageUrl = "https://www.periplus.com/index.php?route=product/category&anl=103";
    private final static int addToCartInc = 1;
    private final static Duration defaultWait = Duration.ofSeconds(20);

    // <div class="row row-category row-categor-grid"></div>
    private final By productListBy = By.xpath("/html/body/section/div/div/div[3]/div[3]");
    // <div class="single-product" style="margin-right:0px;margin-top:20px;padding:5px 5px 0px 5px;height:95%;width:100%;border-radius:5px;margin-bottom:10px;"></div>
    private final By productBy = By.className("single-product");
    // <a title="Add to cart" onclick="update_total(43518459)" class="addtocart addtocart-cat"></a>
    private final By addToCartBy = By.className("addtocart");

    // <div class="product-content product-contents title-product-cat"></div>
    private final By productNameBy = By.className("title-product-cat");
    // <a href=""></a>
    private final By productLinkBy = By.xpath(".//h3/a");
    // <div class="product-price" style="margin: 6px 0 6px 0;"></div>
    private final By productPriceDivBy = By.className("product-price");
    // <div style="font-size:100%;color:#000000;font-weight:600;">Rp 189,000</div>
    private final By productPriceBy = By.xpath(".//div");

    public NewReleasesPage(WebDriver driver){
        if (!pageUrl.equals(driver.getCurrentUrl())) {
            driver.get(pageUrl);
            Preloader.waitPreloader(driver);
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, defaultWait);
    }

    /**
     * Return first product on list
     *
     * @return WebElement object
     */
    public WebElement getFirstProduct() {
        WebElement productList = driver.findElement(productListBy);
        return productList.findElement(productBy);
    }

    /**
     * Get product from product list
     *
     * @param productAttr - product attributes to find
     * @return WebElement object
     */
    public WebElement getProductByAttr(ItemAttr productAttr) {
        WebElement productList = driver.findElement(productListBy);
        List<WebElement> productItems = productList.findElements(productBy);

        for (WebElement product : productItems) {
            if (PagesHelper.compareItemWithProduct(saveAttributes(product), productAttr)) {
                return product;
            }
        }

        return null;
    }

    /**
     * Add item to cart
     *
     * @param product - product to add
     */
    public void addItemToCart(WebElement product) {
        WebElement addToCart = product.findElement(addToCartBy);
        wait.until(ExpectedConditions.elementToBeClickable(addToCart));
        addToCart.click();

        Preloader.waitPreloader(driver);
    }

    /**
     * Save attributes of product
     *
     * @param product - product which attributes to save
     * @return ItemAttr object
     */
    public ItemAttr saveAttributes(WebElement product) {
        WebElement productName = product.findElement(productNameBy).findElement(productLinkBy);
        WebElement productPrice = product.findElement(productPriceDivBy).findElement(productPriceBy);

        String title = productName.getDomProperty("innerText");
        String url = productName.getDomProperty("href");
        String price = productPrice.getDomProperty("innerText");

        return new ItemAttr(title, url, price, addToCartInc);
    }
}
