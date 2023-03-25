package com.easy.query.core.util;

/**
 * create time 2023/3/25 14:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class HexUtil {
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(HEX_CHARS[(b >> 4) & 0x0f]);
            sb.append(HEX_CHARS[b & 0x0f]);
        }
        return sb.toString();
    }

    public static byte[] tryHexStringToBytes(String hexString) {
        try {
            return hexStringToBytes(hexString);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        int len = hexString.length() / 2;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            int high = Character.digit(hexString.charAt(i * 2), 16);
            int low = Character.digit(hexString.charAt(i * 2 + 1), 16);
            if (high < 0 || low < 0) {
                return null;
            }
            bytes[i] = (byte) ((high << 4) + low);
        }
        return bytes;
    }
}
