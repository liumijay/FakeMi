package com.lmj.fakemi.entity;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import com.lmj.fakemi.util.FileSizeUtil;
import com.lmj.fakemi.util.FileUtils;
import com.lmj.fakemi.util.MimeType;
import com.lmj.fakemi.util.PathUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * EssFile
 * Created by 李波 on 2018/2/5.
 */

public class EssFile implements Parcelable {

    public String mFilePath;
    public String mFileSize;
    public int mFileIcon = -1;
    public int mFileParentIcon = -1;
    public String mModifyTime;
    public String childFolderCount;
    public String childFileCount;
    public boolean isChecked = false;
    public boolean isExits = false;
    public boolean isDirectory = false;
    public boolean isFile = false;
    public boolean isShowCheck = false;
    public String mFileName;
    public Uri uri;

    public EssFile(String path) {
        mFilePath = path;
        File file = new File(mFilePath);
        if (file.exists()) {
            isExits = true;
            isDirectory = file.isDirectory();
            isFile = file.isFile();
            mFileName = file.getName();
            if (!file.isDirectory()){
                mFileSize = FileSizeUtil.FormetFileSize(file.length());
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            mModifyTime = formatter.format(file.lastModified());
        }
    }

    public EssFile(File file) {
        mFilePath = file.getAbsolutePath();
        if (file.exists()) {
            isExits = true;
            isDirectory = file.isDirectory();
            isFile = file.isFile();
            mFileName = file.getName();
            if (!isDirectory){
                mFileSize = FileSizeUtil.FormetFileSize(file.length());
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            mModifyTime = formatter.format(file.lastModified());
        }
    }


    public void setChildCounts(String childFileCount, String childFolderCount) {
        this.childFileCount = childFileCount;
        this.childFolderCount = childFolderCount;
    }


    public File getFile() {
        return new File(mFilePath);
    }

    public String getExtension(){
        return FileUtils.getExtension(mFilePath);
    }

    public String getFileDesc(){
        return mModifyTime+" "+mFileSize;
    }

    public String getFolderDesc(){
        return (Integer.parseInt(childFileCount)+ Integer.parseInt(childFolderCount))+"项 | "+mModifyTime.substring(2);
    }

    public static List<EssFile> getEssFileList(List<File> files) {
        List<EssFile> essFileList = new ArrayList<>();
        for (File file : files) {
            essFileList.add(new EssFile(file));
        }
        return essFileList;
    }

    public static ArrayList<EssFile> getEssFileList(Context context, Set<EssFile> essFileSet) {
        ArrayList<EssFile> essFileArrayList = new ArrayList<>();
        for (EssFile ess_file :
                essFileSet) {
            ess_file.mFilePath = PathUtils.getPath(context, ess_file.uri);
            essFileArrayList.add(ess_file);
        }
        return essFileArrayList;
    }


    @Override
    public String toString() {
        return "EssFile{" +
                "mFilePath='" + mFilePath + '\'' +
                ", mFileName='" + mFileName + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EssFile)) {
            return false;
        }

        EssFile other = (EssFile) obj;
        if (uri == null) {
            return mFilePath.equalsIgnoreCase(other.mFilePath);
        } else {
            return uri.equals(other.uri);
        }
    }

    @Override
    public int hashCode() {
        int result = mFilePath != null ? mFilePath.hashCode() : 0;
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        result = 31 * result ;
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mFilePath);
        dest.writeString(this.mFileSize);
        dest.writeInt(this.mFileIcon);
        dest.writeString(this.mModifyTime);
        dest.writeString(this.childFolderCount);
        dest.writeString(this.childFileCount);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isExits ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isDirectory ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFile ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isShowCheck ? (byte) 1 : (byte) 0);
        dest.writeString(this.mFileName);
        dest.writeParcelable(this.uri, flags);
    }

    protected EssFile(Parcel in) {
        this.mFilePath = in.readString();
        this.mFileSize = in.readString();
        this.mFileIcon = in.readInt();
        this.mModifyTime = in.readString();
        this.childFolderCount = in.readString();
        this.childFileCount = in.readString();
        this.isChecked = in.readByte() != 0;
        this.isExits = in.readByte() != 0;
        this.isDirectory = in.readByte() != 0;
        this.isFile = in.readByte() != 0;
        this.isShowCheck = in.readByte() != 0;
        this.mFileName = in.readString();
        this.uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<EssFile> CREATOR = new Creator<EssFile>() {
        @Override
        public EssFile createFromParcel(Parcel source) {
            return new EssFile(source);
        }

        @Override
        public EssFile[] newArray(int size) {
            return new EssFile[size];
        }
    };
}
