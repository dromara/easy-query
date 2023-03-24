package com.easy.query.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.LogicDelete;
import com.easy.query.core.basic.enums.LogicDeleteStrategyEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @FileName: BaseEntity.java
 * @Description: 文件说明
 * @Date: 2023/3/16 17:23
 * @author xuejiaming
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -4834048418175625051L;

    @Column(primaryKey = true)
    private String id;
    /**
     * 创建时间;创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间;修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建人;创建人
     */
    private String createBy;
    /**
     * 修改人;修改人
     */
    private String updateBy;
    /**
     * 是否删除;是否删除
     */
    @LogicDelete(strategy = LogicDeleteStrategyEnum.BOOLEAN)
    private Boolean deleted;
}
