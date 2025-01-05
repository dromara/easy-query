package com.easy.query.test.doc.dto;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/1/4 14:14
 * {@link com.easy.query.test.doc.entity.DocBankCard}
 *
 * @author xuejiaming
 */
@Data
@FieldNameConstants
@EntityProxy
@SuppressWarnings("EasyQueryFieldMissMatch")
public class BankCardVO {

    private String id;
    private String uid;
    /**
     * 银行卡号
     */
    private String code;
    /**
     * 银行卡类型借记卡 储蓄卡
     */
    private String type;

    private String userName;
    private String bankName;
}
