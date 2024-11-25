package com.easy.query.test.entity.relation;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import lombok.Data;

import java.util.List;

/**
 * create time 2024/3/1 17:04
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_user")
@EntityProxy
@Data
public class MyRelationUser implements ProxyEntityAvailable<MyRelationUser, com.easy.query.test.entity.relation.proxy.MyRelationUserProxy> {
    @Column(primaryKey = true)
    private String id;

    private String name;

    /**
     * book type=1
     */
    @Navigate(value = RelationTypeEnum.OneToMany,targetProperty ="userId", extraFilter = BookNavigateExtraFilterStrategy.class)
    private List<RelationBook> books;
    /**
     * 时间2022年以前的
     */
    @Navigate(value = RelationTypeEnum.OneToMany,targetProperty ="userId", extraFilter = BookNavigateExtraFilterStrategy.class)
    private List<RelationBook> historyBooks;

    @Navigate(value = RelationTypeEnum.ManyToMany,
            mappingClass = RelationRoute.class
            ,selfMappingProperty = "firstId"
            ,targetMappingProperty = "secondId",
    extraFilter = BookNavigateExtraFilterStrategy.class)
    private List<RelationTeacher> teachers;

}
