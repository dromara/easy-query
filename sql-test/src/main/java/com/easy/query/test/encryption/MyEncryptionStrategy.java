package com.easy.query.test.encryption;

import com.easy.query.core.basic.extension.encryption.AbstractUnSupportEmojiAesBase64EncryptionStrategy;

/**
 * create time 2023/4/4 21:58
 * spring项目通过@Component即可 否则就自行添加到EasyQueryConfiguration.applyEasyEncryptionStrategy
 *
 * @author xuejiaming
 */
public class MyEncryptionStrategy extends AbstractUnSupportEmojiAesBase64EncryptionStrategy {
    @Override
    public String getIv() {
        return "A-16-Byte-String";
    }

    @Override
    public String getKey() {
        return "1234567890abcdef";
    }


}
