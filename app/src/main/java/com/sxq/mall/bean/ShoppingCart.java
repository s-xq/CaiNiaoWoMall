package com.sxq.mall.bean;

import java.io.Serializable;

/**
 * Created by SXQ on 2017/3/19.
 */

public class ShoppingCart extends Ware implements Serializable {

    private int count ;

    private boolean checked = false ;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String toString(){
        String format = "[count = %d , isChecked = %b , ware = %s ]";
        return String.format(format , count , isChecked() ,super.toString());
    }
}
