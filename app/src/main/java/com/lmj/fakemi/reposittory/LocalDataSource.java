package com.lmj.fakemi.reposittory;

import com.lmj.fakemi.entity.EssFile;
import com.lmj.fakemi.entity.RecentFile;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by goldze on 2019/3/26.
 */
public interface LocalDataSource {
    /**
     * 获取最近文件
     */
    Observable<List<RecentFile>> getRecentFile();
}
