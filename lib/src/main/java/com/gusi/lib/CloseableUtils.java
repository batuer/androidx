package com.gusi.lib;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Ylw
 * @since 2019/10/30 21:59
 */
public class CloseableUtils {
    public static void closeCloseables(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }
}
