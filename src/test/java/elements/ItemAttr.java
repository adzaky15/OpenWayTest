package elements;

public class ItemAttr {
    public String title;
    public String url;
    public String price;
    public int quantity;

    public ItemAttr(String title, String url, String price, int quantity) {
        this.title = (title != null ? title : "").trim().replaceAll("\\.+$", "");
        this.url = (url != null ? url : "").trim();
        this.price = (price != null ? price : "").split("or")[0].trim();
        this.quantity = quantity;
    }
}
