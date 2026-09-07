package com.easy.query.test.encryption;

/**
 * SM4加密策略(hex存储) 支持emoji和中英文特殊字符 支持高性能like查询
 * spring项目通过@Component即可 否则就自行添加到QueryConfiguration.applyEncryptionStrategy
 * key必须是16字节
 *
 * @author xuejiaming
 */
public class DefaultSafeSm4EasyEncryptionStrategy extends AbstractSupportEmojiSm4HexEncryptionStrategy {
    @Override
    public String getKey() {
        return "sm4key1234567890";
    }
}
