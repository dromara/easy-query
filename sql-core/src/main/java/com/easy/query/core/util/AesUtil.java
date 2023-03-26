package com.easy.query.core.util;

import com.easy.query.core.exception.EasyQueryException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * 文件说明
 *
 * @author xuejiaming
 */
public class AesUtil {

    public static String encrypt(String plaintext, String key, String iv,Charset charset) {
        byte[] encrypt = encrypt(plaintext.getBytes(charset), key.getBytes(charset), iv.getBytes(charset));
        return new String(Base64Util.encode(encrypt),charset);
    }
    public static byte[] encrypt(byte[] plaintext, byte[] key, byte[] iv) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(plaintext);
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }

    public static String decrypt(String content, String key, String iv, Charset charset) {
        byte[] decrypt = decrypt(Base64Util.decode(content.getBytes(charset)), key.getBytes(charset), iv.getBytes(charset));
        return new String(decrypt,charset);
    }
    public static byte[] decrypt(byte[] encrypted, byte[] key, byte[] iv) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }
}
