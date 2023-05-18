package com.easy.query.test.encryption;

import com.easy.query.core.basic.plugin.encryption.EasyEncryptionStrategy;
import com.easy.query.core.util.EasyBase64Util;

import java.nio.charset.StandardCharsets;

/**
 * create time 2023/4/4 11:38
 * 如果是spring项目可以通过@Component 否则就自行添加到EasyQueryConfiguration.applyEasyEncryptionStrategy
 *
 * @author xuejiaming
 */
public class Base64EncryptionStrategy implements EasyEncryptionStrategy {
    @Override
    public Object encrypt(Class<?> entityClass, String propertyName, Object plaintext) {
        if(plaintext==null){
            return null;
        }
        return new String(EasyBase64Util.encode(plaintext.toString().getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
    }

    @Override
    public Object decrypt(Class<?> entityClass, String propertyName, Object ciphertext) {
        if(ciphertext==null){
            return null;
        }

        return new String(EasyBase64Util.decode(ciphertext.toString().getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
    }
}
