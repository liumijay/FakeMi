package com.lmj.fakemi.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import com.lmj.fakemi.BR;
import com.lmj.fakemi.R;
import com.lmj.fakemi.databinding.FragmentMainBinding;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/13 16:45
 * Description:     首页
 */
public class MainFragment extends BaseFragment<FragmentMainBinding, BaseViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        MainFragmentPagerAdapter pagerAdapter = new MainFragmentPagerAdapter(getChildFragmentManager());
        binding.viewPager.setOffscreenPageLimit(2);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
    }
}
