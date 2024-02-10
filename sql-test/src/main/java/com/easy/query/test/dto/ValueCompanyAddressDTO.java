package com.easy.query.test.dto;

import lombok.Data;

/**
 * create time 2024/2/9 10:43
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class ValueCompanyAddressDTO {
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
