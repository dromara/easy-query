package com.easy.query.test.entity.testrelation.vo;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * create time 2024/3/8 16:36
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class TestRoleDTO1 {
    /**
     * 主键ID
     */
    @Column(primaryKey = true)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;


    @Navigate(value = RelationTypeEnum.ManyToMany)
    private List<TestRouteDTO1> routes;
}
