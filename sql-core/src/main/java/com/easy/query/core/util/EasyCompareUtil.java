package com.easy.query.core.util;

import java.util.UUID;

/**
 * create time 2023/5/3 23:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyCompareUtil {
    private EasyCompareUtil(){}

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static int safeCompare(final Comparable x, final Comparable y, final boolean asc, final boolean caseSensitive) {
        if (asc) {
            return doSafeCompare(x, y,caseSensitive);
        }
        return doSafeCompare(y, x,caseSensitive);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static int doSafeCompare(final Comparable x, final Comparable y, final boolean caseSensitive) {

        if (x == null && y == null) {
            return 0;
        }
        if (x == null) {
            return -1;
        }
        if (y == null) {
            return 1;
        }
        if (x instanceof UUID && y instanceof UUID) {
            return compareUUID0((UUID) x, (UUID) y);
        }
        if (!caseSensitive && x instanceof String && y instanceof String) {
            return compareToIgnoreCaseString((String) x, (String) y);
        }
        return x.compareTo(y);
    }

    private static int compareToIgnoreCaseString(String x, String y) {
        return x.toUpperCase().compareTo(y.toUpperCase());
    }

    private static int compareUUID0(UUID x, UUID y) {
        byte[] b1 = toByteArray(x);
        byte[] b2 = toByteArray(y);
        for (int i = 0; i < 16; i++) {
            int cmp = Byte.compare(b1[i], b2[i]);
            if (cmp != 0) {
                return cmp;
            }
        }
        return 0;
    }

    private static byte[] toByteArray(UUID uuid) {
        byte[] byteArray = new byte[16];
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        for (int i = 0; i < 8; i++) {
            byteArray[i] = (byte) (msb >>> 8 * (7 - i));
            byteArray[8 + i] = (byte) (lsb >>> 8 * (7 - i));
        }
        return byteArray;
    }

}
