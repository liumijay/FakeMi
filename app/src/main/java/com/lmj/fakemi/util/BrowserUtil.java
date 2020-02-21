package com.lmj.fakemi.util;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.SparseArray;

import com.lmj.fakemi.R;
import com.lmj.fakemi.entity.BreadModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.core.content.FileProvider;
import androidx.documentfile.provider.DocumentFile;
import me.goldze.mvvmhabit.utils.ToastUtils;

/*
 * Author:        LMJ
 * CreateDate:     2019/7/18 14:22
 * Description:     全局文件扫描工具
 */
public class BrowserUtil {

    /**
     * 点击某个位置的面包屑导航，获取到面包屑导航列表
     *
     * @param rootName      当前所在存储类型别名
     * @param breadModelList 原列表
     * @param position       点击位置
     * @return 新列表
     */
    public static String getBreadModelListByPosition(Context context, String rootName, List<BreadModel> breadModelList, int position) {
        StringBuilder result = new StringBuilder("/");
        String rootPath = getRootPath(context,rootName);
        for (int i = 0; i < breadModelList.size(); i++) {
            if (position >= i) {
                result.append(breadModelList.get(i).getCurName()).append(File.separator);
            } else {
                break;
            }
        }
        String resultStr = result.toString();
        resultStr = resultStr.replace(rootName, rootPath);
        return resultStr;
    }

    /**
     * 从路径名中获取面包屑导航列表
     *
     * @param rootPath 存储类型根目录
     * @param rootName 存储类型名称
     * @param path     路径名
     * @return 面包屑导航列表
     */
    public static List<BreadModel> getBreadModeListFromPath(String rootPath, String rootName, String path) {
        List<String> pathList = new ArrayList<>();
        //内部存储设备
        path = path.replace(rootPath, rootName);
        if (!TextUtils.isEmpty(path)) {
            String[] a = path.substring(1, path.length()).split("/");
            pathList = Arrays.asList(a);
        }
        List<BreadModel> breadModelList = new ArrayList<>();
        for (String str : pathList) {
            BreadModel breadModel = new BreadModel();
            breadModel.setCurName(str);
            breadModelList.add(breadModel);
        }
        return breadModelList;
    }

    /**
     * 从路径名中获取面包屑导航列表
     *
     * @param rootName 存储类型名称
     * @param path     路径名
     * @return 面包屑导航列表
     */
    public static List<BreadModel> getBreadModeListFromPath(Context context, String rootName, String path) {
        List<String> pathList = new ArrayList<>();
        String rootPath = getRootPath(context,rootName);
        //内部存储设备
        path = path.replace(rootPath, rootName);
        if (!TextUtils.isEmpty(path)) {
            String[] a = path.substring(1, path.length()).split("/");
            pathList = Arrays.asList(a);
        }
        List<BreadModel> breadModelList = new ArrayList<>();
        for (String str : pathList) {
            BreadModel breadModel = new BreadModel();
            breadModel.setCurName(str);
            breadModelList.add(breadModel);
        }
        return breadModelList;
    }

    /**
     * 打开文件
     * @param context
     * @param target
     */
    public static void openFileByCustom(final Context context, final File target) {
        if (!target.exists()){
            ToastUtils.showShort(R.string.operator_exist_no);
            return;
        }
        final String mime = FileUtils.getMimeType(target.getAbsolutePath());

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        if (mime != null) {
            intent.setDataAndType(getContentUri(context,target), mime);
        } else {
            intent.setDataAndType(getContentUri(context,target), "*/*");
        }
        if (context.getPackageManager().queryIntentActivities(intent, 0).isEmpty()) {
            ToastUtils.showShort(R.string.operator_open_fail);
        }

        try {
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showShort(R.string.operator_open_fail);
        }
    }

    /**
     * 分享文件
     * @param context
     * @param selectedItems
     */
    public static void shareFileByCustom(final Context context, SparseArray<String> selectedItems) {
        ArrayList<Uri> uris = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            File file = new File(selectedItems.valueAt(i));
            if (!file.isDirectory()){
                uris.add(getContentUri(context,file));
            }
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("*/*");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);

        try {
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showShort(R.string.operator_share_fail);
        }
    }

    private static Uri getContentUri(Context context, File target){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return  FileProvider.getUriForFile(context, "com.lmj.fakemi.fileprovider", target);

        } else {
           return Uri.fromFile(target);
        }
    }

    /**
     * 通知媒体库更新文件夹
     * @param context
     * @param filePaths 文件名
     *
     * */
    public static void scanFile(Context context, List<String> filePaths, MediaScannerConnection.OnScanCompletedListener listener) {
        String[] path =new String[filePaths.size()];
        for (int i = 0;i<path.length;i++){
            String filePath = filePaths.get(i);
            int lastSep = filePath.lastIndexOf(File.separator);
            path[i] = lastSep == -1 ? Environment.getExternalStorageState() : filePath.substring(0, lastSep + 1);
        }
        MediaScannerConnection.scanFile(context, path, null,listener);
    }

    /**
     * 新建文件夹
     * @param folder
     * @return
     */
    public static boolean createDir(File folder) {
        if (folder.mkdirs())
            return true;
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                DocumentFile document = DocumentFile.fromFile(folder.getParentFile());
                return document.exists();
            }
        }
        return false;
    }

    //判断是sd卡还是内部存储
    public static String getRootName(Context context, String path){
        List<String> sdCardList = FileUtils.getAllSdPaths(context);
        for (int i = 0; i < sdCardList.size(); i++) {
            if (path.equals(sdCardList.get(i))){
                if (i == 0) {
                    return  "内部存储";
                } else if (i==1){
                    return "外部存储";
                }else {
                    return "外部存储"+i;
                }
            }
        }
        return "内部存储";
    }

    //判断是sd卡还是内部存储
    public static String getRootPath(Context context, String name){
        List<String> sdCardList = FileUtils.getAllSdPaths(context);
        for (int i = 0; i < sdCardList.size(); i++) {
            if ("/内部存储".equals(name)){
                return sdCardList.get(i);
            }else if ("/外部存储".equals(name)){
                return sdCardList.get(i);
            }else if (("外部存储"+i).equals(name)){
                return sdCardList.get(i);
            }
        }
        return sdCardList.get(0);
    }

}
