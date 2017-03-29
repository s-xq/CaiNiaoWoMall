package com.sxq.mall.bean;

/**
 * Created by SXQ on 2017/3/5.
 */

import java.io.Serializable;

/**
 * 热卖商品的bean
 *
 * 对应API : WARES_HOT
 *
 */
public class Ware implements Serializable{

    private long id ;

    private String name ;

    private String imgUrl ;

    private String description ;

    private float price ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String toString(){
        return "id = " + id
                + "\nname = " + name
                + "\nimageUrl = " + imgUrl
                + "\ndescription = " + description ;
    }
}
