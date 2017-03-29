package com.sxq.mall.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ProviderInfo;

/**
 * Created by SXQ on 2017/3/19.
 *
 * SharedPreferencesUtil
 *
 * <ul>
 *     <strong>PREFERENCE_NAME</strong>
 *     <li>you can change preference name by {@link #PREFERENCES_NAME}</li>
 * </ul>
 *
 * <ul>
 *     <strong>put value</strong>
 *     <li>put string {@link #putString(Context, String, String)}</li>
 *     <li>put int {@link #putInt(Context, String, int)}</li>
 *     <li>put long {@link #putLong(Context, String, long)}</li>
 *     <li>put float {@link #putFloat(Context, String, float)}</li>
 *     <li>put boolean {@link #putBoolean(Context, String, boolean)}</li>
 * </ul>
 *
 * <ul>
 *     <strong>get value</strong>
 *     <li>get string {@link #getString(Context, String)} {@link #getString(Context, String, String)}</li>
 *     <li>get int {@link #getInt(Context, String)} {@link #getInt(Context, String, int)}</li>
 *     <li>get long {@link #getLond(Context, String)} {@link #getLong(Context, String, long)}</li>
 *     <li>get float {@link #getFloat(Context, String)} {@link #getFloat(Context, String)}</li>
 *     <li>get boolean {@link #getBoolean(Context, String)} {@link #getBoolean(Context, String)}</li>
 * </ul>
 *
 */

public class SharedPreferencesUtil {

    public static String PREFERENCES_NAME = "Mall_Pref_Common" ;

    /**
     * put String preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successful written to the persistent storage
     *
     */
    public static boolean putString(Context context ,String key , String value ){
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit() ;
        editor.putString(key , value );
        return editor.commit();
    }

    /**
     * get String preferences
     *
     * @param context
     * @param key The name of the preferences to retrieve
     *
     * @see #getString(Context, String, String)
     */
    public static String getString (Context context , String key ){
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES_NAME , Context.MODE_PRIVATE) ;
        return setting.getString(key , null);
    }

    /**
     * get String preferences
     *
     * @params context
     * @param key The name of the preferences to retrieve
     * @param defaultValue Value to return if the preferences dose not exist
     * @return The preference value if it exist , or defaultValue . Throws ClassCastException if there is a preference with this
     *              name that is not a String
     */
    public static String getString(Context context , String key , String defaultValue) {
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return setting.getString(key , defaultValue );
    }

    /**
     * put int preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successful written to the persistent storage
     */
    public static boolean putInt(Context context , String key , int value ){
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = setting.edit() ;
        editor.putInt(key , value) ;
        return editor.commit() ;
    }

    /**
     * get int preferences
     *
     * @param context
     * @key The name of the preference to modify
     * @see #getInt(Context, String, int)
     */
    public static int getInt(Context context , String key ){
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES_NAME , Context.MODE_PRIVATE);
        return setting.getInt(key , -1);
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param defaultValue Value to return if the preference does not exist
     * @return The preference value if it exist , or defaultValue . Throws ClassCastException if there is a preference with this
     *          name that is not a int
     */
    public static int getInt(Context context , String key , int defaultValue){
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES_NAME , Context.MODE_PRIVATE);
        return setting.getInt(key , defaultValue);
    }

    /**
     * put long preferences
     *
     * @param context
     * @param key The name of the preference to modify
     *  @param value The new value  for the preference
     *  @return True if the new values were successful written to the persistent storage
     */
    public static boolean putLong(Context context , String key , long value ){
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit() ;
        editor.putLong(key , value) ;
        return editor.commit() ;
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key The name of the preferences to modify
     * @see #getLong(Context, String, long)
     */
    public static long getLond(Context context , String key ){
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES_NAME , Context.MODE_PRIVATE);
        return setting.getLong(key , -1);
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key The name of the preferences to modify
     * @param defaultValue Value to return if the preference does not exist
     * @return The preference value if exist , or defaultValue . Throws ClassCastException if there is a preference with this
     *          name that is not long
     */
    public static long getLong(Context context , String key , long defaultValue ){
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES_NAME , Context.MODE_PRIVATE);
        return setting.getLong(key , defaultValue);
    }

    /**
     * put float preferences
     *
     * @param context
     * @param key The name of the preferences to modify
     * @param value The new value for the preference
     * @return True if the value were successful written to the persistent storage
     */
    public static boolean putFloat(Context context , String key , float value ){
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putFloat(key , value);
        return editor.commit();
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key The name of the to modify
     *  @see #getFloat(Context, String, float)
     */
    public static float getFloat (Context context , String key ){
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES_NAME , Context.MODE_PRIVATE);
        return setting.getFloat(key , -1);
    }
    /**
     * get float preferences
     *
     * @param context
     * @param key The name of the preferences to modify
     * @patam defaultValue Value to return if the does not exist
     * @return The preference value if exist , or defaultValue . Throws ClassCastException if there is a preference with this
     *          name that is not float
     */
    public static float getFloat(Context context , String key , float defaultValue){
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES_NAME , Context.MODE_PRIVATE);
        return setting.getFloat(key , defaultValue);
    }

    /**
     * put boolean preferences
     *
     * @param context
     * @param key The name of the preferences to modify
     * @param value The new value for the preference
     *@return True if the value were successful written to the persistent storage
     */
    public static boolean putBoolean (Context context , String key , boolean value){
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putBoolean(key , value) ;
        return editor.commit() ;
    }

    /**
     * get boolean preferences
     *
     * @param context
     * @param key The name of the preference to modify
     *  @see #getBoolean(Context, String, boolean)
     */
    public static boolean getBoolean (Context context , String key ){
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES_NAME , Context.MODE_PRIVATE);
        return setting.getBoolean(key  , false );
    }

    /**
     * get boolean preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param defaultValue Value to return if the preference does not exist
     * @return The preference value if exist , or the defaultValue . Throws ClassCastException if there is a preference with this
     *          name thar is not boolean
     */
    public static boolean getBoolean (Context context , String key , boolean defaultValue ){
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES_NAME ,Context.MODE_PRIVATE);
        return setting.getBoolean(key , defaultValue);
    }
}
