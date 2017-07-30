package com.farheen.grossary.model;

/**
 * Created by Parth Modi on 29/03/2017.
 */

public class CategoryPojo {
//    @SerializedName("id")
    private String cat_id;
//    @SerializedName("cate_name")
    private String CategoryName;
//    @SerializedName("img")
    private String imageSrc;
//    @SerializedName("desc")
    private String CategoryDesc;





    public CategoryPojo() {
    }

    public CategoryPojo(String imageSrc, String categoryName, String categoryDesc, String cat_id) {
        this.imageSrc = imageSrc;
        CategoryName = categoryName;
        CategoryDesc = categoryDesc;
        this.cat_id = cat_id;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryDesc() {
        return CategoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        CategoryDesc = categoryDesc;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }


    /*public CategoryPojo(int imageSrc, String categoryName) {
        this.imageSrc = imageSrc;
        CategoryName = categoryName;
    }*/





}
