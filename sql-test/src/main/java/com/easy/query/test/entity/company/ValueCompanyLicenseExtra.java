package com.easy.query.test.entity.company;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * create time 2023/11/7 14:07
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EqualsAndHashCode
@ToString
public class ValueCompanyLicenseExtra {
    /**
     * 企业营业执照图片
     */
    private String licenseImage;
    /**
     * 企业营业执照经营内容
     */
    private String licenseContent;
}
