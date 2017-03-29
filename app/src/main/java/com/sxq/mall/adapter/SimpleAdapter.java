package com.sxq.mall.adapter;

import android.content.Context;


import java.util.List;

/**
 * Created by SXQ on 2017/3/11.
 */

public abstract class SimpleAdapter<T> extends BaseAdapter<T , BaseViewHolder> {
    public SimpleAdapter(Context context, List<T> datas, int layoutResId) {
        super(context, datas, layoutResId);
    }
    public SimpleAdapter(Context context , int layoutResId){
        super(context , layoutResId);
    }
}
