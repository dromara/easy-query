package com.easy.query.test.dto;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2023/7/17 21:27
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@ToString
@EntityProxy
public class CityVO {
    private String code;
    private String provinceCode;
    private String name;
}
