package com.easy.query.core.basic.api.cte;

import com.easy.query.core.util.EasyClassUtil;

/**
 * create time 2025/2/7 10:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultCteTableNamedProvider implements CteTableNamedProvider {
    @Override
    public String getDefaultCteTableName(Class<?> clazz) {
        return "with_"+ EasyClassUtil.getSimpleName(clazz);
    }
}
