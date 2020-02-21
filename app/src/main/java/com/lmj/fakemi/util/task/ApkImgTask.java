package com.lmj.fakemi.util.task;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.lmj.fakemi.R;
import com.lmj.fakemi.base.BaseImgHolder;

import me.goldze.mvvmhabit.utils.ACache;
import me.goldze.mvvmhabit.utils.Utils;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/15 16:43
 * Description:     作用描述
 */
public class ApkImgTask extends AsyncTask<String, Void, Drawable> {

    private BaseImgHolder holder;
    //doInBackground方法的参数是上面输入的第一个参数，返回的对象会传递给onPostExecute方法
private String mPath;
    public ApkImgTask(BaseImgHolder holder) {
        this.holder = holder;
    }

    @Override
    protected Drawable doInBackground(String... params) {
        mPath = params[0];
        try {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo packageInfo = pm.getPackageArchiveInfo(params[0], PackageManager.GET_ACTIVITIES);
            ApplicationInfo appInfo = packageInfo.applicationInfo;
            if (appInfo != null) {
                appInfo.sourceDir = params[0];
                appInfo.publicSourceDir = params[0];
                return appInfo.loadIcon(pm);
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Drawable drawable) {
        super.onPostExecute(drawable);
        //根据url从listView中找到对应的ImageView
        ImageView imageView = holder.getImageView();
        if (imageView!=null){
            if (drawable != null) {
                imageView.setImageDrawable(drawable);
                ACache.get(Utils.getContext()).put(mPath, drawable, ACache.TIME_HOUR);
            }else {
                imageView.setImageResource(R.mipmap.icon_destroy);
            }
        }

    }

}