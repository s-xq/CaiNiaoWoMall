package com.sxq.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sxq.mall.R;
import com.sxq.mall.bean.Campaign;
import com.sxq.mall.bean.HomeCampaign;

import java.util.List;

/**
 * Created by SXQ on 2017/3/4.
 */

public class HomeCampaignAdapter extends RecyclerView.Adapter<HomeCampaignAdapter.ViewHolder> {
    
    private Context mContext ; 
    
    private static int VIEW_TYPE_L = 0;
    private static int VIEW_TYPE_R = 1;

    private List<HomeCampaign> mDatas;
    
    private LayoutInflater mInflater ;

    private OnCampaignClickListener mClickListener ;

    public HomeCampaignAdapter(List<HomeCampaign> datas , Context context){
        this.mDatas = datas ;
        this.mContext = context ;
    }

    public void setOnCampaignClickListener(OnCampaignClickListener clickListener){
        this.mClickListener = clickListener ;
    }

    @Override
    public HomeCampaignAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        if(viewType == VIEW_TYPE_R){
            return new HomeCampaignAdapter.ViewHolder(mInflater.inflate(R.layout.template_home_cardview2 , parent , false));
        }
        return new HomeCampaignAdapter.ViewHolder(mInflater.inflate(R.layout.template_home_cardview , parent , false));
    }

    @Override
    public void onBindViewHolder(HomeCampaignAdapter.ViewHolder holder, int position) {
        HomeCampaign homeCampaign = mDatas.get(position);
        holder.textTitle.setText(homeCampaign.getTitle());
        Picasso.with(mContext).load(homeCampaign.getCpOne().getImgUrl()).into(holder.imageViewBig);
        Picasso.with(mContext).load(homeCampaign.getCpTwo().getImgUrl()).into(holder.imageViewSmallTop);
        Picasso.with(mContext).load(homeCampaign.getCpThree().getImgUrl()).into(holder.imageViewSmallBottom);
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


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textTitle ;
        ImageView imageViewBig  ;
        ImageView imageViewSmallTop ;
        ImageView imageViewSmallBottom ;

        public ViewHolder(View itemView){
            super(itemView);
            textTitle = (TextView)itemView.findViewById(R.id.text_title);
            imageViewBig = (ImageView)itemView.findViewById(R.id.imgview_big);
            imageViewSmallTop = (ImageView)itemView.findViewById(R.id.imgview_small_top);
            imageViewSmallBottom = (ImageView)itemView.findViewById(R.id.imgview_small_bottom);

            imageViewBig.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
            imageViewSmallBottom.setOnClickListener(this);

        }

        @Override
        public void onClick(View v){
            HomeCampaign homeCampaign = mDatas.get(getLayoutPosition());
            if(mClickListener != null){
                switch(v.getId()){
                    case R.id.imgview_big :
                        mClickListener.onClick(v , homeCampaign.getCpOne());
                        break ;
                    case R.id.imgview_small_top :
                        mClickListener.onClick(v , homeCampaign.getCpTwo());
                        break;
                    case R.id.imgview_small_bottom :
                        mClickListener.onClick(v , homeCampaign.getCpThree());
                        break;
                }
            }
        }

    }

    public  interface OnCampaignClickListener {

        void onClick (View v , Campaign campaign);

    }
}
