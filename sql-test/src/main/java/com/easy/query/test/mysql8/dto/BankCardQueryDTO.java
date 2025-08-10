package com.easy.query.test.mysql8.dto;

import com.easy.query.core.annotation.EasyWhereCondition;
import lombok.Data;

/**
 * create time 2025/8/10 21:07
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class BankCardQueryDTO {
    @EasyWhereCondition(propName = "bank.name")
    private String bankName;
}
