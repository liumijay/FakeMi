package com.lmj.fakemi.recent;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lmj.fakemi.BR;
import com.lmj.fakemi.R;
import com.lmj.fakemi.entity.RecentFile;
import com.lmj.fakemi.util.constant.Const;

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
import me.goldze.mvvmhabit.utils.ConvertUtils;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/14 15:16
 * Description:     作用描述
 */
public class RecentParentAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private List<RecentFile> mList;

    private Activity mActivity;

    public RecentParentAdapter(Activity activity) {
        mActivity = activity;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        if (viewType != Const.EMPTY) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                    R.layout.item_recent_parent, parent, false);
            return new RecentParentHolder(binding);
        }
        binding = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                R.layout.item_main_search, parent, false);
        return new SearchHolder(binding);

    }

    @Override
    public void onViewAttachedToWindow(@NonNull BindingViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        if (holder instanceof RecentParentHolder){
            ((RecentParentHolder)holder).bind(position-1);
        }else if (holder instanceof SearchHolder){
            ((SearchHolder)holder).bind();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return position!=0?0:Const.EMPTY;
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
               //30+16+5*4+16
               int picSize = (ConvertUtils.getScreenWidth()-ConvertUtils.dp2px(82))/3;
               adapter = new RecentChildPicAdapter(mActivity,recentFile.getFileList(),picSize);
               layoutManager = new GridLayoutManager(mActivity, 3);
               lineFactory = LineManagers.both(ConvertUtils.dp2px(5));
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


    public class SearchHolder extends BindingViewHolder<ViewDataBinding>{
        public BindingCommand onSearchClick = new BindingCommand(new BindingAction() {
            @Override
            public void call() {

            }
        });

        public SearchHolder(@NonNull ViewDataBinding binding) {
            super(binding);
        }

        public void bind(){
            getBinding().setVariable(BR.holder,this);
            getBinding().executePendingBindings();
        }
    }

}
