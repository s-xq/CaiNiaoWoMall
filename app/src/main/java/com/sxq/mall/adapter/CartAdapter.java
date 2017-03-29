package com.sxq.mall.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sxq.mall.R;
import com.sxq.mall.bean.ShoppingCart;
import com.sxq.mall.utils.CartProvider;
import com.sxq.mall.view.AmountView;

import java.util.Iterator;
import java.util.List;

/**
 * Created by SXQ on 2017/3/19.
 */

public class CartAdapter extends SimpleAdapter<ShoppingCart> implements BaseAdapter.OnItemClickListener{

    private CartProvider cartProvider ;
    private TextView totalView ;
    private CheckBox allSelectedView ;

    public CartAdapter(Context context , List<ShoppingCart> datas , TextView totalView , CheckBox allSelectedView){
        super(context , datas , R.layout.template_cart);

        this.cartProvider = new CartProvider(context);

        //坑！勿忘
        setOnItemClickListener(this);

        setTotalView(totalView);
        setAllSelectedView(allSelectedView);

        showTotalPrice();
        updateAllSelectedView();
    }

    @Override
    public void bindData(BaseViewHolder holder, final ShoppingCart cart) {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView)holder.getView(R.id.drawee_view);
        simpleDraweeView.setImageURI(cart.getImgUrl());

        holder.getTextView(R.id.text_title).setText(cart.getName());
        holder.getTextView(R.id.text_price).setText("￥" + cart.getPrice());

        CheckBox checkBox = (CheckBox) holder.getView(R.id.checkbox);
        checkBox.setChecked(cart.isChecked());

        AmountView amountView = (AmountView) holder.getView(R.id.amount_view);
        amountView.setCurrentAmount(cart.getCount());
        amountView.setOnAmountChangedListener(new AmountView.OnAmountChangedListener(){
            @Override
            public void onIncrease(View v, int amount) {
                cart.setCount(amount);
                cartProvider.update(cart);
                showTotalPrice();
            }

            @Override
            public void onDecrease(View v, int amount) {
                cart.setCount(amount);
                cartProvider.update(cart);
                showTotalPrice();
            }
        });


    }

    @Override
    public void onItemClick(View v, int position) {
        ShoppingCart cart = getItem(position);
        cart.setChecked( !cart.isChecked() );
        cartProvider.update(cart);
        notifyItemChanged(position);
        updateAllSelectedView();
        showTotalPrice();
    }

    public void delCart(){
        if(isDatasNull())
            return ;

/*        for (ShoppingCart cart : datas){//每次都会取size()
            if(cart.isChecked()){
                int position = datas.indexOf(cart);
                cartProvider.delete(cart);
                datas.remove(cart);//由于循环时remove，size改变，会报错
                notifyItemRemoved(position);
            }
        }*/

        for(Iterator iterator = mDatas.iterator(); iterator.hasNext();){
            ShoppingCart cart = (ShoppingCart) iterator.next();
            if(cart.isChecked()){
                int position = mDatas.indexOf(cart);
                cartProvider.delete(cart);
                iterator.remove();
                notifyItemRemoved(position);
            }
        }
    }

    private void updateAllSelectedView(){
        int count = 0 ;
        int checkNum = 0  ;
        if(mDatas != null){
            count = mDatas.size();
            for(ShoppingCart cart : mDatas){
                if(cart.isChecked() == false ){
                    allSelectedView.setChecked(false);
                    break;
                }
                else
                    checkNum += 1 ;
            }
            if(checkNum == count )
                allSelectedView.setChecked(true);
        }
    }

    private float getTotalPrice(){
        float sum= 0 ;
        if(isDatasNull()){
            return sum ;
        }
        for(ShoppingCart cart : mDatas){
            if(cart.isChecked()){
                sum += cart.getCount()*cart.getPrice();
            }
        }
        return sum ;
    }

    public void showTotalPrice(){
        float total = getTotalPrice();
        totalView.setText(Html.fromHtml("合计 ￥<span style='color:#eb4f38'>" + total + "</span>"), TextView.BufferType.SPANNABLE);
    }

    private boolean isDatasNull(){
        return !(this.mDatas != null && this.mDatas.size() > 0);
    }

    public void checkAll(boolean isChecked){
        if(isDatasNull())
            return ;
        for(int i = 0 ; i < mDatas.size() ; i ++ ){
            mDatas.get(i).setChecked(isChecked);
            notifyItemChanged(i);
        }
    }

    private void setTotalView(TextView totalView){
        this.totalView = totalView ;
    }

    private void setAllSelectedView(final CheckBox allSelectedView){
        this.allSelectedView = allSelectedView ;
        allSelectedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAll(allSelectedView.isChecked());
                showTotalPrice();
            }
        });
    }


}
