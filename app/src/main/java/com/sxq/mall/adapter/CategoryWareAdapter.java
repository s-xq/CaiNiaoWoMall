package com.sxq.mall.adapter;

import android.content.Context;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sxq.mall.R;
import com.sxq.mall.bean.WareInCategory;

import java.util.List;

/**
 * Created by SXQ on 2017/3/12.
 */

public class CategoryWareAdapter extends SimpleAdapter<WareInCategory> {

    public CategoryWareAdapter(Context context , List<WareInCategory> wareInCategories){
        super(context, wareInCategories , R.layout.template_grid_wares );
    }

    @Override
    public void bindData(BaseViewHolder holder, WareInCategory wareInCategory) {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);
        simpleDraweeView.setImageURI(wareInCategory.getImgUrl());
        holder.getTextView(R.id.text_title).setText(wareInCategory.getName());
        holder.getTextView(R.id.text_price).setText(wareInCategory.getPrice());
    }
}
