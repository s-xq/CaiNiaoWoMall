package com.sxq.mall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by SXQ on 2017/2/26.
 */

public class FragmentStateAdapter extends FragmentStatePagerAdapter{
    private List<Fragment> fragmentList;
    public FragmentStateAdapter(FragmentManager fm , List<Fragment> list){
        super(fm);
        this.fragmentList = list ;
    }
    public Fragment getItem(int arg0){
        return fragmentList.get(arg0);
    }
    public int getCount(){
        return this.fragmentList.size();
    }
}
