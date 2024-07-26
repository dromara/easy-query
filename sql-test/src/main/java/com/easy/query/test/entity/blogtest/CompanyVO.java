package com.easy.query.test.entity.blogtest;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * create time 2024/7/24 08:02
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class CompanyVO {
    private String id;
    private String name;
    private LocalDateTime createTime;

    private Integer useTotalAge;
}
