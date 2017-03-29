package com.sxq.mall.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.sxq.mall.R;
import com.sxq.mall.adapter.CartAdapter;
import com.sxq.mall.adapter.decoration.DividerItemDecoration;
import com.sxq.mall.bean.ShoppingCart;
import com.sxq.mall.utils.CartProvider;
import com.sxq.mall.view.AmountView;
import com.sxq.mall.widget.MyToolbar;

import java.util.List;

/**
 * Created by SXQ on 2017/2/26.
 */

public class CartFragment extends Fragment implements  View.OnClickListener{

    private static final String TAG = "CartFragment";

    private static final int ACTION_EDIT = 0 ;
    private static final int ACTION_COMPLETE = 1 ;

    private View view ;

    @ViewInject(R.id.toolbar)
    MyToolbar mToolbar ;

    @ViewInject(R.id.recycler_view)
    RecyclerView mRecyclerView ;

    @ViewInject(R.id.checkbox_all)
    CheckBox mAllSelectedView ;

    @ViewInject(R.id.txt_total)
    TextView mTotalView ;

    @ViewInject(R.id.btn_order)
    Button mBtnOrder ;

    @ViewInject(R.id.btn_del)
    Button mBtnDel ;


    private CartAdapter mCartAdapter ;
    private CartProvider mCartProvider ;

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle onSavedInstanceState){
        view = inflater.inflate(R.layout.fragment_cart , null);

        ViewUtils.inject(this , view);

        init();

        return view ;
    }

    @Override
    public void onClick(View v){
        int action = (int) v.getTag();

        if(ACTION_EDIT == action){
            showDelControl();
        }
        else if(ACTION_COMPLETE == action){
            hideDelControl();
        }
    }

    @OnClick(R.id.btn_del)
    public void delCart(View view){
        mCartAdapter.delCart();
    }

    private void init(){

        mCartProvider = new CartProvider(getContext());
        initToolbar();
        showData();
    }

    private void showData(){
        List<ShoppingCart> mDatas = mCartProvider.getAll();
        mCartAdapter = new CartAdapter(getContext() , mDatas , mTotalView , mAllSelectedView);

        mRecyclerView.setAdapter(mCartAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext() , DividerItemDecoration.VERTICAL_LIST));
    }


    public void refreshData(){
        mCartAdapter.clearData();
        List<ShoppingCart> mDatas = mCartProvider.getAll();
        mCartAdapter.addData(mDatas);
        mCartAdapter.showTotalPrice();
    }

    public void initToolbar(){
        mToolbar.hideSearchView();
        mToolbar.showTitleView();
        mToolbar.setTitle(R.string.cart);
        mToolbar.getRightButton().setVisibility(View.VISIBLE);
        mToolbar.setRightButtonText("编辑");

        mToolbar.getRightButton().setOnClickListener(this);

        mToolbar.getRightButton().setTag(ACTION_EDIT);
    }

    private void showDelControl(){
        mToolbar.getRightButton().setText("完成");
        mTotalView.setVisibility(View.GONE);
        mBtnOrder.setVisibility(View.GONE);
        mBtnDel.setVisibility(View.VISIBLE);
        mToolbar.getRightButton().setTag(ACTION_COMPLETE);

        mCartAdapter.checkAll(false);
        mAllSelectedView.setChecked(false);

    }

    private void  hideDelControl(){

        mTotalView.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.VISIBLE);

        mBtnDel.setVisibility(View.GONE);
        mToolbar.setRightButtonText("编辑");
        mToolbar.getRightButton().setTag(ACTION_EDIT);

        mCartAdapter.checkAll(true);
        mCartAdapter.showTotalPrice();

        mAllSelectedView.setChecked(true);
    }


}
