package com.easyquery.springbootdemo.domain;

import com.easy.query.cache.core.CacheMultiEntity;
import com.easy.query.cache.core.annotation.CacheEntitySchema;
import com.easy.query.cache.core.annotation.CacheMultiEntitySchema;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easyquery.springbootdemo.domain.proxy.HelpAreaEntityProxy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 实体类。
 *
 * @author xjm
 * @since 1.0
 */
@Setter
@Getter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "help_area")
@EntityFileProxy
@CacheEntitySchema
@CacheMultiEntitySchema(value = "cityId")
public class HelpAreaEntity extends BaseEntity implements CacheMultiEntity,ProxyEntityAvailable<HelpAreaEntity , HelpAreaEntityProxy> {

    private String name;

    private String provinceId;

    private String provinceName;

    private String cityId;

    private String cityName;

    @Override
    public String cacheIndexId() {
        return getCityId();
    }

    @Override
    public String cacheIdValue() {
        return getId();
    }

}
