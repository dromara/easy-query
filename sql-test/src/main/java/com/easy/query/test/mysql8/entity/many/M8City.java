package com.easy.query.test.mysql8.entity.many;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.many.proxy.M8CityProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * create time 2025/7/17 22:22
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_city")
@FieldNameConstants
public class M8City implements ProxyEntityAvailable<M8City , M8CityProxy> {
    @Column(primaryKey = true)
    private String id;

    private String pid;

    private String name;

    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {M8City.Fields.id}, targetProperty = {M8Area.Fields.cid})
    private List<M8Area> areas;

    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {M8City.Fields.id}, targetProperty = {M8Area.Fields.cid})
    private List<M8Area> areas2;

}
