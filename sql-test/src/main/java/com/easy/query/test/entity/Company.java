package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ValueObject;
import com.easy.query.core.annotation.Table;
import lombok.Data;

/**
 * create time 2023/11/3 08:37
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_company")
@Data
public class Company {
    @Column(primaryKey = true)
    private String id;
    private String name;
    @ValueObject
    private CompanyAddress address;
}
