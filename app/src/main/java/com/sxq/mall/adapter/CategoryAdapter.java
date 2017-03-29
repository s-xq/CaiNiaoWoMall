package com.sxq.mall.adapter;

import android.content.Context;

import com.sxq.mall.R;
import com.sxq.mall.bean.Category;

import java.util.List;

/**
 * Created by SXQ on 2017/3/12.
 */

public class CategoryAdapter extends SimpleAdapter<Category> {

    public CategoryAdapter(Context context , List<Category> categories){
        super(context , categories , R.layout.template_single_text);
    }


    @Override
    public void bindData(BaseViewHolder holder, Category category) {
        holder.getTextView(R.id.textView).setText(category.getName());
    }
}
