package com.easy.query.test.kingbase;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.UpdateIgnore;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * create time 2024/1/2 09:32
 * 文件说明
 *
 * @author xuejiaming
 */

@Data
@FieldNameConstants
public abstract class BaseEntity  {
    private static final long serialVersionUID = -4834048418175625051L;
    /**
     * 记录标识;记录标识
     */
    @Column(primaryKey = true)
    private String id;
    /**
     * 创建时间;创建时间
     */
    @UpdateIgnore
    private LocalDateTime createTime;
    /**
     * 修改时间;修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建人;创建人
     */
    @UpdateIgnore
    private String createBy;
    /**
     * 修改人;修改人
     */
    private String updateBy;
    /**
     * 是否删除;是否删除
     */
    @UpdateIgnore
    private Boolean deleted;

    /**
     * 删除人
     */
    @Length(max = 32, message = "删除人过长")
    @UpdateIgnore
    private String deleteBy;

    /**
     * 删除时间
     */
    @UpdateIgnore
    private LocalDateTime deleteTime;

}

