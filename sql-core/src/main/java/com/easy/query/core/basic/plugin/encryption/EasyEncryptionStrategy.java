package com.easy.query.core.basic.plugin.encryption;

/**
 * create time 2023/3/24 21:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyEncryptionStrategy {
    Object encrypt(Object plaintext);
    Object decrypt(Object ciphertext);
}
