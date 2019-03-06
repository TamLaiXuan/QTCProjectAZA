package qtc.project.aza.model;

import java.io.Serializable;

public class ProductResponseModel implements Serializable {

    private String id_product;
    private String id_order;
    private String name_product;
    private String price;
    private String unit_name;
    private String quantity;
    private String quantity_purchased_1;
    private String quantity_purchased_2;
    private String total_purchase;
    private String price_purchased;
    private String code_product;
    private String name_supplier;
    private String check_box;

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    public String getCode_product() {
        return code_product;
    }

    public void setCode_product(String code_product) {
        this.code_product = code_product;
    }

    public String getName_supplier() {
        return name_supplier;
    }

    public void setName_supplier(String name_supplier) {
        this.name_supplier = name_supplier;
    }

    public String getCheck_box() {
        return check_box;
    }

    public void setCheck_box(String check_box) {
        this.check_box = check_box;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getQuantity_purchased_1() {
        return quantity_purchased_1;
    }

    public void setQuantity_purchased_1(String quantity_purchased_1) {
        this.quantity_purchased_1 = quantity_purchased_1;
    }

    public String getQuantity_purchased_2() {
        return quantity_purchased_2;
    }

    public void setQuantity_purchased_2(String quantity_purchased_2) {
        this.quantity_purchased_2 = quantity_purchased_2;
    }

    public String getTotal_purchase() {
        return total_purchase;
    }

    public void setTotal_purchase(String total_purchase) {
        this.total_purchase = total_purchase;
    }

    public String getPrice_purchased() {
        return price_purchased;
    }

    public void setPrice_purchased(String price_purchased) {
        this.price_purchased = price_purchased;
    }
}
