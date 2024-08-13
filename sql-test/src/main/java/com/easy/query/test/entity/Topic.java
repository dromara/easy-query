package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.InsertIgnore;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.TopicProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @FileName: Topic.java
 * @Description: 文件说明
 * @Date: 2023/3/16 21:26
 * @author xuejiaming
 */
@Data
@Table("t_topic")
@EntityProxy //添加这个注解插件会在当前目录下面生成一个proxy的包,生成代理对象
@EasyAssertMessage("未找到主题信息")
public class Topic implements ProxyEntityAvailable<Topic , TopicProxy> {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;
//    @Column(value = "C_USER_NAME")
//    private String cUserName;

    @Column(autoSelect = false)
    @InsertIgnore
    @UpdateIgnore
    private String alias;
}
