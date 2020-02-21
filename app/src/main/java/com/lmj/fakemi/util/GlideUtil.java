package com.lmj.fakemi.util;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lmj.fakemi.R;

import java.io.File;

import androidx.core.content.FileProvider;

/*
 * Author:        LMJ
 * CreateDate:     2020/1/15 17:30
 * Description:     作用描述
 */
public class GlideUtil {

    //加载文件图片
    public static void loadFileImage(Context mContext, String path, ImageView mImageView, int errIcon) {
        if (mContext == null){
            return;
        }
        Uri uri ;
        File file = new File(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(mContext, "com.lmj.fakemi.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        RequestOptions requestOptions = new RequestOptions().placeholder(R.color.common_line).error(errIcon);
        if (mContext instanceof Activity){
            if (!((Activity) mContext).isDestroyed()){
                Glide.with(mContext).load(uri).apply(requestOptions).into(mImageView);
            }
        }else {
            Glide.with(mContext).load(uri).apply(requestOptions).into(mImageView);
        }
    }
}
