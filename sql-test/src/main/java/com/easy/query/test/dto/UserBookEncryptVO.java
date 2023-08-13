package com.easy.query.test.dto;

import com.easy.query.core.annotation.Encryption;
import com.easy.query.test.conversion.JavaEncryptionStrategy;
import lombok.Data;

/**
 * create time 2023/8/13 09:31
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class UserBookEncryptVO {
    private String id;
    private String userId;
    private String name;
    private String userName;
    private String userPhone;

    /**
     * 返回vo结果如果没有该注解将是返回数据库对应的列的原始数据
     */
    @Encryption(strategy = JavaEncryptionStrategy.class)
    private String userAddress;
}
