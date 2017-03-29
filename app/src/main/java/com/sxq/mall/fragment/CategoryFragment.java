package com.sxq.mall.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sxq.mall.Constants;
import com.sxq.mall.R;
import com.sxq.mall.adapter.BaseAdapter;
import com.sxq.mall.adapter.CategoryAdapter;
import com.sxq.mall.adapter.CategoryWareAdapter;
import com.sxq.mall.adapter.decoration.DividerGridItemDecoration;
import com.sxq.mall.adapter.decoration.DividerItemDecoration;
import com.sxq.mall.bean.Banner;
import com.sxq.mall.bean.Category;
import com.sxq.mall.bean.Page;
import com.sxq.mall.bean.WareInCategory;
import com.sxq.mall.http.BaseCallBack;
import com.sxq.mall.http.OkHttpHelper;
import com.sxq.mall.http.SpotsCallBack;

import java.util.List;

/**
 * Created by SXQ on 2017/2/26.
 */

public class CategoryFragment extends Fragment {
    private static final String TAG = "CategoryFragment";
    private View view ;

    @ViewInject(R.id.recyclerview_category)
    private RecyclerView mRecyclerView ;

    @ViewInject(R.id.slider)
    private SliderLayout mSliderLayout ;

    @ViewInject(R.id.recyclerview_wares)
    private RecyclerView mRecyclerViewWares ;

    @ViewInject(R.id.refresh_layout)
    private MaterialRefreshLayout mMaterialRefreshLayout ;

    private CategoryAdapter mCategoryAdapter ;
    private CategoryWareAdapter categoryWareAdapter ;
    private OkHttpHelper mOkHttpHelper = OkHttpHelper.getInstance() ;

    private int curPage = 1;
    private int pageSize  = 10 ;
    private int totalPage = 1 ;
    private long categoryId = 1;

    private static final int STATE_NORMAL = 0 ;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_LOADMORE = 2 ;

    private int state = STATE_NORMAL ;


    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle onSavedInstanceState){
        view = inflater.inflate(R.layout.fragment_category , null);

        ViewUtils.inject(this , view);

        init() ;

        return view ;
    }

    private void init(){
        requestCategoryData();
        requestBannerData();
        initMaterialRefreshLayout();
    }
    private void requestCategoryData(){
        String url = Constants.API.CATEGORY_LIST ;
        mOkHttpHelper.get(url, new SpotsCallBack<List<Category>>(getContext()) {
            @Override
            public void onFailure(Request request, Exception ex) {

            }

            @Override
            public void onSuccess(Response response, List<Category> categories) {
                showCategoryData(categories);

                if(categories != null & categories.size() > 0 ){
                    categoryId = categories.get(0).getId();
                    requestWares(categoryId);
                }
            }

            @Override
            public void onError(Response response, int code, Exception ex) {

            }
        });

    }

    private void showCategoryData(final List<Category> categories){
        mCategoryAdapter = new CategoryAdapter(getContext() , categories);
        mCategoryAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Category category = mCategoryAdapter.getItem(position) ;
                categoryId = category.getId() ;
                curPage = 1;
                state = STATE_NORMAL ;
                requestWares(categoryId);
            }
        });
        mRecyclerView.setAdapter(mCategoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext() , DividerItemDecoration.VERTICAL_LIST));
    }

    private void requestBannerData(){
        String url = Constants.API.BANNER + "?type=1" ;
        mOkHttpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()) {
            @Override
            public void onFailure(Request request, Exception ex) {

            }

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                showBanner(banners);
            }

            @Override
            public void onError(Response response, int code, Exception ex) {

            }
        });
    }

    private void showBanner(List<Banner> banners){
        if(banners != null){
            for(Banner banner : banners ){
                TextSliderView textSliderView = new TextSliderView(getActivity());
                textSliderView.image(banner.getImgUrl());
                textSliderView.description(banner.getDescription());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(textSliderView);
            }

            mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

            mSliderLayout.setDuration(3000);

            mSliderLayout.setPresetTransformer(SliderLayout.Transformer.CubeIn);

        }

    }

    private void initMaterialRefreshLayout(){
        mMaterialRefreshLayout.setLoadMore(true);

        mMaterialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                if(curPage < totalPage)
                    loadMoreData();
                else{
                    Toast.makeText(getContext() , "Not more Wares" ,Toast.LENGTH_SHORT).show();
                    materialRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    private void requestWares(long categoryId ){
        String url = Constants.API.WARES_LIST + "?curPage=" + curPage + "&pageSize=" + pageSize + "&categoryId=" + categoryId ;

        mOkHttpHelper.get(url, new BaseCallBack<Page<WareInCategory>>() {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Request request, Exception ex) {

            }

            @Override
            public void onSuccess(Response response, Page<WareInCategory> wareInCategoryPage) {
                curPage = wareInCategoryPage.getCurrentPage() ;
                int tmp1 = wareInCategoryPage.getTotalCount() / wareInCategoryPage.getPageSize() ;
                int tmp2 = wareInCategoryPage.getTotalCount() % wareInCategoryPage.getPageSize() ;
                totalPage = tmp1 + (tmp2 > 0 ? 1 : 0) ;
                showWares(wareInCategoryPage.getList());
            }

            @Override
            public void onError(Response response, int code, Exception ex) {

            }

            @Override
            public void onResponse(Response response) {

            }
        });
    }

    private void showWares(List<WareInCategory> wares){
        switch(state){
            case STATE_NORMAL :
                if(categoryWareAdapter == null) {
                    categoryWareAdapter = new CategoryWareAdapter(getContext(), wares);
                    mRecyclerViewWares.setAdapter(categoryWareAdapter);
                    mRecyclerViewWares.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    mRecyclerViewWares.addItemDecoration(new DividerGridItemDecoration(getContext()));
                }else{
                    categoryWareAdapter.clearData();
                    categoryWareAdapter.addData(wares);
                }
                break ;

            case STATE_REFRESH:
                categoryWareAdapter.clearData();
                categoryWareAdapter.addData(wares);
                mRecyclerViewWares.scrollToPosition(0);
                mMaterialRefreshLayout.finishRefresh();
                Log.e(TAG, "showWares: finishRefresh");
                break;

            case STATE_LOADMORE :
                int currentPosition = categoryWareAdapter.getItemCount();
                categoryWareAdapter.addData(currentPosition , wares);
                mRecyclerViewWares.scrollToPosition(currentPosition);
                mMaterialRefreshLayout.finishRefreshLoadMore();
                break;
        }

    }

    private void refreshData(){
        curPage = 1 ;
        state = STATE_REFRESH;
        requestWares(categoryId);
    }

    private void loadMoreData(){
        curPage += 1 ;
        state = STATE_LOADMORE ;
        requestWares(categoryId);
    }

}
