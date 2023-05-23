package com.easy.query.core.util;

import java.util.Base64;

/**
 * create time 2023/3/24 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyBase64Util {
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();

    public static byte[] encode(byte[] data) {
        return encoder.encode(data);
    }

    public static byte[] decode(byte[] data) {
        return decoder.decode(data);
    }
}
