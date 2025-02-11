package com.easy.query.test.vo;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * create time 2025/2/9 14:57
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class GroupVO {
    private String key;
    private Long idCount;
    private LocalDateTime createTimeMax;
    private BigDecimal scoreSum;
}
