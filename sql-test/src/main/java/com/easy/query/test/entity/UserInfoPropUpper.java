package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.UserInfoPropUpperProxy;
import lombok.Data;

import java.util.Date;

/**
 * create time 2024/5/20 14:24
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_user")
@EntityProxy //添加这个注解插件会在当前目录下面生成一个proxy的包,生成代理对象
@EasyAssertMessage("未找到主题信息")
public class UserInfoPropUpper implements ProxyEntityAvailable<UserInfoPropUpper , UserInfoPropUpperProxy> {
    @Column(primaryKey = true)
    private String ID;
    private Date LevelDate;
}
