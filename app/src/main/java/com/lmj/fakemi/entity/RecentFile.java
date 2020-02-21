package com.lmj.fakemi.entity;

import com.lmj.fakemi.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 * Author:        LMJ
 * CreateDate:     2019/7/23 15:03
 * Description:     最近文件
 */
public class RecentFile {

    public String title;

    public boolean isExpand;

    private boolean isPic;

    public int fileIcon;

    private List<EssFile> mFileList;


    public RecentFile(String title, List<EssFile> fileList) {
        mFileList = fileList;
        this.title = title.replace("#",fileList.size()+"");
        String time =  this.title.substring( this.title.lastIndexOf("-")+1);
        this.title =  this.title.replace(time,getTitleStr(time));
        fileIcon = fileList.get(0).mFileParentIcon;
        isPic = fileList.get(0).mFileParentIcon== R.mipmap.icon_main_class_pic;
    }


    public List<EssFile> getFileList() {
        if (!isExpand){
            if (isPic){
                if (mFileList.size()>8){
                  return  new ArrayList<>(mFileList.subList(0,8));
                }
            }else {
                if (mFileList.size()>3){
                    return  new ArrayList<>(mFileList.subList(0,3));
                }
            }
        }
        return mFileList;
    }

    public boolean hasExpand(){
        if (isPic){
            return mFileList.size()>8;
        }else {
            return mFileList.size()>3;
        }
    }

    /**
     * 判断给定字符串时间是否为今日
     */
    private String getTitleStr(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        String nowDate = formatter.format(System.currentTimeMillis());
        if (nowDate.equals(time)) {
            return "今天";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            long beforeDate = calendar.getTime().getTime();
            String yesterDay = formatter.format(beforeDate);
            if (yesterDay.equals(time)) {
                return "昨天";
            } else {
                return time;
            }
        }
    }
}
