package com.easy.query.test.encryption;

import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 支持emoji表情符和全中英文特殊字符的SM4加密策略
 * 和{@link com.easy.query.core.basic.extension.encryption.AbstractSupportEmojiAesBase64EncryptionStrategy}算法一致
 * 区别是使用国密SM4替代AES并且使用16进制(hex)替代base64进行密文存储
 * SM4/ECB/PKCS7Padding 16字节加密后数据长度16 转成hex后固定32个字符为一组
 * 明文按最小加密长度分段后每段独立加密拼接,相同明文片段密文相同从而支持高性能like查询
 *
 * @author xuejiaming
 */
public abstract class AbstractSupportEmojiSm4HexEncryptionStrategy implements EncryptionStrategy {
    private static final Log log = LogFactory.getLog(AbstractSupportEmojiSm4HexEncryptionStrategy.class);
    /**
     * 一个分段(16字节)加密后转hex的固定长度
     */
    private static final int HEX_GROUP_SIZE = 32;

    public abstract String getKey();

    /**
     * 加密最小长度
     *
     * @return
     */
    public int encryptWordMinLength() {
        return 4;
    }

    /**
     * 一个中文占用2 如果用中文来查询那么需要两个中文或者一个中文2个非中文才可以查询到数据
     *
     * @return
     */
    public int chineseCharOccupancyLength() {
        return 2;
    }

    /**
     * 非中文占用1 如果用非中文来查询需要4个字节才可以查询
     *
     * @return
     */
    public int otherCharOccupancyLength() {
        return 1;
    }

    public boolean throwIfDecryptFail() {
        return true;
    }

    @Override
    public Object encrypt(Class<?> entityClass, String propertyName, Object plaintext) {

        if (plaintext == null) {
            return null;
        }
        try {
            return doEncrypt(plaintext);
        } catch (Exception exception) {
            log.error(EasyClassUtil.getInstanceSimpleName(this) + " " + EasyClassUtil.getSimpleName(entityClass) + "." + "." + propertyName + " decrypt error:" + plaintext, exception);
            throw exception;
        }
    }

    protected Object doEncrypt(Object plaintext) {
        String plaintextString = plaintext.toString();
        List<String> stringCharSegments = EasyStringUtil.getStringSafeCharSegments(plaintextString, encryptWordMinLength(), otherCharOccupancyLength(), chineseCharOccupancyLength());
        //符合要求譬如最少4个非中文字符或者2个中文字的情况下,可以选择抛错重写或者直接加密对应的值
        if (EasyCollectionUtil.isEmpty(stringCharSegments)) {
            stringCharSegments.add(plaintextString);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String stringCharSegment : stringCharSegments) {
            String str = Sm4Util.encrypt(stringCharSegment, getKey(), StandardCharsets.UTF_8);
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    @Override
    public Object decrypt(Class<?> entityClass, String propertyName, Object ciphertext) {
        try {
            return doDecrypt(entityClass, propertyName, ciphertext);
        } catch (Exception exception) {
            log.error(EasyClassUtil.getInstanceSimpleName(this) + " " + EasyClassUtil.getSimpleName(entityClass) + "." + "." + propertyName + " decrypt error:" + ciphertext, exception);
            if (throwIfDecryptFail()) {
                throw exception;
            }
        }
        return ciphertext;
    }

    private Object doDecrypt(Class<?> entityClass, String propertyName, Object ciphertext) {
        if (ciphertext == null) {
            return null;
        }
        String ciphertextString = ciphertext.toString();
        if (ciphertextString.length() % HEX_GROUP_SIZE != 0) {
            if (throwIfDecryptFail()) {
                throw new IllegalArgumentException(EasyClassUtil.getSimpleName(entityClass) + "." + propertyName + " decrypt cant decode hex:" + ciphertext);
            }
            return ciphertext;
        }

        List<String> segments = EasyStringUtil.splitByGroupSize(ciphertextString, HEX_GROUP_SIZE);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < segments.size(); i++) {
            String str = Sm4Util.decrypt(segments.get(i), getKey(), StandardCharsets.UTF_8);
            boolean last = i == (segments.size() - 1);
            if (last) {
                stringBuilder.append(str);
            } else {
                String safeChar = EasyStringUtil.safeSubstringFirst(str);
                stringBuilder.append(safeChar);
            }
        }
        return stringBuilder.toString();
    }
}
