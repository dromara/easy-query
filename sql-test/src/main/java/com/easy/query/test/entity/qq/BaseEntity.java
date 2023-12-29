package com.easy.query.test.entity.qq;

import com.easy.query.core.annotation.LogicDelete;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * create time 2023/12/29 16:00
 * 文件说明
 *
 * @author xuejiaming
 */

@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1L;
    /**
     * 租户信息
     */
    @UpdateIgnore
    private Long companyId;
    /**
     * 创建人
     */
    @UpdateIgnore
    private Long createdId;
    /**
     * 创建人名字
     */
    @UpdateIgnore //创建时间字段不需要update时更新
    private String createdName;
    /**
     * 创建时间
     */
    @UpdateIgnore //创建时间字段不需要update时更新
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
    /**
     * 更新人id
     */
    @UpdateIgnore
    private Long updatedId;
    /**
     * 更新人名称
     */
    private String updatedName;
    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;
    /**
     * 删除标识(0=正常，1=删除)
     */
    @LogicDelete(strategy = LogicDeleteStrategyEnum.BOOLEAN)
    @UpdateIgnore//逻辑删除字段不需要update时更新
    private Boolean isDeleted;

}
