package com.lmj.fakemi.main;

import com.lmj.fakemi.ergodic.ErgodicFragment;
import com.lmj.fakemi.recent.RecentFragment;
import com.lmj.fakemi.type.TypeFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/13 17:03
 * Description:     作用描述
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list = new ArrayList<>();//ViewPager要填充的fragment列表
    private List<String> title = new ArrayList<>();//tab中的title文字列表

    public MainFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        list.add(new RecentFragment());
        list.add(new TypeFragment());
        list.add(new ErgodicFragment());

        title.add("最近");
        title.add("分类");
        title.add("手机");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {//返回FragmentPager的个数
        return list.size();
    }

    //FragmentPager的标题,如果重写这个方法就显示不出tab的标题内容
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }


}
