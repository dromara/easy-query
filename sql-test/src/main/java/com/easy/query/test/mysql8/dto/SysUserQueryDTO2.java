package com.easy.query.test.mysql8.dto;

import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import lombok.Data;

import java.util.List;

/**
 * create time 2025/8/10 21:18
 * {@link SysUser}
 *
 * @author xuejiaming
 */
@Data
public class SysUserQueryDTO2 {

    @EasyWhereCondition(propName = "bankCards.code",type = EasyWhereCondition.Condition.STARTS_WITH)
    private String bankCardCode;
    @EasyWhereCondition(propName = "bankCards.bank.name", type = EasyWhereCondition.Condition.IN)
    private List<String> bankCardBankNames;
    @EasyWhereCondition(propName = "bankCards.type",type = EasyWhereCondition.Condition.CONTAINS)
    private String bankCardType;
    @EasyWhereCondition(propName = "bankCards.type",type = EasyWhereCondition.Condition.ENDS_WITH)
    private String bankCardType2;
    @EasyWhereCondition(propName = "bankCards.type",type = EasyWhereCondition.Condition.CONTAINS)
    private String bankCardType3;
}
