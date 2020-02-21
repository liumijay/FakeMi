package com.lmj.fakemi.ergodic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lmj.fakemi.BR;
import com.lmj.fakemi.R;
import com.lmj.fakemi.app.FakeViewModelFactory;
import com.lmj.fakemi.databinding.FragmentErgodicBinding;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import me.goldze.mvvmhabit.base.BaseFragment;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/13 17:30
 * Description:    遍历手机存储
 */
public class ErgodicFragment extends BaseFragment<FragmentErgodicBinding,ErgodicViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_ergodic;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public ErgodicViewModel initViewModel() {
        FakeViewModelFactory factory = FakeViewModelFactory.getInstance(mActivity.getApplication());
        return ViewModelProviders.of(this,factory).get(ErgodicViewModel.class);
    }
}
