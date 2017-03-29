package com.sxq.mall.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import com.sxq.mall.Constants;
import com.sxq.mall.R;
import com.sxq.mall.adapter.HomeCampaignAdapter;
import com.sxq.mall.adapter.decoration.DividerItemDecoration;
import com.sxq.mall.bean.Banner;
import com.sxq.mall.bean.Campaign;
import com.sxq.mall.bean.HomeCampaign;

import com.sxq.mall.http.BaseCallBack;
import com.sxq.mall.http.OkHttpHelper;
import com.sxq.mall.http.SpotsCallBack;

import java.util.List;

/**
 * Created by SXQ on 2017/2/26.
 */

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private View view = null ;

    private SliderLayout mSliderLayout ;
    private RecyclerView mRecyclerView ;

    private OkHttpHelper mOkHttpHelper = OkHttpHelper.getInstance() ;

    private List<Banner> mBanners;

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container  , Bundle onSavedInstanceState){
        view = inflater.inflate(R.layout.fragment_home , null);
        Log.e(TAG, "onCreateView: " );
        requestImages();

        initRecyclerView();
        return view ;
    }

    private void requestImages(){

        String url ="http://112.124.22.238:8081/course_api/banner/query?type=1";

        mOkHttpHelper.get(url , new SpotsCallBack<List<Banner>>(getContext()){
            @Override
            public void onFailure(Request request, Exception ex) {

            }

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBanners = banners ;
                initSliderLayout(mBanners);
            }

            @Override
            public void onError(Response response, int code, Exception ex) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        stopSliderLayout();
        super.onDestroy();
    }

    private void initSliderLayout(List<Banner> bannerList){
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider);

        if(bannerList != null ){
            for(Banner banner : bannerList){
                TextSliderView textSliderView = new TextSliderView(getActivity());
                textSliderView.image(banner.getImgUrl());
                textSliderView.description(banner.getName());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(textSliderView);
            }
        }

        //底部切换指示图标
        //xml中定义的indicator应该放在SliderLayout外面，否则无法显示。
        PagerIndicator indicator = (PagerIndicator)view.findViewById(R.id.custom_indicator);
        mSliderLayout.setCustomIndicator(indicator);
        mSliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
//        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);//指示图标的位置
        mSliderLayout.setDuration(3000);

        //转场动画
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

   private void stopSliderLayout(){
       if(mSliderLayout != null){
           mSliderLayout.stopAutoCycle();
       }
   }

    private void initRecyclerView(){
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        
        mOkHttpHelper.get(Constants.API.CAMPAIGN_HOME, new BaseCallBack<List<HomeCampaign>>() {
            @Override
            public void onBeforeRequest(Request request) {
                
            }

            @Override
            public void onFailure(Request request, Exception ex) {

            }

            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {
                initRecyclerViewData(homeCampaigns);
            }

            @Override
            public void onError(Response response, int code, Exception ex) {

            }

            @Override
            public void onResponse(Response response) {

            }
        });

    }

    private void initRecyclerViewData(List<HomeCampaign> homeCampaigns){

        HomeCampaignAdapter homeCampaignAdapter = new HomeCampaignAdapter(homeCampaigns , getContext());

        homeCampaignAdapter.setOnCampaignClickListener(new HomeCampaignAdapter.OnCampaignClickListener() {
            @Override
            public void onClick(View v, Campaign campaign) {
                Toast.makeText(getContext() , campaign.getTitle() , Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setAdapter(homeCampaignAdapter);
        
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext() , DividerItemDecoration.VERTICAL_LIST));
        
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        
    }
}
