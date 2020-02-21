package com.lmj.fakemi.recent;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lmj.fakemi.BR;
import com.lmj.fakemi.R;
import com.lmj.fakemi.entity.RecentFile;
import com.lmj.fakemi.widget.CusRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.viewadapter.recyclerview.BindingViewHolder;
import me.goldze.mvvmhabit.binding.viewadapter.recyclerview.LineManagers;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/14 15:16
 * Description:     作用描述
 */
public class RecentParentAdapter extends RecyclerView.Adapter<RecentParentAdapter.RecentParentHolder> {
    private List<RecentFile> mList;

    private Activity mActivity;

    public RecentParentAdapter(Activity activity) {
        mActivity = activity;
    }

    @NonNull
    @Override
    public RecentParentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                R.layout.item_recent_parent, parent, false);
        return new RecentParentHolder(binding);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecentParentHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentParentHolder holder, int position) {
            holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setNewData(List<RecentFile> data) {
        mList = data == null ? new ArrayList<RecentFile>() : data;
        notifyDataSetChanged();
    }

   public class RecentParentHolder extends BindingViewHolder<ViewDataBinding>{
        public RecentFile recentFile;
        public RecyclerView.Adapter adapter;
        public RecyclerView.LayoutManager layoutManager;
        public LineManagers.LineManagerFactory lineFactory;
        private int mPosition;

        public BindingCommand onMoreClick = new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                recentFile.isExpand = !recentFile.isExpand;
                notifyItemChanged(mPosition);
            }
        });

       RecentParentHolder(@NonNull ViewDataBinding binding) {
           super(binding);
       }

       public void bind(int pos){
           mPosition = pos;
           recentFile = mList.get(pos);
           if (recentFile.fileIcon==R.mipmap.icon_main_class_pic){
               adapter = new RecentChildPicAdapter(mActivity,recentFile.getFileList());
               layoutManager = new GridLayoutManager(mActivity, 4);
               lineFactory = LineManagers.both();
           }else {
               adapter = new RecentChildCommonAdapter(mActivity,recentFile.getFileList());
               layoutManager =new LinearLayoutManager(mActivity);
           }
           getBinding().setVariable(BR.holder,this);
           getBinding().executePendingBindings();
       }

       public String moreText(){
            return recentFile.isExpand?"收起":"更多";
        }

        public int moreArrow(){
            return recentFile.isExpand?R.mipmap.arrow_top:R.mipmap.arrow_bottom;
        }
    }

}
