package com.lmj.fakemi.recent;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lmj.fakemi.BR;
import com.lmj.fakemi.R;
import com.lmj.fakemi.base.BaseImgHolder;
import com.lmj.fakemi.entity.EssFile;
import com.lmj.fakemi.util.BrowserUtil;
import com.lmj.fakemi.util.GlideUtil;
import com.lmj.fakemi.util.task.ApkImgTask;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ACache;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/15 16:10
 * Description:     最近文件通用子适配器
 */
public class RecentChildCommonAdapter extends RecyclerView.Adapter<RecentChildCommonAdapter.RecentChildCommonHolder> {

    private Activity mActivity;
    private List<EssFile> mList;

    public RecentChildCommonAdapter(Activity activity, List<EssFile> list) {
        mActivity = activity;
        mList = list == null ? new ArrayList<EssFile>() : list;
    }

    @NonNull
    @Override
    public RecentChildCommonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                R.layout.item_parent_common_list, parent, false);
        return new RecentChildCommonHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentChildCommonHolder holder, int position) {
        holder.essFile = mList.get(position);
            holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class RecentChildCommonHolder extends BaseImgHolder {
        public EssFile essFile;

        public boolean lineVisible;

        private ApkImgTask mApkImgTask;

        public BindingCommand onItemClick = new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                BrowserUtil.openFileByCustom(mActivity, essFile.getFile());
            }
        });

        RecentChildCommonHolder(@NonNull ViewDataBinding binding) {
            super(binding);
            mApkImgTask = new ApkImgTask(this);
        }

        public void bind(int pos) {
            lineVisible = pos != 0;
            ImageView imageView = itemView.findViewById(R.id.item_icon);
            switch (essFile.mFileParentIcon) {
                case R.mipmap.icon_main_class_apk:
                    Drawable drawable = ACache.get(mActivity).getAsDrawable(essFile.mFilePath);
                    if (drawable!=null){
                        getImageView().setImageDrawable(drawable);
                    }else {
                        if (mApkImgTask.getStatus() == AsyncTask.Status.RUNNING) {
                            mApkImgTask.cancel(true);
                            mApkImgTask = new ApkImgTask(this);
                        }
                        if (mApkImgTask.getStatus() == AsyncTask.Status.FINISHED) {
                            mApkImgTask = new ApkImgTask(this);
                        }
                        mApkImgTask.execute(essFile.mFilePath);
                    }
                    break;
                case R.mipmap.icon_main_class_video:
                    GlideUtil.loadFileImage(mActivity, essFile.mFilePath, imageView, R.mipmap.icon_unknow);
                    break;
                default:
                    getImageView().setImageResource(essFile.mFileIcon);
                    break;
            }
            getBinding().setVariable(BR.holder, this);
            getBinding().executePendingBindings();
        }
    }
}
