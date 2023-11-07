package com.easy.query.test.entity.company;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * create time 2023/11/7 14:06
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EqualsAndHashCode
@ToString
public class ValueCompanyAddress {
    /**
     * 企业所属省份
     */
    private String province;
    /**
     * 企业所属市区
     */
    private String city;
    /**
     * 企业所属区域
     */
    private String area;
}
