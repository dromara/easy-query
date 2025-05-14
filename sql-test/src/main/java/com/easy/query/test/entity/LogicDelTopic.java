package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.LogicDelete;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.LogicDelTopicProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/3/28 08:29
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_logic_del_topic")
@EntityProxy
public class LogicDelTopic implements ProxyEntityAvailable<LogicDelTopic , LogicDelTopicProxy> {
    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    @LogicDelete(strategy = LogicDeleteStrategyEnum.BOOLEAN)
    private Boolean deleted;
    private LocalDateTime createTime;
}
