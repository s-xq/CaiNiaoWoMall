package com.sxq.mall.bean;

/**
 * Created by SXQ on 2017/3/3.
 */

public class Banner extends BaseBean {

    private String name  ;
    private String imgUrl ;
    private String description ;

    public Banner(long id , String name, String imgUrl, String description) {
        this.id = id ;
        this.name = name;
        this.imgUrl = imgUrl;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String toString (){
        String str = "id = " + id +
                " , name = " + name +
                " , imgurl = " + imgUrl +
                " , decription = " + description ;
        return str ;
    }
}
