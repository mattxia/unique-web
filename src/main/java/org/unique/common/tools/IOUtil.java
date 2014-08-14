package org.unique.common.tools;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO工具类
 * @author:rex
 * @date:2014年8月14日
 * @version:1.0
 */
public class IOUtil {

    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
