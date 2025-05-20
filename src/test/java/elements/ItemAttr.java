package elements;

public class ItemAttr {
    public String title;
    public String url;
    public String price;

    public ItemAttr(String title, String url, String price) {
        this.title = (title != null ? title : "").trim();
        this.url = (url != null ? url : "").trim();
        this.price = (price != null ? price : "").split("or")[0].trim();
    }
}
