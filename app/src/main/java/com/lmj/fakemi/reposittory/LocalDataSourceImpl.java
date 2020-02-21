package com.lmj.fakemi.reposittory;

import android.database.Cursor;
import android.provider.MediaStore;

import com.lmj.fakemi.R;
import com.lmj.fakemi.entity.EssFile;
import com.lmj.fakemi.entity.RecentFile;
import com.lmj.fakemi.util.constant.Const;
import com.lmj.fakemi.util.constant.PreferenceKey;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.utils.ACache;
import me.goldze.mvvmhabit.utils.ConvertUtils;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.Utils;

/**
 * 本地数据源，可配合Room框架使用
 * Created by goldze on 2019/3/26.
 */
public class LocalDataSourceImpl implements LocalDataSource {
    private volatile static LocalDataSourceImpl INSTANCE = null;

    public static LocalDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSourceImpl();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private LocalDataSourceImpl() {
        //数据库Helper构建
    }

    @Override
    public Observable<List<RecentFile>> getRecentFile() {
        return Observable.create(new ObservableOnSubscribe<List<RecentFile>>() {

            @Override
            public void subscribe(ObservableEmitter<List<RecentFile>> emitter) {
                String[] projection = new String[]{
                        MediaStore.Files.FileColumns.DATA,
                        MediaStore.Files.FileColumns.MEDIA_TYPE,
                        MediaStore.Files.FileColumns.DATE_MODIFIED};
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.MONTH, -1);
                long beforeDate = calendar.getTime().getTime() / 1000;
                String selection = MediaStore.Files.FileColumns.DATE_MODIFIED + " >" + beforeDate;
                String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC ,"+
                        MediaStore.Files.FileColumns.MEDIA_TYPE+ " DESC ";
                Map<String, List<EssFile>> essFileList = new LinkedHashMap<>();
                List<RecentFile> recentFiles = new ArrayList<>();
                Cursor cursor = Utils.getContext().getContentResolver().query(
                        MediaStore.Files.getContentUri("external"), projection, selection,
                        null, sortOrder);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
                        long time = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED)) * 1000;
                        String type = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE));
                        File file = new File(path);
                        if (!file.isDirectory() && file.exists() && !file.isHidden()) {
                            String pathLowerCase = path.toLowerCase();
                            String mapKey;
                            EssFile essFile = new EssFile(file);
                            if (String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(type)) {
                                mapKey = "图片#张-" + ConvertUtils.formatDate(time,"MM月dd日");
                                essFile.mFileParentIcon=R.mipmap.icon_main_class_pic;
                                addToMap(mapKey, essFile,essFileList);
                            }else if (String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO).equals(type)) {
                                mapKey = "音频#项-" + ConvertUtils.formatDate(time,"MM月dd日");
                                essFile.mFileParentIcon=R.mipmap.icon_main_class_music;
                                essFile.mFileIcon=R.mipmap.icon_music;
                                addToMap(mapKey, essFile,essFileList);
                            } else if (String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO).equals(type)) {
                                mapKey = "视频#项-" + ConvertUtils.formatDate(time,"MM月dd日");
                                essFile.mFileParentIcon=R.mipmap.icon_main_class_video;
                                addToMap(mapKey, essFile,essFileList);
                            } else if (pathLowerCase.endsWith(".apk")) {
                                mapKey = "安装包#项-" + ConvertUtils.formatDate(time,"MM月dd日");
                                essFile.mFileParentIcon=R.mipmap.icon_main_class_apk;
                                addToMap(mapKey, essFile,essFileList);
                            } else if (pathLowerCase.endsWith(".zip") || pathLowerCase.endsWith(".rar") || pathLowerCase.endsWith(".tar")) {
                                mapKey = "压缩包#项-" + ConvertUtils.formatDate(time,"MM月dd日");
                                essFile.mFileParentIcon=R.mipmap.icon_main_class_zip;
                                essFile.mFileIcon=R.mipmap.icon_zip;
                                addToMap(mapKey, essFile,essFileList);
                            } else if (pathLowerCase.endsWith(".txt")){
                                mapKey = "文档#项-" + ConvertUtils.formatDate(time,"MM月dd日");
                                essFile.mFileParentIcon=R.mipmap.icon_main_class_doc;
                                essFile.mFileIcon=R.mipmap.icon_txt;
                                addToMap(mapKey, essFile,essFileList);
                            }else if ( pathLowerCase.endsWith(".doc")  || pathLowerCase.endsWith(".docx")||pathLowerCase.endsWith(".wps")) {
                                mapKey = "文档#项-" + ConvertUtils.formatDate(time,"MM月dd日");
                                essFile.mFileParentIcon=R.mipmap.icon_main_class_doc;
                                essFile.mFileIcon=R.mipmap.icon_doc;
                                addToMap(mapKey, essFile,essFileList);
                            }else if (pathLowerCase.endsWith(".xls") ) {
                                mapKey = "文档#项-" + ConvertUtils.formatDate(time,"MM月dd日");
                                essFile.mFileParentIcon=R.mipmap.icon_main_class_doc;
                                essFile.mFileIcon=R.mipmap.icon_excl;
                                addToMap(mapKey, essFile,essFileList);
                            }else if ( pathLowerCase.endsWith(".pdf")) {
                                mapKey = "文档#项-" + ConvertUtils.formatDate(time,"MM月dd日");
                                essFile.mFileParentIcon=R.mipmap.icon_main_class_doc;
                                essFile.mFileIcon=R.mipmap.icon_pdf;
                                addToMap(mapKey, essFile,essFileList);
                            }else if ( pathLowerCase.endsWith(".ppt")) {
                                mapKey = "文档#项-" + ConvertUtils.formatDate(time,"MM月dd日");
                                essFile.mFileParentIcon=R.mipmap.icon_main_class_doc;
                                essFile.mFileIcon=R.mipmap.icon_ppt;
                                addToMap(mapKey, essFile,essFileList);
                            }else if (pathLowerCase.endsWith(".html")|| pathLowerCase.endsWith(".css")
                                    || pathLowerCase.endsWith(".h")|| pathLowerCase.endsWith(".c")
                                    || pathLowerCase.endsWith(".cpp") || pathLowerCase.endsWith(".mm")
                                    || pathLowerCase.endsWith(".m")|| pathLowerCase.endsWith(".java")
                                    || pathLowerCase.endsWith(".vbs")) {
                                mapKey = "文档#项-" + ConvertUtils.formatDate(time,"MM月dd日");
                                essFile.mFileParentIcon=R.mipmap.icon_main_class_doc;
                                essFile.mFileIcon=R.mipmap.icon_unknow;
                                addToMap(mapKey, essFile,essFileList);
                            }
                        }
                    }
                    for (Map.Entry<String, List<EssFile>> entry : essFileList.entrySet()) {
                        recentFiles.add(new RecentFile(entry.getKey(), entry.getValue()));
                    }
                }
                emitter.onNext(recentFiles);
            }
        }).subscribeOn(Schedulers.io());
    }

    private void addToMap(String key, EssFile file,Map<String, List<EssFile>> essFileList){
        if (essFileList.get(key) == null) {
            List<EssFile> list = new ArrayList<>();
            list.add(file);
            essFileList.put(key, list);
        } else {
            essFileList.get(key).add(file);
        }
    }
}
