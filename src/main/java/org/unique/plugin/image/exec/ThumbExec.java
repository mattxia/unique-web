package org.unique.plugin.image.exec;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

import org.apache.log4j.Logger;

/**
 * 缩略图处理器
 * 
 * @author rex
 */
public class ThumbExec {

    private static Logger logger = Logger.getLogger(ThumbExec.class);

    /**
     * 缩略图片
     * 
     * @param filePath 源图片位置
     * @param thumbPath 缩略后的位置
     * @param width 缩略宽
     * @param height 缩略高
     * @param scale 按比例缩放
     * @param quality 图片质量百分数
     * @param rotate 旋转角度
     * @return
     */
    public static String thumb(String filePath, String thumbPath, int width, int height, double scale, double quality, double rotate) {
        File img = new File(thumbPath);

        if (img.exists()) {
            return img.getPath();
        }
        Builder<File> f = Thumbnails.of(filePath);
        if (width > 0 && height > 0) {
            f.size(width, height);
        }
        if (scale > 0.0D) {
            f.scale(scale);
        }
        if (quality > 0.0D) {
            f.outputQuality(quality);
        }
        if (rotate > 0.0D) {
            f.rotate(rotate);
        }
        try {
            f.toFile(img);
            return img.getPath();
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return filePath;
    }

    public static void main(String[] args) {
        String filePath = "D:\\tomca7\\webapps\\unique-img-plugin\\upload\\12674158787444.jpg";
        File img = new File("D:\\tomca7\\webapps\\unique-img-plugin\\upload\\12674158787444_1.jpg");
        Builder<File> f = Thumbnails.of(filePath);
        f.size(200, 200);
        // f = f.scale(1);
        // f = f.outputQuality(quality);
        if("a".equals("a")){
            f.rotate(180);
        }
        try {
            f.toFile(img);
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
    }
}
