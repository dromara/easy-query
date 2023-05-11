package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.LogicDelete;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.basic.enums.LogicDeleteStrategyEnum;
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
public class LogicDelTopic {
    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    @LogicDelete(strategy = LogicDeleteStrategyEnum.BOOLEAN)
    private Boolean deleted;
    private LocalDateTime createTime;
}
