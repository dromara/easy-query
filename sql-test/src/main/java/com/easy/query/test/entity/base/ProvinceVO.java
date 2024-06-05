package com.easy.query.test.entity.base;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2024/5/30 17:37
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@FieldNameConstants
public class ProvinceVO {
    private String myCode;
    private String myName;
//
//    @Navigate(RelationTypeEnum.OneToMany)
//    private List<BlogEntity> blogs;
}
