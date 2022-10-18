import java.nio.charset.CoderResult;
import java.util.Objects;

public class Products {

    private String category;
    private String type;
    private String brandName;
    private Long eaCode;
    private double price;

    public Products(String category, String type, String brandName, Long eaCode, double price) {
        this.category = category;
        this.type = type;
        this.brandName = brandName;
        this.eaCode = eaCode;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getEaCode() {
        return eaCode;
    }

    public void setEaCode(Long eaCode) {
        this.eaCode = eaCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return Double.compare(products.price, price) == 0 && Objects.equals(category, products.category) && Objects.equals(type, products.type) && Objects.equals(brandName, products.brandName) && Objects.equals(eaCode, products.eaCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, type, brandName, eaCode, price);
    }

    @Override
    public String toString() {
        return "Category = " + category +
                ", type =  " + type +
                ", brandName = " + brandName +
                ", ea-Code = " + eaCode +
                ", price = " + price;
    }
}
