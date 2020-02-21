package com.lmj.fakemi;

import android.app.Application;

import com.lmj.fakemi.reposittory.FakeRepository;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/10 17:01
 * Description:     作用描述
 */
public class SplashViewModel extends BaseViewModel<FakeRepository> {

    public SplashViewModel(@NonNull Application application, FakeRepository model) {
        super(application, model);
    }

}
