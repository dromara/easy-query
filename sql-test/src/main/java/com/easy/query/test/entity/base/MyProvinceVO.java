package com.easy.query.test.entity.base;

import com.easy.query.core.annotation.ColumnIgnore;
import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * create time 2024/5/30 17:37
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@FieldNameConstants
public class MyProvinceVO {
    private String myCode;
    private Long count;

    @ColumnIgnore
    private List<City> cities;
//
//    @Navigate(RelationTypeEnum.OneToMany)
//    private List<BlogEntity> blogs;
}
