package com.lmj.fakemi.recent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lmj.fakemi.BR;
import com.lmj.fakemi.R;
import com.lmj.fakemi.app.FakeViewModelFactory;
import com.lmj.fakemi.databinding.FragmentRecentBinding;
import com.lmj.fakemi.entity.RecentFile;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import me.goldze.mvvmhabit.base.BaseFragment;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/13 17:19
 * Description:     最近文件
 */
public class RecentFragment extends BaseFragment<FragmentRecentBinding,RecentViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_recent;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public RecentViewModel initViewModel() {
        FakeViewModelFactory factory = FakeViewModelFactory.getInstance(mActivity.getApplication());
        return ViewModelProviders.of(this, factory).get(RecentViewModel.class);
    }

    @Override
    public void initData() {
        viewModel.requestRecentFile();
    }

    @Override
    public void initViewObservable() {
        viewModel.fileList.observe(this, new Observer<List<RecentFile>>() {
            @Override
            public void onChanged(List<RecentFile> recentFiles) {
                RecentParentAdapter adapter = new RecentParentAdapter(mActivity);
                adapter.setNewData(recentFiles);
                binding.recycler.setLayoutManager(new LinearLayoutManager(mActivity));
                binding.recycler.setAdapter(adapter);
            }
        });
    }
}
