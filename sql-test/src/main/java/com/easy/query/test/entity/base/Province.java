package com.easy.query.test.entity.base;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ColumnIgnore;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.base.proxy.ProvinceProxy;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * create time 2023/7/17 21:26
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_province")
@Data
@ToString
@EntityFileProxy
public class Province implements ProxyEntityAvailable<Province , ProvinceProxy> {
    @Column(primaryKey = true)
    private String code;
    private String name;
    @Navigate(value = RelationTypeEnum.OneToMany,targetProperty = "provinceCode")
    private List<City> cities;

    @ColumnIgnore
    private String firstCityName;
}
