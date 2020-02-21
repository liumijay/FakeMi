package com.lmj.fakemi.reposittory;

import com.lmj.fakemi.entity.RecentFile;

import java.util.List;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/10 17:02
 * Description:     数据源(可添加网络数据源)
 */
public class FakeRepository extends BaseModel implements LocalDataSource{
    private volatile static FakeRepository INSTANCE = null;
    private final LocalDataSource mLocalDataSource;

    public FakeRepository(LocalDataSource localDataSource) {
        mLocalDataSource = localDataSource;
    }

    public static FakeRepository getInstance(LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (FakeRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FakeRepository(localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<RecentFile>> getRecentFile() {
        return mLocalDataSource.getRecentFile();
    }
}
