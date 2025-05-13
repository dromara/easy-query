package com.easy.query.test.vo;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/5/11 22:46
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@FieldNameConstants
public class GenericDTO {
    private String value1;
    private String value2;
}
