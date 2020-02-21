package com.lmj.fakemi.type;

import android.app.Application;

import com.lmj.fakemi.reposittory.FakeRepository;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/13 17:27
 * Description:     作用描述
 */
public class TypeViewModel extends BaseViewModel<FakeRepository> {

    public TypeViewModel(@NonNull Application application, FakeRepository model) {
        super(application, model);
    }

}
