package com.gusi.lib;

/**
 * @author Ylw
 * @since 2019/10/29 22:25
 */
public class ByteUtils {

    public static byte[] long2Bytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte)((num >> offset) & 0xff);
        }
        return byteNum;
    }

    public static long bytes2Long(byte[] byteNum) {
        long num = 0;
        for (int ix = 0; ix < 8; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        return num;
    }

    /**
     * int转byte数组
     * 
     * @param num
     * @return
     */
    public static byte[] intToByte(int num) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte)((num >> 24) & 0xff);
        bytes[1] = (byte)((num >> 16) & 0xff);
        bytes[2] = (byte)((num >> 8) & 0xff);
        bytes[3] = (byte)(num & 0xff);
        return bytes;
    }

    /**
     * byte数组转int类型的对象
     * 
     * @param bytes
     * @return
     */
    public static int byte2Int(byte[] bytes) {
        return (bytes[0] & 0xff) << 24 | (bytes[1] & 0xff) << 16 | (bytes[2] & 0xff) << 8 | (bytes[3] & 0xff);
    }
}
