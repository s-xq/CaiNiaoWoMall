package com.sxq.mall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by SXQ on 2017/2/26.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList ;

    public FragmentAdapter(FragmentManager fm ,List<Fragment> fragmentList){
        super(fm);
        this.fragmentList = fragmentList;
    }
    public Fragment getItem (int arg0){
        return fragmentList.get(arg0);
    }
    public int getCount(){
        return fragmentList.size();
    }
}
