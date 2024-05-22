package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.InsertIgnore;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.conversion.FullNameColumnValueSQLConverter;
import com.easy.query.test.conversion.UserAgeColumnValueSQLConverter;
import com.easy.query.test.entity.proxy.UserExtraProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2024/3/28 21:17
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_user_extra")
@EntityProxy
public class UserExtra implements ProxyEntityAvailable<UserExtra , UserExtraProxy> {
    @Column(primaryKey = true)
    private String id;
    private String firstName;
    private String lastName;
    private LocalDateTime birthday;
    @InsertIgnore
    @UpdateIgnore
    @Column(sqlConversion = FullNameColumnValueSQLConverter.class)
    private String fullName;

    @InsertIgnore
    @UpdateIgnore
    @Column(sqlConversion = UserAgeColumnValueSQLConverter.class)
    private Integer age;
}
