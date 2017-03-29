package com.sxq.mall.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by SXQ on 2017/3/19.
 */

public class ToastUtil {

    public static void show(Context context , CharSequence text , int duration ){
        Toast.makeText(context , text , duration).show() ;
    }

    public static void show(Context context , CharSequence text ){
        show(context , text , Toast.LENGTH_SHORT) ;
    }

    public static void show(Context context , int resId , int duration , Object ... args){
        show(context , String.format(context.getResources().getString(resId) , args) ,duration);
    }
    
    public static void show(Context context , int resId , int duration ){
        show(context , context.getResources().getString(resId) ,duration );
    }
    
    public static void show(Context context , int resId ){
        show(context , resId , Toast.LENGTH_SHORT);
    }

    public static void show(Context context , String format , int duration , Object... args){
        show(context , String.format(format , args) ,duration );
    }
    
    public static void show(Context context , String format , Object... args ){
        show(context , String.format(format , args));
    }
    
}
