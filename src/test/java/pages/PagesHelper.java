package pages;

import elements.ItemAttr;

public class PagesHelper {
    /**
     * Compare cart item attributes with product attributes
     *
     * @param cartItemAttr    - cart item attributes from cart
     * @param productAttr - product attributes to compare
     * @return true if equal
     */
    public static boolean compareItemWithProduct(ItemAttr cartItemAttr, ItemAttr productAttr) {
        return nonEmptyEqual(cartItemAttr.title, productAttr.title) &&
                nonEmptyEqual(cartItemAttr.url, productAttr.url) &&
                nonEmptyEqual(cartItemAttr.price, productAttr.price);
    }

    /**
     * Compare two non-empty string
     *
     * @param s1 - first string
     * @param s2 - second string
     * @return true if equal
     */
    private static boolean nonEmptyEqual(String s1, String s2) {
        return !s1.isEmpty() && !s2.isEmpty() && s1.equals(s2);
    }
}
