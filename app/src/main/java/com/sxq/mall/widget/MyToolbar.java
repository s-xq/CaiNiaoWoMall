package com.sxq.mall.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.sxq.mall.R;


/**
 * Created by SXQ on 2017/2/27.
 */

public class MyToolbar extends Toolbar {
    private static final String TAG = "MyToolbar";
    private LayoutInflater mInflater ;
    View mView ;
    TextView mTextTitle ;
    EditText mSearchView ;
    Button mRightButton ;
    public MyToolbar(Context context){
        this(context , null , 0 );
    }
    public MyToolbar(Context context , AttributeSet attrs ){
        this(context , attrs , 0);
    }
    public MyToolbar(Context context , AttributeSet attrs , int defStyleAttr){
        super(context , attrs , defStyleAttr);

        initView();
        setContentInsetsRelative(10,10);

        if(attrs != null) {
            final TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.MyToolbar, defStyleAttr, 0);

            String text = "" ;
            text = tintTypedArray.getString(R.styleable.MyToolbar_rightButtonText );
            if(text != ""){
                setRightButtonText(text);
            }

            final Drawable icon = tintTypedArray.getDrawable(R.styleable.MyToolbar_rightButtonIcon);
            if (icon != null) {
                setRightButtonIcon(icon);
            }

            boolean isShowSearchView = tintTypedArray.getBoolean(R.styleable.MyToolbar_isShowSearchView , false);
            if(isShowSearchView){
                showSearchView();
                hideTitleView();
                hideRightButton();
            }

            tintTypedArray.recycle();
        }
    }

    @Override
    public void setTitle(int resId){
        setTitle(getContext().getText(resId));
    }
    @Override
    public void setTitle(CharSequence title){
        initView();
        if(mTextTitle != null){
            mTextTitle.setText(title);
            showTitleView();
        }
    }

    private void initView(){
        if(mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.toolbar, null);

            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mSearchView = (EditText) mView.findViewById(R.id.toolbar_searchview);
            mRightButton = (Button) mView.findViewById(R.id.toolbar_rightButton);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);

        }
    }

    public void setRightButtonIcon(Drawable icon){
        if(mRightButton != null){
            mRightButton.setBackground(icon);
            mRightButton.setVisibility(VISIBLE);
        }
    }

    public void setRightButtonText(String text){
        if(mRightButton != null ){
            mRightButton.setText(text);
            mRightButton.setVisibility(VISIBLE);
        }
    }

    public void setRightButtonClickListener(OnClickListener li){
        mRightButton.setOnClickListener(li);
    }

    public void hideTitleView (){
        if(mTextTitle != null)
            mTextTitle.setVisibility(GONE);
    }

    public void showTitleView(){
        if(mTextTitle != null)
            mTextTitle.setVisibility(VISIBLE);
    }

    public void showRightButton(){
        if(mRightButton != null)
            mRightButton.setVisibility(VISIBLE);
    }

    public void hideRightButton(){
        if(mRightButton != null)
            mRightButton.setVisibility(GONE);
    }

    public void hideSearchView(){
        if(mSearchView != null)
            mSearchView.setVisibility(GONE);
    }

    public void showSearchView(){
        if(mSearchView != null){
            mSearchView.setVisibility(VISIBLE);
        }
    }

    public Button getRightButton(){
        return mRightButton;
    }
}
