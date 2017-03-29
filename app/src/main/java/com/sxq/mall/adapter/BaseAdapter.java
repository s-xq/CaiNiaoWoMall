package com.sxq.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SXQ on 2017/3/11.
 */

public abstract class BaseAdapter<T , H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    protected Context mContext ;
    protected List<T> mDatas ;
    protected int mLayoutResId ;

    protected OnItemClickListener listener ;

    public BaseAdapter(Context context , int layoutResId){
        this(context , null , layoutResId);
    }
    public BaseAdapter(Context context , List<T> datas , int layoutResId ){
        this.mContext = context ;
        this.mDatas = ((datas == null ) ? new ArrayList<T>() : datas ) ;
        this.mLayoutResId = layoutResId ;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutResId, parent, false);
        return new BaseViewHolder(view , listener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T t = getItem(position);
        bindData(holder , t) ;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener ;
    }

    public T getItem (int position){
        return mDatas.get(position);
    }

    public void clearData(){
        mDatas.clear();
        notifyItemRangeChanged(0 , mDatas.size());
    }

    public void addData(List<T> datas){
        addData(0 , datas);
    }

    public void addData(int position , List<T> datas ){
        if(datas != null && datas.size() > 0 ){
            mDatas.addAll(position , datas) ;
            notifyItemRangeChanged(position , mDatas.size());
        }
    }

    public abstract void bindData(BaseViewHolder holder , T t);

    public interface OnItemClickListener{
        void onItemClick(View v , int position);
    }
}
