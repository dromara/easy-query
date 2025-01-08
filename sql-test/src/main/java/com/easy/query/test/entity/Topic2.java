package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.InsertIgnore;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.Topic2Proxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xuejiaming
 * @FileName: Topic.java
 * @Description: 文件说明
 * @Date: 2023/3/16 21:26
 */
@Data
@Table("t_topic")
@EntityProxy //添加这个注解插件会在当前目录下面生成一个proxy的包,生成代理对象
@EasyAssertMessage("未找到主题信息")
public class Topic2 implements ProxyEntityAvailable<Topic2, Topic2Proxy> {

    @Column(primaryKey = true)
    private String id;
    @Column("abc")
    private String title;
    @Column(defaultUse = false)
    private String title1;
}
