package com.easy.query.test.mysql8.dto;

import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import lombok.Data;

/**
 * create time 2025/6/30 15:59
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class BankCardSelectAutoInclude {
    private String uid;
    private Long count;


    @Navigate(value = RelationTypeEnum.OneToOne)
    private InternalUser user;

    @Data
    public static class InternalUser{
        private String id;
        private String name;
        private String phone;
        private Integer age;

    }
}
