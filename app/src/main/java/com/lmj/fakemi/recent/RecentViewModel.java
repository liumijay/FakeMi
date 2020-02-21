package com.lmj.fakemi.recent;

import android.app.Application;
import android.graphics.RectF;

import com.lmj.fakemi.entity.RecentFile;
import com.lmj.fakemi.reposittory.FakeRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/13 17:19
 * Description:     作用描述
 */
public class RecentViewModel extends BaseViewModel<FakeRepository> {
    public SingleLiveEvent<List<RecentFile>> fileList = new SingleLiveEvent<>();
    public ObservableBoolean refreshing = new ObservableBoolean(false);

    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            requestRecentFile();
        }
    });

    public RecentViewModel(@NonNull Application application, FakeRepository model) {
        super(application, model);
    }

    public void requestRecentFile() {
        refreshing.set(true);
        model.getRecentFile().observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)//请求与ViewModel周期同步
               .subscribe(new Consumer<List<RecentFile>>() {
            @Override
            public void accept(List<RecentFile> recentFiles) throws Exception {
                fileList.setValue(recentFiles);
                refreshing.set(false);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                refreshing.set(false);
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                //请求刷新完成收回
                refreshing.set(false);
            }
        });
    }
}

