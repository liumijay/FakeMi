package com.lmj.fakemi.app;

import android.annotation.SuppressLint;
import android.app.Application;

import com.lmj.fakemi.SplashViewModel;
import com.lmj.fakemi.ergodic.ErgodicViewModel;
import com.lmj.fakemi.recent.RecentViewModel;
import com.lmj.fakemi.reposittory.FakeRepository;
import com.lmj.fakemi.type.TypeViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by goldze on 2019/3/26.
 */
public class FakeViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile FakeViewModelFactory INSTANCE;
    private final Application mApplication;
    private final FakeRepository mRepository;

    public static FakeViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (FakeViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FakeViewModelFactory(application, Injection.provideFakeRepository());
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private FakeViewModelFactory(Application application, FakeRepository fakeRepository) {
        this.mApplication = application;
        this.mRepository = fakeRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(RecentViewModel.class)){
            return (T) new RecentViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(TypeViewModel.class)){
            return (T) new TypeViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ErgodicViewModel.class)){
            return (T) new ErgodicViewModel(mApplication, mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
