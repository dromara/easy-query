package com.easy.query.test.entity.base;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ColumnIgnore;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.base.proxy.ProvinceProxy;
import com.easy.query.test.entity.base.proxy.ValidProvinceProxy;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import java.util.List;

/**
 * create time 2026/11/26 11:03
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_province2")
@Data
@ToString
@EntityProxy
public class ValidProvince implements ProxyEntityAvailable<ValidProvince , ValidProvinceProxy> {
    @Column(primaryKey = true)
    private String code;
    private String name;
    @Navigate(value = RelationTypeEnum.OneToMany,targetProperty = "provinceCode")
    private List<@Valid City> cities;

    @ColumnIgnore
    private String firstCityName;
}