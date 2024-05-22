package com.easyquery.springbootdemo.domain;

import com.easy.query.cache.core.CacheAllEntity;
import com.easy.query.cache.core.annotation.CacheEntitySchema;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easyquery.springbootdemo.domain.proxy.HelpProvinceEntityProxy;
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
@Table(value = "help_province")
@EntityFileProxy
@CacheEntitySchema
public class HelpProvinceEntity extends BaseEntity implements CacheAllEntity, ProxyEntityAvailable<HelpProvinceEntity , HelpProvinceEntityProxy> {

    private String name;


    @Override
    public String cacheIdValue() {
        return getId();
    }
}
