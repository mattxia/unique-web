package org.unique.common.tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 上传工具类
 * @author:rex
 * @date:2014年8月14日
 * @version:1.0
 */
public class UploadUtils {

    /**
     * 文件允许格式
     */
    public static String[] FILE_TYPE = { ".rar", ".doc", ".docx", ".zip", ".pdf", ".txt", ".swf", ".wmv" };

    /**
     * 图片允许格式
     */
    public static String[] PHOTO_TYPE = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };

    public static boolean isFileType(String fileName, String[] typeArray) {
        for (String type : typeArray) {
            if (fileName.toLowerCase().endsWith(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 递归获得目录的所有地址
     * 
     * @param realpath
     * @param files
     * @param fileType
     * @return
     */
    public static List<java.io.File> getFiles(String realpath, List<File> files, String[] fileType) {
        File realFile = new File(realpath);
        if (realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
                if (file.isDirectory()) {
                    getFiles(file.getAbsolutePath(), files, fileType);
                } else {
                    if (isFileType(file.getName(), fileType)) {
                        files.add(file);
                    }
                }
            }
        }
        return files;
    }

    /**
     * 得到文件上传的相对路径
     * 
     * @param fileName 文件名
     * @return
     */
    public static String getUploadPath(String fileName, long time) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
        String uploadPath = "/upload/" + formater.format(new Date()) + "/" + time + getFileExt(fileName);
        File dir = new File(getWebRoot() + uploadPath);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        return uploadPath;
    }

    /**
     * @函数功能：获取项目物理路径
     * @参数： @return
     * @创建人：rex
     * @return String
     */
    public static String getWebRoot() {
        File f = new File("");
        return f.getAbsoluteFile().getAbsoluteFile().getPath();
    }

    /**
     * 获取文件扩展名
     * 
     * @return string
     */
    public static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 删除物理文件
     * 
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(getWebRoot() + path);
        file.delete();
    }
}
