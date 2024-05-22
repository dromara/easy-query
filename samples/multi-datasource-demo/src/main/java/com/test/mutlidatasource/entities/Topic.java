package com.test.mutlidatasource.entities;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.test.mutlidatasource.entities.proxy.TopicProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2024/3/13 15:59
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_topic")
@EntityProxy
public class Topic implements ProxyEntityAvailable<Topic , TopicProxy> {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;

}
