package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.InsertIgnore;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.TopicProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

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
@EasyAlias("t_topic")
@FieldNameConstants
public class Topic implements ProxyEntityAvailable<Topic, TopicProxy> {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;
//    @Column(value = "C_USER_NAME")
//    private String cUserName;

    @Column(exist = false)
    private String alias;

    @Navigate(value = RelationTypeEnum.OneToMany, targetProperty = "stars")
    private List<Topic> children;
}
