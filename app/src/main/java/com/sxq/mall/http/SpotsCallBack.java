package com.sxq.mall.http;

/**
 * Created by SXQ on 2017/3/4.
 */

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import dmax.dialog.SpotsDialog;

/**
 * 用于带有加载控件的BaseCallBack,测试只有在网络状况较差(2G)的时候才会显示dialog,且显示时间不长，网络较好时看不到
 *
 * 无连接网络时也看不到dialog，因为在onFailure中关闭了dialog,时间很短
 * 应该在onFailure()中加入网络状况的判断
 *
  * @param <T>
 */
public abstract class SpotsCallBack<T> extends BaseCallBack<T> {
    private static final String TAG = "SpotsCallBack";
    private Context mContext ;
    private SpotsDialog mDialog ;

    public SpotsCallBack(Context context){
        mContext = context;

        mDialog = new SpotsDialog(context , "拼命加载中...");
    }

    @Override
    public void onBeforeRequest(Request request) {
        showDialog() ;
    }

    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }

    public void setMessage(int resId){
        mDialog.setMessage(mContext.getString(resId));
    }

    protected void showDialog(){
        Log.e(TAG, "showDialog: " );
        mDialog.show();

    }

    protected void dismissDialog(){
        Log.e(TAG, "dismissDialog: " );
        mDialog.dismiss();
    }
}
