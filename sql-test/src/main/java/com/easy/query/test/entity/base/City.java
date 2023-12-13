package com.easy.query.test.entity.base;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ColumnIgnore;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.base.proxy.CityProxy;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * create time 2023/7/17 21:27
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_city")
@Data
@ToString
@EntityFileProxy
public class City implements ProxyEntityAvailable<City , CityProxy> {
    @Column(primaryKey = true)
    private String code;
    private String provinceCode;
    private String name;
    @Navigate(value = RelationTypeEnum.OneToMany,targetProperty = "cityCode")
    private List<Area> areas;

    @ColumnIgnore
    private Province province;

    @Override
    public Class<CityProxy> proxyTableClass() {
        return CityProxy.class;
    }
}
