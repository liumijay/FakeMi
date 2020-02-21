package com.lmj.fakemi.base;

import android.widget.ImageView;

import com.lmj.fakemi.R;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import me.goldze.mvvmhabit.binding.viewadapter.recyclerview.BindingViewHolder;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/16 9:25
 * Description:     带icon的viewHolder
 */
public class BaseImgHolder extends BindingViewHolder<ViewDataBinding> {
    private ImageView imageView;

    public BaseImgHolder(@NonNull ViewDataBinding binding) {
        super(binding);
        imageView = itemView.findViewById(R.id.item_icon);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
