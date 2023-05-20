package com.easy.query.core.basic.plugin.encryption;

/**
 * create time 2023/3/24 21:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EncryptionStrategy {
    Object encrypt(Class<?> entityClass,String propertyName,Object plaintext);
    Object decrypt(Class<?> entityClass,String propertyName,Object ciphertext);
}
