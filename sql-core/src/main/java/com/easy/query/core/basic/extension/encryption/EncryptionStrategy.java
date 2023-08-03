package com.easy.query.core.basic.extension.encryption;

/**
 * create time 2023/3/24 21:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EncryptionStrategy {
    /**
     * 将当前数据加密成数据库对应的加密数据
     * @param entityClass
     * @param propertyName
     * @param plaintext
     * @return
     */
    Object encrypt(Class<?> entityClass,String propertyName,Object plaintext);

    /**
     * 将数据库密文解密成明文数据
     * @param entityClass
     * @param propertyName
     * @param ciphertext
     * @return
     */
    Object decrypt(Class<?> entityClass,String propertyName,Object ciphertext);
}
