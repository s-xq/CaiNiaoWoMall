package com.sxq.mall.http;

import android.os.Handler;
import android.os.Looper;
import android.webkit.HttpAuthHandler;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by SXQ on 2017/3/4.
 */

public class OkHttpHelper {

    private static  OkHttpHelper mInstance ;

    private OkHttpClient mOkHttpClient ;

    private Gson mGson ;

    private Handler mHandler ;
    static {
        mInstance  = new OkHttpHelper ();
    }

    private OkHttpHelper (){
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(10,TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(30,TimeUnit.SECONDS);

        mGson = new Gson();

        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpHelper getInstance(){

        return mInstance ;
    }


    public void get(String url , BaseCallBack baseCallBack ){
        Request request = buildRequest(url , HttpMethodType.GET , null);
        doRequest(request , baseCallBack);
    }

    public void post(String url , Map<String , String > params , BaseCallBack baseCallBack){
        Request request = buildRequest(url , HttpMethodType.POST  ,params);
        doRequest(request , baseCallBack);

    }

    public void doRequest(Request request , final BaseCallBack baseCallBack ){
        callBackBefore(baseCallBack , request);

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callBackFailure(baseCallBack , request , e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                callBackResponse(baseCallBack , response);

                if(response.isSuccessful()){
                    String resultStr = response.body().string() ;
                    if(baseCallBack.mType == String.class){
                        callBackSuccess(baseCallBack , response , resultStr);
                    }
                    else {
                        try{
                            Object object = mGson.fromJson(resultStr , baseCallBack.mType);
                            callBackSuccess(baseCallBack , response , object);
                        }
                        catch(JsonParseException ex){
                            callBackError(baseCallBack , response , response.code() , ex);
                        }
                    }
                }
                else{
                    callBackError(baseCallBack , response , response.code() , null );
                }
            }
        });
    }

    public Request buildRequest(String url , HttpMethodType type , Map<String , String > params ){
       Request.Builder builder  = new Request.Builder();
        builder.url(url);
        if(type == HttpMethodType.GET){
            builder.get();
        }
        else if (type == HttpMethodType.POST){
            RequestBody body = builderFormData(params);
            builder.post(body);
        }
        return builder.build();
    }

    public RequestBody builderFormData (Map<String , String> params){
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if(params != null){
            for(Map.Entry<String ,String > entry :params.entrySet()){
                builder.add(entry.getKey() , entry.getValue());
            }
        }
        return builder.build();
    }

    private void callBackBefore(final BaseCallBack baseCallBack , final Request request){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onBeforeRequest(request);
            }
        });
    }

    private void callBackFailure(final BaseCallBack baseCallBack , final Request request , final Exception ex){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onFailure(request , ex);
            }
        });
    }

    private void callBackSuccess(final BaseCallBack baseCallback , final Response response, final Object object ){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseCallback.onSuccess(response , object);
            }
        });
    }

    private void callBackError(final BaseCallBack baseCallBack , final Response response , final int code , final Exception ex){
        mHandler.post(new Runnable(){
           @Override
            public void run(){
               baseCallBack.onError(response , code , ex);
           }
        });
    }

    private void callBackResponse(final BaseCallBack baseCallBack , final Response response ){
        mHandler.post(new Runnable(){
            @Override
            public void run(){
                baseCallBack.onResponse(response);
            }
        });
    }


    enum HttpMethodType{
        GET ,
        POST
    }

}
