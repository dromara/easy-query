package com.easy.query.core.basic.plugin.encryption;

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
public abstract class AbstractAesBase64EasyEncryptionStrategy implements EasyEncryptionStrategy {
    private static final Log log = LogFactory.getLog(AbstractAesBase64EasyEncryptionStrategy.class);

//    /**
//     * AES/CBC/PKCS5Padding
//     * 16 字节加密后数据长度 32
//     * 不满 16 字节加密后长度 16
//     * 4长度加密后肯定还是16
//     * 16小于24 所以解密用24位为一组分割base64,多占用8位长度浪费后续优化
//     */
//    private final int minWordLength = 4;
//    private final int byteLength = 16;

    public abstract String getIv();

    public abstract String getKey();

    /**
     * 加密最小长度
     * @return
     */
    public int encryptWordMinLength(){
        return 4;
    }

    /**
     * 一个中文占用2 如果用中文来查询那么需要两个中文或者一个中文2个非中文才可以查询到数据
     * @return
     */
    public int chineseCharOccupancyLength(){
        return 2;
    }

    /**
     * 非中文占用1 如果用非中文来查询需要4个字符才可以查询
     * @return
     */
    public int otherCharOccupancyLength(){
        return 1;
    }

    public boolean throwIfDecryptFail() {
        return true;
    }

    @Override
    public Object encrypt(Class<?> entityClass,String propertyName,Object plaintext) {

        if (plaintext == null) {
            return null;
        }
        try {
            return doEncrypt(plaintext);
        }catch (Exception exception){
            log.error(EasyClassUtil.getInstanceSimpleName(this)+" "+ EasyClassUtil.getSimpleName(entityClass)+"."+ "."+propertyName+" decrypt error:" + plaintext, exception);
            throw exception;
        }
    }

    protected Object doEncrypt(Object plaintext){
        String plaintextString = plaintext.toString();
        List<String> stringCharSegments = EasyStringUtil.getStringCharSegments(plaintextString, encryptWordMinLength(),otherCharOccupancyLength(),chineseCharOccupancyLength());
        //符合要求譬如最少4个非中文字符或者2个中文字的情况下,可以选择抛错重写或者直接加密对应的值
        if (EasyCollectionUtil.isEmpty(stringCharSegments)) {
            stringCharSegments.add(plaintextString);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String stringCharSegment : stringCharSegments) {
            String str= EasyAesUtil.encrypt(stringCharSegment, getKey(), getIv(),StandardCharsets.UTF_8);
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    @Override
    public Object decrypt(Class<?> entityClass,String propertyName,Object ciphertext) {
        try {
            return doDecrypt(entityClass,propertyName,ciphertext);
        } catch (Exception exception) {
            log.error(EasyClassUtil.getInstanceSimpleName(this)+" "+ EasyClassUtil.getSimpleName(entityClass)+"."+ "."+propertyName+" decrypt error:" + ciphertext, exception);
            if (throwIfDecryptFail()) {
                throw exception;
            }
        }
        return ciphertext;
    }

    private Object doDecrypt(Class<?> entityClass,String propertyName,Object ciphertext) {
        if (ciphertext == null) {
            return null;
        }
        String ciphertextString = ciphertext.toString();
        if (ciphertextString.length() % 24 != 0) {
            if (throwIfDecryptFail()) {
                throw new IllegalArgumentException(EasyClassUtil.getSimpleName(entityClass)+"."+propertyName+" decrypt cant decode base64:" + ciphertext);
            }
            return ciphertext;
        }

        List<String> segments = EasyStringUtil.splitString(ciphertextString, 24);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < segments.size(); i++) {
            String str= EasyAesUtil.decrypt(segments.get(i), getKey(), getIv(),StandardCharsets.UTF_8);
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
