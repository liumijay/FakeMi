package com.lmj.fakemi.ergodic;

import android.app.Application;

import com.lmj.fakemi.reposittory.FakeRepository;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/13 17:30
 * Description:     作用描述
 */
public class ErgodicViewModel extends BaseViewModel<FakeRepository> {

    public ErgodicViewModel(@NonNull Application application, FakeRepository model) {
        super(application, model);
    }
}
