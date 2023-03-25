package com.easy.query.core.encryption;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.util.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * create time 2023/3/24 21:36
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractAesEasyEncryptionStrategy implements EasyEncryptionStrategy {
    private static final Log log = LogFactory.getLog(AbstractAesEasyEncryptionStrategy.class);

    /**
     * AES/CBC/PKCS5Padding
     * 16 字节加密后数据长度 32
     * 不满 16 字节加密后长度 16
     * 4长度加密后肯定还是16
     * 16小于24 所以解密用24位为一组分割base64,多占用8位长度浪费后续优化
     */
    private final int minWordLength = 4;
    private final int byteLength = 16;

    public abstract String getIv();

    public abstract String getKey();

    public boolean throwIfDecryptFail() {
        return true;
    }

    @Override
    public Object encrypt(Object plaintext) {
        if (plaintext == null) {
            return null;
        }
        String plaintextString = plaintext.toString();
        List<String> stringCharSegments = StringUtil.getStringCharSegments(plaintextString, minWordLength);
        if (ArrayUtil.isEmpty(stringCharSegments)) {
            stringCharSegments.add(plaintextString);
//            throw new EasyQueryException("输入字符不符合要求,中文一个字符2位长度,英文数字字母等一个字符一位长度，最小输入数据必须满足4位长度:" + plaintextString);
        }
//        byte[] mergedArray = new byte[stringCharSegments.size() * byteLength];
//        int destPos = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (String stringCharSegment : stringCharSegments) {
            byte[] encrypt = AesUtil.encrypt(stringCharSegment, getKey(), getIv());
            byte[] encode = Base64Util.encode(encrypt);
            stringBuilder.append(new String(encode, StandardCharsets.UTF_8));
//            System.arraycopy(encrypt, 0, mergedArray, destPos, encrypt.length);
//            destPos += encrypt.length;
        }
//        byte[] encode = Base64Util.encode(mergedArray);
//        return new String(encode, StandardCharsets.UTF_8);
        return stringBuilder.toString();
    }

    @Override
    public Object decrypt(Object ciphertext) {
        try {
            return doDecrypt(ciphertext);
        } catch (Exception exception) {
            log.error(ClassUtil.getInstanceSimpleName(this) + ".decrypt error:" + ciphertext, exception);
            if (throwIfDecryptFail()) {
                throw exception;
            }
        }
        return ciphertext;
    }

    private Object doDecrypt(Object ciphertext) {
        if (ciphertext == null) {
            return null;
        }
        String ciphertextString = ciphertext.toString();
//        byte[] decode = Base64Util.tryDecode(ciphertextString.getBytes(StandardCharsets.UTF_8));
        //当前数据非base64或者base64但是不是aes加密的返回原始数据
        if (ciphertextString.length() % 24 != 0) {
            if (throwIfDecryptFail()) {
                throw new IllegalArgumentException("decrypt cant decode base64:" + ciphertext);
            }
            return ciphertext;
        }

        List<String> segments = StringUtil.splitString(ciphertextString, 24);
//        byte[][] chunks = ArrayUtil.splitByteArray(decode, 16);

        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < chunks.length; i++) {
//            byte[] chunk = chunks[i];
//            byte[] decrypt = AesUtil.decrypt(chunk, getKey(), getIv());
//            String str = new String(decrypt, StandardCharsets.UTF_8);
//            boolean last = i == (chunks.length - 1);
//            if (last) {
//                stringBuilder.append(str);
//            } else {
//                stringBuilder.append(str.charAt(0));
//            }
//        }
        for (int i = 0; i < segments.size(); i++) {
            String str = AesUtil.decryptToString(segments.get(i), getKey(), getIv());
            boolean last = i == (segments.size() - 1);
            if (last) {
                stringBuilder.append(str);
            } else {
                stringBuilder.append(str.charAt(0));
            }
        }
        return stringBuilder.toString();
    }
}
