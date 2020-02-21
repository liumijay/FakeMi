package com.lmj.fakemi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.lmj.fakemi.R;
import com.lmj.fakemi.databinding.ViewMainClassBinding;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

/*
 * Author:        LMJ
 * CreateDate:     2019/7/9 17:30
 * Description:     作用描述
 */
public class MainClassView extends FrameLayout {

    private ViewMainClassBinding mBinding;

    public MainClassView(@NonNull Context context) {
        super(context);
        init(context, null, 0);
    }

    public MainClassView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MainClassView(@NonNull Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MainClassView);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_main_class,this,true);

        int iconSrc = ta.getResourceId(R.styleable.MainClassView_view_icon, -1);
        if (iconSrc!=-1) {
            mBinding.viewIcon.setImageDrawable(ContextCompat.getDrawable(context, iconSrc));
        }
        mBinding.viewTitle.setText(ta.getString(R.styleable.MainClassView_view_title));
        ta.recycle();
    }

    public void setCount(int count){
        mBinding.viewCount.setText(String.valueOf(count));
    }
}
