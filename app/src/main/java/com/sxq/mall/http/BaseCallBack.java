package com.sxq.mall.http;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by SXQ on 2017/3/4.
 */

public abstract class BaseCallBack<T>  {

    public Type mType  ;

    public BaseCallBack(){
        mType = getSuperClassTypeParameter(getClass());
    }

    static Type getSuperClassTypeParameter(Class<?> subClass){
        Type superClass = subClass.getGenericSuperclass() ;
        if(superClass instanceof Class){
            throw new RuntimeException("Missing type Parameter");
        }
        ParameterizedType parameterizeType = (ParameterizedType) superClass ;
        return $Gson$Types.canonicalize(parameterizeType.getActualTypeArguments()[0]);
    }

    public abstract void onBeforeRequest(Request request);

    public abstract void onFailure(Request request , Exception ex);

    public abstract void onSuccess(Response response , T t);

    public abstract void onError(Response response , int code , Exception ex );

    public abstract void onResponse(Response response );
}
