package com.target.onlinecourierservice.model;

public class ProductModel {
    private String ProductId;
    private String ProductName;
    private String ProductMerchant;
    private String MerchantID;
    private String ProductPrice;
    private String ProductImage;
    private String ProductDescription;
    private String ProductDiscountPrice;
    private String ProductDiscount;

    public ProductModel() {

    }

    public ProductModel(String productId, String productName, String productMerchant, String merchantID, String productPrice, String productImage, String productDescription, String productDiscountPrice, String productDiscount) {
        ProductId = productId;
        ProductName = productName;
        ProductMerchant = productMerchant;
        MerchantID = merchantID;
        ProductPrice = productPrice;
        ProductImage = productImage;
        ProductDescription = productDescription;
        ProductDiscountPrice = productDiscountPrice;
        ProductDiscount = productDiscount;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductDiscountPrice() {
        return ProductDiscountPrice;
    }

    public void setProductDiscountPrice(String productDiscountPrice) {
        ProductDiscountPrice = productDiscountPrice;
    }

    public String getProductDiscount() {
        return ProductDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        ProductDiscount = productDiscount;
    }

    public String getMerchantID() {
        return MerchantID;
    }

    public void setMerchantID(String merchantID) {
        MerchantID = merchantID;
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
