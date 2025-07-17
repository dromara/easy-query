package com.easy.query.test.mysql8.entity.many;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.many.proxy.M8ProvinceProxy;
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
@Table("m8_province")
@FieldNameConstants
public class M8Province implements ProxyEntityAvailable<M8Province , M8ProvinceProxy> {
    @Column(primaryKey = true)
    private String id;

    private String name;

    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {M8Province.Fields.id}, targetProperty = {M8City.Fields.pid})
    private List<M8City> cities;
}
