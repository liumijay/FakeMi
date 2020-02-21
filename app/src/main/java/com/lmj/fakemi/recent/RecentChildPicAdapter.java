package com.lmj.fakemi.recent;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lmj.fakemi.BR;
import com.lmj.fakemi.R;
import com.lmj.fakemi.entity.EssFile;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import me.goldze.mvvmhabit.binding.viewadapter.recyclerview.BindingViewHolder;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/16 9:59
 * Description:     最近文件图片子适配器
 */
public class RecentChildPicAdapter extends RecyclerView.Adapter<RecentChildPicAdapter.RecentChildPicHolder> {
    private Activity mActivity;
    private List<EssFile> mList;

    public RecentChildPicAdapter(Activity activity, List<EssFile> list) {
        mActivity = activity;
        mList = list == null ? new ArrayList<EssFile>() : list;
    }

    @NonNull
    @Override
    public RecentChildPicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                R.layout.item_child_pic_list, parent, false);
        return new RecentChildPicAdapter.RecentChildPicHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentChildPicHolder holder, int position) {
        holder.path = mList.get(position).mFilePath;
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class RecentChildPicHolder extends BindingViewHolder<ViewDataBinding> {
        public String path;

        RecentChildPicHolder(@NonNull ViewDataBinding binding) {
            super(binding);
        }

        private void bind(){
            getBinding().setVariable(BR.holder, this);
            getBinding().executePendingBindings();
        }
    }
}
