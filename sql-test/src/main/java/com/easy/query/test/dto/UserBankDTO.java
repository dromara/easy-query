package com.easy.query.test.dto;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

/**
 * create time 2025/9/3 10:11
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class UserBankDTO {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户拥有的银行卡数量
     */
    private Long bankCardCount;
    /**
     * 用户拥有的银行卡里面第一张开户的编号
     */
    private String firstBankCardCode;
}
