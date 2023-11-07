package com.easy.query.test.entity.company;

import com.easy.query.core.annotation.ValueObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * create time 2023/11/7 14:07
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EqualsAndHashCode
@ToString
public class ValueCompanyLicense {

    /**
     * 企业营业执照编号
     */
    private String licenseNo;
    /**
     * 企业营业执照到期时间
     */
    private LocalDateTime licenseDeadline;
    @ValueObject
    private ValueCompanyLicenseExtra extra;
}
