package com.farheen.grossary.model;

/**
 * Created by Parth Modi on 12/04/2017.
 */

public class ItemsPojo {
    private String item_id;
    private String item_img;
    private String item_name;
    private String item_price;
    private String item_qty;

    public String getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(String item_qty) {
        this.item_qty = item_qty;
    }

    public ItemsPojo() {
    }

    public ItemsPojo(String item_id, String item_img, String item_name, String item_qty, String item_price) {
        this.item_id = item_id;
        this.item_img = item_img;
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_qty = item_qty;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_img() {
        return item_img;
    }

    public void setItem_img(String item_img) {
        this.item_img = item_img;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }


}
