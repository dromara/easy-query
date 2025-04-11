package com.easy.query.test.mysql8.vo;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import lombok.Data;

/**
 * create time 2025/4/11 21:29
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class BankCardVO {
    private SysBankCard bankCard;
    private String bankName;
}
