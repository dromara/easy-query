package com.easy.query.test.mysql8.dto;

import com.easy.query.core.annotation.EasyWhereCondition;
import lombok.Data;

import java.util.List;

/**
 * create time 2025/8/10 21:18
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class SysUserQueryDTO {

    @EasyWhereCondition(propName = "bankCards.code")
    private String bankCardCode;
    @EasyWhereCondition(propName = "bankCards.type")
    private String bankCardType;
    @EasyWhereCondition(propName = "bankCards.bank.name", type = EasyWhereCondition.Condition.IN)
    private List<String> bankCardBankNames;
}
