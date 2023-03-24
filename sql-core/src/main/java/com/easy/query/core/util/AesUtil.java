package com.easy.query.core.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * 文件说明
 *
 * @author xuejiaming
 */
public class AesUtil {
    private static final String IV_STRING = "A-16-Byte-String";
    private static final String charset = "UTF-8";
    private static final int SEGMENT_LENGTH = 10;
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();

    public static String encrypt(String content, String key) throws Exception {
        byte[] keyBytes = key.getBytes(charset);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        byte[] ivBytes = IV_STRING.getBytes(charset);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        StringBuilder sb = new StringBuilder();
        int length = content.length();
        ;
        for (int i = 0; i < length; i += SEGMENT_LENGTH) {
            String segment = content.substring(i, Math.min(i + SEGMENT_LENGTH, length));
            byte[] encrypted = cipher.doFinal(segment.getBytes(charset));
            sb.append(new String(encoder.encode(encrypted), charset));
        }
        return sb.toString();
    }

    public static String decrypt(String content, String key) throws Exception {
        byte[] keyBytes = key.getBytes(charset);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        byte[] ivBytes = IV_STRING.getBytes(charset);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        StringBuilder sb = new StringBuilder();
        int length = content.length();

        for (int i = 0; i < length; i += SEGMENT_LENGTH * 4 / 3) {
            String segment = content.substring(i, Math.min(i + SEGMENT_LENGTH * 4 / 3, length));
            byte[] encrypted = decoder.decode(segment.getBytes(charset));
            byte[] original = cipher.doFinal(encrypted);
            sb.append(new String(original, charset));
        }
        return sb.toString();
    }
}
