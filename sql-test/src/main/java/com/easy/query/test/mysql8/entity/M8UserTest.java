package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8UserTestProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2026/5/14 08:16
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("m8_user_test")
@EntityProxy
@FieldNameConstants
public class M8UserTest implements ProxyEntityAvailable<M8UserTest , M8UserTestProxy> {
    private Long id;
    private Integer sex;
    private Integer age;

    private String userName;
}
