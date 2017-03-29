package com.sxq.mall.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by SXQ on 2017/3/19.
 */

public class JSONUtil {

    private static Gson gson = new Gson();

    public static Gson getGson (){
        return gson ;
    }

    public static <T> T fromJson(String json , Class<T> clz){
        return gson.fromJson(json , clz);
    }

    public static <T> T fromJson(String json , Type type ){
        return gson.fromJson(json , type);
    }

    public static String toJson(Object object ){
        return gson.toJson(object);
    }
}
