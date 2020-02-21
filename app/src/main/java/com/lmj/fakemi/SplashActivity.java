package com.lmj.fakemi;

import androidx.lifecycle.ViewModelProviders;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

import android.Manifest;
import android.os.Bundle;

import com.lmj.fakemi.app.FakeViewModelFactory;
import com.lmj.fakemi.databinding.ActivitySplashBinding;
import com.lmj.fakemi.main.MainFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public SplashViewModel initViewModel() {
        FakeViewModelFactory factory = FakeViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SplashViewModel.class);
    }

    @Override
    public void initData() {
        requestCameraPermissions();
    }

    /**
     * 请求相机权限
     */
    private void requestCameraPermissions() {
        //请求打开相机权限
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            startContainerActivity(MainFragment.class.getCanonicalName());
                        } else {
                            ToastUtils.showShort("权限被拒绝");
                        }
                    }
                });
    }
}
