package com.sxq.mall.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sxq.mall.Constants;
import com.sxq.mall.R;
import com.sxq.mall.adapter.BaseAdapter;
import com.sxq.mall.adapter.HotWareAdapter;
import com.sxq.mall.adapter.decoration.DividerItemDecoration;
import com.sxq.mall.bean.Page;
import com.sxq.mall.bean.Ware;
import com.sxq.mall.utils.Pager;

import java.util.List;

/**
 * Created by SXQ on 2017/2/26.
 */

public class HotFragment extends Fragment implements Pager.OnPageListener<Ware>{
    private static final String TAG = "HotFragment";
    private View view ;

    @ViewInject(R.id.recyclerview)
    private RecyclerView mRecyclerView ;

    @ViewInject(R.id.materiarefreshlayout)
    private MaterialRefreshLayout mMaterialRefreshLayout;

    private HotWareAdapter mAdapter ;



    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle onSavedInstanceState){
        view = inflater.inflate(R.layout.fragment_hot , null);

        ViewUtils.inject(this , view);

        init() ;

        return view ;
    }

    private void init(){
        Pager pager = Pager.newBuilder()
                .setUrl(Constants.API.WARES_HOT)
                .setCanLoadMore(true)
                .setOnPageListener(this)
                .setMaterialRefreshLayout(mMaterialRefreshLayout)
                .setPageSize(10)
                .build(getContext() , new TypeToken<Page<Ware>>(){}.getType());
        pager.request();

    }

    @Override
    public void load(List<Ware> datas, int totalPage, int totalCount) {
        if(mAdapter == null ) {
            mAdapter = new HotWareAdapter(getContext(), datas);
            mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Toast.makeText(getContext(), "Ware#" + position, Toast.LENGTH_SHORT).show();
                }
            });

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            mAdapter.clearData();
            mAdapter.addData(datas);
        }
    }

    @Override
    public void refresh(List<Ware>  datas, int totalPage, int totalCount) {
        mAdapter.clearData();

        mAdapter.addData(datas);

        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void loadMore(List<Ware>  datas, int totalPage, int totalCount) {
        int currentPosition = mAdapter.getItemCount();

        mAdapter.addData(currentPosition, datas);

        mRecyclerView.scrollToPosition(currentPosition);
    }
}
