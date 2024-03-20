package com.easy.query.test.encryption;

import com.easy.query.core.basic.extension.encryption.AbstractSupportEmojiAesBase64EncryptionStrategy;

/**
 * create time 2023/3/25 09:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSafeAesEasyEncryptionStrategy extends AbstractSupportEmojiAesBase64EncryptionStrategy {
    @Override
    public String getIv() {
        return "A-16-Byte-String";
    }

    @Override
    public String getKey() {
        return "abcdef1234567890";
    }


}
