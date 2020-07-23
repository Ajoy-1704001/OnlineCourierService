package com.target.onlinecourierservice.model;

public class ProductModel {
    private String ProductId;
    private String ProductName;
    private String ProductMerchant;
    private String ProductPrice;
    private String ProductImage;

    public ProductModel() {

    }

    public ProductModel(String productId, String productName, String productMerchant, String productPrice, String productImage) {
        ProductId = productId;
        ProductName = productName;
        ProductMerchant = productMerchant;
        ProductPrice = productPrice;
        ProductImage = productImage;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductMerchant() {
        return ProductMerchant;
    }

    public void setProductMerchant(String productMerchant) {
        ProductMerchant = productMerchant;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }
}
