package com.sxq.mall.bean;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SXQ on 2017/2/26.
 */

public class Tab {
    private int title ;
    private int icon ;
    private Class Fragment ;

    public Tab(int title, int icon, Class fragment) {
        this.title = title;
        this.icon = icon;
        Fragment = fragment;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getFragment() {
        return Fragment;
    }

    public void setFragment(Class fragment) {
        Fragment = fragment;
    }
}
