package me.goldze.mvvmhabit.binding.viewadapter.image;


import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.databinding.BindingAdapter;
import me.goldze.mvvmhabit.R;

/**
 * Created by goldze on 2017/6/18.
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"url", "placeholderRes","size"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes,int size) {
        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = new RequestOptions().placeholder(placeholderRes);
            if (size>0){
                options= options.override(size);
            }
            //使用Glide框架加载图片
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .into(imageView);
        }
    }

    @BindingAdapter("imgSrc")
    public static void setImageSrc(ImageView imageView, int src) {
       imageView.setImageResource(src);
    }

}

