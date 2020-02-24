package com.lmj.fakemi.type;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lmj.fakemi.BR;
import com.lmj.fakemi.R;
import com.lmj.fakemi.app.FakeViewModelFactory;
import com.lmj.fakemi.databinding.FragmentTypeBinding;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import me.goldze.mvvmhabit.base.BaseFragment;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/13 17:27
 * Description:     分类
 */
public class TypeFragment extends BaseFragment<FragmentTypeBinding,TypeViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_type;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public TypeViewModel initViewModel() {
        FakeViewModelFactory factory = FakeViewModelFactory.getInstance(mActivity.getApplication());

        return ViewModelProviders.of(this,factory).get(TypeViewModel.class);
    }
}
