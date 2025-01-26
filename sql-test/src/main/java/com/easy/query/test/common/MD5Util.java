package com.easy.query.test.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * create time 2025/1/26 09:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class MD5Util {
    public static String getMD5Hash(String input) {

        return getMD5Hash(input, StandardCharsets.UTF_8);
    }

    public static String getMD5Hash(String input, Charset charset) {

        // 将输入字符串转换为字节数组
        byte[] inputBytes = input.getBytes(charset);
        return getMD5Hash(inputBytes);
    }
    public static String getMD5Hash(byte[] inputBytes) {
        try {
            // 创建 MessageDigest 对象并指定算法为 MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 使用字节数组更新摘要
            md.update(inputBytes);

            // 获取摘要的字节数组
            byte[] digestBytes = md.digest();

            // 将摘要字节数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digestBytes) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
