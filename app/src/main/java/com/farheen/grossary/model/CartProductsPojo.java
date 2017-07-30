package com.farheen.grossary.model;

/**
 * Created by Parth Modi on 30/03/2017.
 */

public class CartProductsPojo {
    private int productImg;
    private String productName;
    private String productDetails;
    private String productPrice;
    private String productItems;

    public CartProductsPojo(int productImg, String productName, String productDetails, String productPrice, String productItems) {
        this.productImg = productImg;
        this.productName = productName;
        this.productDetails = productDetails;
        this.productPrice = productPrice;
        this.productItems = productItems;
    }

    public int getProductImg() {
        return productImg;
    }

    public void setProductImg(int productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductItems() {
        return productItems;
    }

    public void setProductItems(String productItems) {
        this.productItems = productItems;
    }


}
