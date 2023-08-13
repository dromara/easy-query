package com.easy.query.test.conversion;

import com.easy.query.core.basic.extension.encryption.AbstractAesBase64EncryptionStrategy;

/**
 * create time 2023/8/12 21:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class JavaEncryptionStrategy extends AbstractAesBase64EncryptionStrategy {
    @Override
    public String getIv() {
        return "1234567890123456";
    }

    @Override
    public String getKey() {
        return "1234561234567890";
    }
}
