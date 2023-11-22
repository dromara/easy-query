package com.easy.query.test.doc.dto;

import com.easy.query.core.annotation.EasyWhereCondition;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/11/21 21:46
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class SysUserQueryRequest {
    @EasyWhereCondition
    private String name;
    @EasyWhereCondition
    private String account;
    @EasyWhereCondition
    private String departName;
    @EasyWhereCondition
    private String phone;
    @EasyWhereCondition(type = EasyWhereCondition.Condition.RANGE_LEFT_CLOSED,propName = "createTime" )
    private LocalDateTime createTimeBegin;
    @EasyWhereCondition(type = EasyWhereCondition.Condition.RANGE_RIGHT_CLOSED,propName = "createTime" )
    private LocalDateTime createTimeEnd;
}
