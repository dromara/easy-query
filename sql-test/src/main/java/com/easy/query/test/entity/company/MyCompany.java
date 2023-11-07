package com.easy.query.test.entity.company;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/11/7 14:04
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("my_company")
@Data
public class MyCompany {
    @Column(primaryKey = true)
    private String id;
    /**
     * 企业名称
     */
    private String name;
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
    /**
     * 企业营业执照编号
     */
    private String licenseNo;
    /**
     * 企业营业执照到期时间
     */
    private LocalDateTime licenseDeadline;
    /**
     * 企业营业执照图片
     */
    private String licenseImage;
    /**
     * 企业营业执照经营内容
     */
    private String licenseContent;
}