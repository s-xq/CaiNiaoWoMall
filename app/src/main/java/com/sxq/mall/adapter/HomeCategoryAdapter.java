package com.sxq.mall.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxq.mall.R;
import com.sxq.mall.bean.HomeCategory;

import java.util.List;

/**
 * Created by SXQ on 2017/2/27.
 */

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    private static final String TAG = "HomeCategoryAdapter";
    private static int VIEW_TYPE_L = 0;
    private static int VIEW_TYPE_R = 1;

    private List<HomeCategory> mDatas;
    private LayoutInflater mInflater ;
    public HomeCategoryAdapter(List<HomeCategory> datas){
        Log.e(TAG, "HomeCategoryAdapter: ");
        this.mDatas = datas ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        Log.e(TAG, "onCreateViewHolder: " );
        if(viewType == VIEW_TYPE_R){
            return new ViewHolder(mInflater.inflate(R.layout.template_home_cardview2 , parent , false));
        }
        return new ViewHolder(mInflater.inflate(R.layout.template_home_cardview , parent , false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeCategory category = mDatas.get(position);
        holder.textTitle.setText(category.getName());
        holder.imageViewBig.setImageResource(category.getImgBig());
        holder.imageViewSmallTop.setImageResource(category.getImgSmallTop());
        holder.imageViewSmallBottom.setImageResource(category.getImgSmallBottom());
        Log.e(TAG, "onBindViewHolder: " );
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position){
        if(position % 2 == 0)
            return VIEW_TYPE_R ;
        else return VIEW_TYPE_L;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textTitle ;
        ImageView imageViewBig  ;
        ImageView imageViewSmallTop ;
        ImageView imageViewSmallBottom ;

        public ViewHolder(View itemView){
            super(itemView);
            Log.e(TAG, "ViewHolder: " );
            textTitle = (TextView)itemView.findViewById(R.id.text_title);
            imageViewBig = (ImageView)itemView.findViewById(R.id.imgview_big);
            imageViewSmallTop = (ImageView)itemView.findViewById(R.id.imgview_small_top);
            imageViewSmallBottom = (ImageView)itemView.findViewById(R.id.imgview_small_bottom);
        }
    }
}
