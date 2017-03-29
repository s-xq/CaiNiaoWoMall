package com.sxq.mall.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;
import com.sxq.mall.bean.ShoppingCart;
import com.sxq.mall.bean.Ware;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SXQ on 2017/3/19.
 */

public class CartProvider  {

    private static final String CART_JSON = "cart_json" ;

    private Context mContext ;

    private SparseArray<ShoppingCart> datas = null ;

    public CartProvider(Context mContext) {
        this.mContext = mContext;
        datas = new SparseArray<>(10);
        listToSparse();
    }

    public void put(ShoppingCart cart){
        ShoppingCart temp = datas.get((int) cart.getId());
        if(temp != null){
            temp.setCount(temp.getCount() + 1) ;
        }else{
            temp = cart ;
            temp.setCount(1);
        }
        datas.put((int)temp.getId() , temp);

        commit() ;
    }

    public void put(Ware ware){
        ShoppingCart cart = convertData(ware);
        put(cart);
    }

    public void update(ShoppingCart cart ){
        datas.put((int)cart.getId() , cart);
        commit();
    }

    public void delete(ShoppingCart cart){
        datas.delete((int)cart.getId());
        commit();
    }

    public List<ShoppingCart> getAll(){
        return getDataFromLocal();
    }

    /**
     * 将内存中的datas存入SharedPreferences
     */
    private void commit(){
        List<ShoppingCart> list = sparseToList();
        String json = JSONUtil.toJson(list);
        SharedPreferencesUtil.putString(mContext , CART_JSON , json);
    }

    public List<ShoppingCart> getDataFromLocal(){
        String json = SharedPreferencesUtil.getString(mContext , CART_JSON );
        List<ShoppingCart> shoppingCarts = null ;
        if(json != null)
            shoppingCarts = JSONUtil.fromJson(json , new TypeToken<List<ShoppingCart>>(){}.getType());
        return shoppingCarts ;
    }

    public List<ShoppingCart> sparseToList(){
        int size = datas.size();
        List<ShoppingCart> list = new ArrayList<>(size);
        for(int i = 0 ; i < size ; i ++ ){
            list.add(datas.valueAt(i));
        }
        return list;
    }

    public void listToSparse(){
        List<ShoppingCart> carts = getDataFromLocal();
        if(carts != null && carts.size() > 0 ){
            for(ShoppingCart cart : carts){
                datas.put((int)cart.getId() , cart);
            }
        }
    }

    private ShoppingCart convertData(Ware ware){
        ShoppingCart cart = new ShoppingCart();
        cart.setId(ware.getId());
        cart.setDescription(ware.getDescription());
        cart.setImgUrl(ware.getImgUrl());
        cart.setName(ware.getName());
        cart.setPrice(ware.getPrice());

        return cart;
    }
}
