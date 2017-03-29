package com.sxq.mall;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.sxq.mall.adapter.FragmentAdapter;
import com.sxq.mall.bean.Tab;
import com.sxq.mall.fragment.CartFragment;
import com.sxq.mall.fragment.CategoryFragment;
import com.sxq.mall.fragment.HomeFragment;
import com.sxq.mall.fragment.HotFragment;
import com.sxq.mall.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";
    private FragmentTabHost mFragmentTabHost ;
    private List<Tab> tabList = new ArrayList<Tab>();
    private LayoutInflater mInflater;
    private ViewPager mViewPager ;
    private List<Fragment> fragmentList ;

    private CartFragment mCartFragment ;//保存引用，以便切换时更新购物车
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();
        initTab();
    }


    private void initTab(){
        tabList.add(new Tab(R.string.home ,R.drawable.selector_icon_home , HomeFragment.class));
        tabList.add(new Tab(R.string.hot ,R.drawable.selector_icon_hot , HotFragment.class));
        tabList.add(new Tab(R.string.catagory ,R.drawable.selector_icon_catagory , CategoryFragment.class));
        tabList.add(new Tab(R.string.cart ,R.drawable.selector_icon_cart , CartFragment.class));
        tabList.add(new Tab(R.string.mine ,R.drawable.selector_icon_mine , MineFragment.class));

        mFragmentTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mFragmentTabHost.setup(this , getSupportFragmentManager() , R.id.realtabcontent);
        mInflater = LayoutInflater.from(this);

        for(Tab tab : tabList){
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));

            mFragmentTabHost.addTab(tabSpec , tab.getFragment() , null);
        }

        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int position = mFragmentTabHost.getCurrentTab();
                mViewPager.setCurrentItem(position);
                if(tabId == getString(R.string.cart)){
                    refreshCartFragmentData();
                }
            }
        });
    }

    private void refreshCartFragmentData(){
        if(mCartFragment != null){
            mCartFragment.refreshData();
        }
    }
    private View buildIndicator(Tab tab ){
        View view = mInflater.inflate(R.layout.tabindicator , null);
        ImageView iv = (ImageView)view.findViewById(R.id.iv_tab);
        TextView tv = (TextView)view.findViewById(R.id.tv_tab);
        iv.setImageResource(tab.getIcon());
        tv.setText(tab.getTitle());
        return view ;
    }
    private void initViewPager(){
        initFragmentList();
        mViewPager = (ViewPager) findViewById(R.id.mainviewpager);
        mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager() , fragmentList));
        mViewPager.setOffscreenPageLimit(2);//参数为预加载数量，系统最小值为1。慎用！预加载数量过多低端机子受不了
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mFragmentTabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initFragmentList(){
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HotFragment());
        fragmentList.add(new CategoryFragment());

        //保存引用
        mCartFragment = new CartFragment();
        fragmentList.add(mCartFragment);

        fragmentList.add(new MineFragment());
    }
}
