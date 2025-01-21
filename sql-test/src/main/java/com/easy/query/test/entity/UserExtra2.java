package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ColumnSQLExpression;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.ExpressionArg;
import com.easy.query.core.annotation.InsertIgnore;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.conversion.FullNameColumnValueSQLConverter;
import com.easy.query.test.conversion.UserAgeColumnValueSQLConverter;
import com.easy.query.test.entity.proxy.UserExtra2Proxy;
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
public class UserExtra2 implements ProxyEntityAvailable<UserExtra2, UserExtra2Proxy> {
    @Column(primaryKey = true)
    private String id;
    private String firstName;
    private String lastName;
    private LocalDateTime birthday;
    @InsertIgnore
    @UpdateIgnore
    @Column(sqlExpression = @ColumnSQLExpression(sql="CONCAT({0},{1})",args = {
            @ExpressionArg(prop = "firstName"),
            @ExpressionArg(prop = "lastName"),
    }))
    private String fullName;

    @InsertIgnore
    @UpdateIgnore
    @Column(sqlConversion = UserAgeColumnValueSQLConverter.class)
    private Integer age;
}
