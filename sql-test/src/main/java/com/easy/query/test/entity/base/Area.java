package com.easy.query.test.entity.base;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2023/7/17 21:27
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_area")
@Data
@ToString
public class Area {
    @Column(primaryKey = true)
    private String code;
    private String provinceCode;
    private String cityCode;
    private String name;
}
