package com.sxq.mall.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sxq.mall.http.OkHttpHelper;
import com.sxq.mall.http.SpotsCallBack;
import com.sxq.mall.bean.Page;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SXQ on 2017/3/19.
 */

public class Pager {
    private static final String TAG = "Pager";
    private static Builder builder;

    private OkHttpHelper mOkHttpHelper;

    private  static final int STATE_NORMAL=0;
    private  static final int STATE_REFREH=1;
    private  static final int STATE_MORE=2;

    private int state = STATE_NORMAL;

    private Pager(){
        mOkHttpHelper = OkHttpHelper.getInstance();
        initRefreshLayout();

    }

    public static Builder newBuilder(){
        builder = new Builder();
        return builder;
    }

    /**
     * 请求数据
     */
    public void request(){
        requestData();
    }

    private void initRefreshLayout(){
        builder.mRefreshLayout.setLoadMore(builder.canLoadMore);
        builder.mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                builder.mRefreshLayout.setLoadMore(builder.canLoadMore);
                refersh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                if(builder.pageIndex < builder.totalPage){
                    loadMore();
                }
                else{
                    Toast.makeText(builder.mContext, "无更多数据", Toast.LENGTH_LONG).show();
                    materialRefreshLayout.finishRefreshLoadMore();
                    materialRefreshLayout.setLoadMore(false);
                }

            }
        });
    }

    /**
     * 请求数据
     */
    private void requestData(){
        mOkHttpHelper.get(buildUrl() , new RequestCallback(builder.mContext));
    }

    /**
     * 刷新数据
     */
    private void refersh(){
        state = STATE_REFREH ;
        builder.pageIndex =1;
        requestData();
    }

    /**
     * 加载更多
     */
    private void loadMore(){
        state=STATE_MORE;
        builder.pageIndex ++ ;
        requestData();
    }

    /**
     * 显示数据
     *
     * @param datas 已经请求到的数据
     * @param totalPage
     * @param totalCount
     * @param <T>
     */
    private <T> void showData(List<T> datas , int totalPage , int totalCount){
        Log.e(TAG, "showData: state = " + state + ",totalPage = " + totalPage + ",totalCount = " + totalCount + ",datas = " + datas.toString());
        if(datas == null|| datas.size() <= 0){
            Toast.makeText(builder.mContext,"加载不到数据",Toast.LENGTH_LONG).show();
            return;
        }

        if(state == STATE_NORMAL){
            if(builder.onPageListener != null){
                builder.onPageListener.load(datas , totalPage ,totalCount);
            }
        }
        else if(state == STATE_REFREH){
            Log.e(TAG, "showData: finishRefreshing" );
            builder.mRefreshLayout.finishRefresh();
            if(builder.onPageListener != null){
                builder.onPageListener.refresh(datas , totalPage ,totalCount);
            }
        }
        else if(state == STATE_MORE){
            Log.e(TAG, "showData: finishRefreshLoadMore" );
            builder.mRefreshLayout.finishRefreshLoadMore();
            if(builder.onPageListener != null){
                builder.onPageListener.loadMore(datas , totalPage , totalCount);
            }
        }
    }

    private String buildUrl (){
        String url = builder.mUrl + "?" + buildUrlParams() ;
        return url ;
    }

    private String buildUrlParams (){
        HashMap<String, Object> map = builder.params;

        map.put("curPage",builder.pageIndex);
        map.put("pageSize",builder.pageSize);

        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String , Object > entry : map.entrySet()){
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }

        String s = sb.toString();
        if(s.endsWith("&")){
            s = s.substring(0 , s.length() -1 );
        }
        return s ;
    }

    public static class Builder{
        private Context mContext;
        private Type mType;
        private String mUrl;

        private MaterialRefreshLayout mRefreshLayout;

        private boolean canLoadMore;

        private int totalPage = 1;
        private int pageIndex = 1;
        private int pageSize = 10;

        private HashMap<String,Object> params = new HashMap<>(5);

        private OnPageListener onPageListener;

        public Builder setUrl(String mUrl) {
            this.mUrl = mUrl;
            return this;
        }

        public Builder setMaterialRefreshLayout(MaterialRefreshLayout mRefreshLayout) {
            this.mRefreshLayout = mRefreshLayout;
            return this;
        }

        public Builder setCanLoadMore(boolean canLoadMore) {
            this.canLoadMore = canLoadMore;
            return this;
        }

        public Builder setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder  putParam(String key,Object value){
            params.put(key,value);
            return builder;
        }

        public Builder setOnPageListener(OnPageListener onPageListener) {
            this.onPageListener = onPageListener;
            return this;
        }

        public Pager build(Context context , Type type){
            this.mContext = context ;
            this.mType = type ;

            validate();

            return new Pager();
        }

        private void validate(){
            if(mContext == null )
                throw new RuntimeException("context can not be null");

            if(mType == null)
                throw new RuntimeException("type can not be null");

            if(mUrl == null || "".equals(this.mUrl))
                throw new RuntimeException("url can not be null");

            if(this.mRefreshLayout == null)
                throw new RuntimeException("MaterialRefreshLayout can not be null");
        }
    }

    class RequestCallback <T> extends SpotsCallBack<Page<T>>{

        public RequestCallback(Context context) {
            super(context);
            /**
             * 用于OkHttpHelper的对json的解析参数类型，非常重要
             */
            super.mType = builder.mType;
        }

        @Override
        public void onFailure(Request request, Exception ex) {
            dismissDialog();

            Toast.makeText(builder.mContext,"请求出错："+ ex.getMessage() , Toast.LENGTH_LONG).show();

            if(STATE_REFREH==state)   {
                builder.mRefreshLayout.finishRefresh();
            }
            else  if(STATE_MORE == state){
                builder.mRefreshLayout.finishRefreshLoadMore();
            }
        }

        @Override
        public void onSuccess(Response response, Page page) {
            //防止API出错
            page.setTotalPage(page.getTotalCount() / page.getPageSize() + (page.getTotalCount() % page.getPageSize() == 0 ? 0 : 1) );

            builder.pageIndex = page.getCurrentPage();
            builder.pageSize = page.getPageSize();
            builder.totalPage = page.getTotalPage();

            showData(page.getList(),page.getTotalPage(),page.getTotalCount());
        }

        @Override
        public void onError(Response response, int code, Exception ex) {
            Toast.makeText(builder.mContext,"加载数据失败",Toast.LENGTH_LONG).show();

            if(STATE_REFREH==state)   {
                builder.mRefreshLayout.finishRefresh();
            }
            else  if(STATE_MORE == state){
                builder.mRefreshLayout.finishRefreshLoadMore();
            }
        }
    }

    public interface OnPageListener<T> {
        void load(List<T> datas , int totalPage , int totalCount);

        void refresh(List<T> datas , int totalPage , int totalCount);

        void loadMore(List<T> datas , int totalPage , int totalCount);
    }
}
