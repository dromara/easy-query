package com.easy.query.test.mysql8.dto;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import lombok.Data;

/**
 * create time 2025/6/29 20:22
 * 文件说明
 *
 * @author xuejiaming
 */
@EntityProxy
@Data
public class BankCardGroupBO {
    private String uid;
    private Long count;


    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {"uid"}, targetProperty = {"id"}, supportNonEntity = true)
    private SysUser user;
}
