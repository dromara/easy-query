package com.easy.query.core.util;

import com.easy.query.core.exception.EasyQueryException;

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

    public static String encryptToString(String content, String key, String iv) {
        try {
            byte[] encrypted= doEncrypt(content, key, iv);
            return new String(Base64Util.encode(encrypted), charset);
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }
    public static byte[] encrypt(String content, String key, String iv) {
        try {
            return doEncrypt(content, key, iv);
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }

    public static byte[] doEncrypt(String content, String key, String iv) throws Exception {
        byte[] keyBytes = key.getBytes(charset);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        byte[] ivBytes = iv.getBytes(charset);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        return cipher.doFinal(content.getBytes(charset));
//        return new String(encoder.encode(encrypted), charset);
    }

    public static String decryptToString(String content, String key, String iv) {
        try {
            byte[] original= doDecryptFromString(content, key, iv);
            return new String(original, charset);
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }
    public static byte[] decrypt(byte[] encrypted, String key, String iv) {
        try {
            return doDecrypt(encrypted, key, iv);
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }

    public static byte[] doDecryptFromString(String content, String key, String iv) throws Exception {
        byte[] encrypted = Base64Util.decode(content.getBytes(charset));
        return doDecrypt(encrypted,key,iv);
    }

    public static byte[] doDecrypt(byte[] encrypted, String key, String iv) throws Exception {
        byte[] keyBytes = key.getBytes(charset);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        byte[] ivBytes = iv.getBytes(charset);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        return cipher.doFinal(encrypted);
    }
}
