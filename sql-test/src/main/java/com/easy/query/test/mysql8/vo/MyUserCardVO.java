package com.easy.query.test.mysql8.vo;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

/**
 * create time 2025/4/15 17:03
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class MyUserCardVO {
    /**
     * 银行卡号
     */
    private String code;
    /**
     * 银行卡类型借记卡 储蓄卡
     */
    private String type;
}
