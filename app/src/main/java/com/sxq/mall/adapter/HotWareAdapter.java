package com.sxq.mall.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.drawee.view.DraweeView;
import com.sxq.mall.R;
import com.sxq.mall.bean.Ware;
import com.sxq.mall.utils.CartProvider;
import com.sxq.mall.utils.ToastUtil;

import java.util.List;

/**
 * Created by SXQ on 2017/3/11.
 */

public class HotWareAdapter extends SimpleAdapter<Ware> {

    private Context mContext ;
    private CartProvider mCartProvider ;

    public HotWareAdapter(Context context , List<Ware> wares){
        super(context , wares , R.layout.template_hot_wares);
        mContext = context ;
        mCartProvider = new CartProvider(context);
    }

    @Override
    public void bindData(BaseViewHolder holder, final Ware ware) {
        DraweeView draweeView = (DraweeView)holder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(ware.getImgUrl()));
        holder.getTextView(R.id.text_title).setText(ware.getName());
        holder.getTextView(R.id.text_price).setText("￥" + ware.getPrice());
        Button mBtnAdd = holder.getButton(R.id.btn_add);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCartProvider.put(ware);
                ToastUtil.show(mContext , "已添加到购物车" );
            }
        });
    }
}
