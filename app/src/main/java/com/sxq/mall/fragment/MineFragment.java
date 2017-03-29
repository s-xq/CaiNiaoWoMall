package com.sxq.mall.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.mall.R;

/**
 * Created by SXQ on 2017/2/26.
 */

public class MineFragment extends Fragment {
    View view ;
    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle onSavedInstanceState ){
        view = inflater.inflate(R.layout.fragment_mine , null);

        return view ;
    }
}
