package com.sxq.mall.bean;

import java.io.Serializable;

/**
 * Created by SXQ on 2017/2/27.
 */

public class BaseBean implements Serializable {

    protected long id ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
