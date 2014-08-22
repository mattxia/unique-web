package org.unique.common.tools;

import java.io.Closeable;
import java.io.IOException;

/**
 * io util
 * @author:rex
 * @date:2014年8月22日
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
