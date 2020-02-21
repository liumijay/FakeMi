package me.goldze.mvvmhabit.binding.viewadapter.recyclerview;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/14 14:43
 * Description:     通用viewholder
 */
public class BindingViewHolder <T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private T mBinding;

    public BindingViewHolder(@NonNull T binding) {
        super(binding.getRoot());
        mBinding = binding;
    }
    public T getBinding() {
        return mBinding;
    }
}
