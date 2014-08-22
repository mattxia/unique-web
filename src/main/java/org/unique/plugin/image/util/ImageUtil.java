package org.unique.plugin.image.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.log4j.Logger;
import org.unique.common.tools.FileUtil;

/**
 * 图片工具类
 * 
 * @author rex
 */
public class ImageUtil {

    private static Logger logger = Logger.getLogger(ImageUtil.class);

    /**
     * 获取图片宽高(图片越大，消耗的时间越长，针对百K以下的图片速度较快)
     * 
     * @param file
     * @return
     */
    public static Map<String, Integer> getImageWH(File file) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            BufferedImage Bi = ImageIO.read(file);
            map.put("width", Bi.getWidth());
            map.put("height", Bi.getHeight());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return map;
    }

    /**
     * 获取图片宽高(不论图片大小，基本恒定时间，在100ms左右)
     * 
     * @param file
     * @param suffix 文件后缀
     * @return
     */
    public static Map<String, Integer> getImageWH(File file, String suffix) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(suffix);
            ImageReader reader = (ImageReader) readers.next();
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            reader.setInput(iis, true);
            map.put("width", reader.getWidth(0));
            map.put("height", reader.getHeight(0));
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return map;
    }
    
    /**
     * 获取图片缩略图路径
     * 
     * @param filePath
     * @param width
     * @param height
     * @param quality
     * @return
     */
    public static String getThumbPath(String filePath, int width, int height, int quality) {
        String kzm = null;
        if (quality > 0) {
            kzm = FileUtil.getNoSuffixFilePath(filePath) + "_" + width + "x" + height + "_" + quality + "." + FileUtil.getSuffix(filePath);
        } else {
            kzm = FileUtil.getNoSuffixFilePath(filePath) + "_" + width + "x" + height + "." + FileUtil.getSuffix(filePath);
        }
        return kzm;
    }

    /**
     * 获取缩略图路径
     * 
     * @param filePath
     * @param scale
     * @param quality
     * @return
     */
    public static String getThumbScalePath(String filePath, float scale, int quality) {
        String kzm = null;
        if (quality > 0) {
            kzm = FileUtil.getNoSuffixFilePath(filePath) + "_" + BigDecimalUtil.decimal2percent(scale, 0) + "_" + quality + "."
                    + FileUtil.getSuffix(filePath);
        } else {
            kzm = FileUtil.getNoSuffixFilePath(filePath) + "_" + BigDecimalUtil.decimal2percent(scale, 0) + "." + FileUtil.getSuffix(filePath);
        }
        return kzm;
    }

    /**
     * 获取图片保存名称
     * 
     * @param filePath
     * @param scale
     * @param width
     * @param height
     * @param quality
     * @param rotate
     * @return
     */
    public static String getImgPath(String filePath, Integer width, Integer height, Double quality, Double scale,  Double rotate) {
        // abc_300x500_s_xx_q90_r90.png
        String k = FileUtil.getNoSuffixFilePath(filePath);
        StringBuffer sb = new StringBuffer(k);
        if (null != width && null != height && width != 0 && height != 0) {
            sb.append("_" + width + "x" + height);
        }
        if (null != scale && scale.compareTo(0.0D) > 0) {
            sb.append("_" + scale);
        }
        if (null != quality && quality > 0) {
            sb.append("_q" + quality);
        }
        if (null != rotate && rotate.compareTo(0.0D) > 0) {
            sb.append("_r" + rotate);
        }
        return sb.append("." + FileUtil.getSuffix(filePath)).toString();
    }
}
