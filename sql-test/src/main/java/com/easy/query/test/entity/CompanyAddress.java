package com.easy.query.test.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * create time 2023/11/3 08:37
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EqualsAndHashCode
public class CompanyAddress {
    private String province;
    private String city;
    private String area;
}
