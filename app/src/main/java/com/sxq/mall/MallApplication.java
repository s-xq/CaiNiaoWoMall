package com.sxq.mall;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by SXQ on 2017/3/5.
 */

public class MallApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Fresco
        Fresco.initialize(this);

    }
}
