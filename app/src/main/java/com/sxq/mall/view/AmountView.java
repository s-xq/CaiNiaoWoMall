package com.sxq.mall.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sxq.mall.R;

/**
 * Created by sxq123 on 2016/12/26/0026.
 */

/**
 * 标准布局
 * android:layout_width="wrap_content"
 android:layout_height="36dp"
 android:layout_marginRight="15dp"
 app:btnTextSize="14sp"
 app:btnWidth="36dp"
 app:etWidth="50dp"
 */
public class AmountView extends LinearLayout implements View.OnClickListener  {
    public static String TAG = "AmountView";
    private int maxAmount = Integer.MAX_VALUE;
    private int minAmount = 1 ;
    private int currentAmount = minAmount;
    private OnAmountChangedListener mOnAmountChangedListener ;
    private Button btnDecrease ;
    private Button btnIncrease ;
    private EditText etAmount ;
    public AmountView(Context context){
        this(context , null , 0);
    }
    public AmountView(Context context , AttributeSet attrs){
        this(context , attrs , 0 );
    }
    public AmountView(Context context , AttributeSet attrs , int defAttributeStyle){
        super(context , attrs , defAttributeStyle);

        LayoutInflater.from(getContext()).inflate(R.layout.view_amount, this);
        etAmount = (EditText) findViewById(R.id.etAmount);
        btnDecrease = (Button) findViewById(R.id.btnDecrease);
        btnIncrease = (Button) findViewById(R.id.btnIncrease);

        btnDecrease.setTextColor(getResources().getColor(android.R.color.black));
        btnIncrease.setTextColor(getResources().getColor(android.R.color.black));
        etAmount.setTextColor(getResources().getColor(android.R.color.black));

        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);

        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnWidth, LayoutParams.WRAP_CONTENT);
        int etWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_etWidth, 80);
        int etTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_etTextSize, 0);
        int btnTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnTextSize, 0);
        obtainStyledAttributes.recycle();

        LayoutParams btnParams = new LayoutParams(btnWidth, LayoutParams.MATCH_PARENT);
        btnDecrease.setLayoutParams(btnParams);
        btnIncrease.setLayoutParams(btnParams);
        if (btnTextSize != 0) {
            btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
            btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
        }

        LayoutParams textParams = new LayoutParams(etWidth, LayoutParams.MATCH_PARENT);
        etAmount.setLayoutParams(textParams);
        if (etTextSize != 0) {
            etAmount.setTextSize(etTextSize);
        }
    }

    @Override
    public void onClick(View v){
        if(v == btnDecrease){
            if(getCurrentAmount() > getMinAmount()){
                setCurrentAmount(getCurrentAmount() - 1 );
                if(mOnAmountChangedListener != null)
                    mOnAmountChangedListener.onDecrease(v , getCurrentAmount());
            }
        }
        else if(v == btnIncrease){
            if(getCurrentAmount() < getMaxAmount()){
                setCurrentAmount(getCurrentAmount() + 1 );
                if(mOnAmountChangedListener != null)
                   mOnAmountChangedListener.onIncrease(v , getCurrentAmount());
            }
        }
        etAmount.setText(getCurrentAmount()+"");
    }

    public interface OnAmountChangedListener{
        void onIncrease(View v , int amount);
        void onDecrease(View v , int amount);
    }

    public void setOnAmountChangedListener(OnAmountChangedListener mOnAmountChangedListener){
        this.mOnAmountChangedListener = mOnAmountChangedListener;
    }

    public int getMaxAmount(){
        return this.maxAmount ;
    }

    public void  setMaxAmount(int maxAmount){
        this.maxAmount = maxAmount;//缺少非法判断max<min ?
    }

    public int getMinAmount(){
        return this.minAmount;
    }

    public void setMinAmount(int minAmount){
        this.minAmount = minAmount ;
    }

    public int getCurrentAmount(){
        return this.currentAmount;
    }

    public void setCurrentAmount(int num){
        setNumInvalidate(num);
        etAmount.setText(getCurrentAmount()+"");
    }

    private void setNumInvalidate(int num){
        if(num > getMaxAmount()){
            this.currentAmount = getMaxAmount();
        }
        else if(num < getMinAmount()){
            this.currentAmount = getMinAmount();
        }
        else {
            this.currentAmount = num;
        }
    }
}
