package com.easy.query.core.basic.api.update.map;

import com.easy.query.core.expression.sql.builder.MapUpdateExpressionBuilder;

import java.util.Collection;
import java.util.Map;

/**
 * create time 2023/10/3 17:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyMapClientUpdatable extends AbstractMapClientUpdatable{
    public EasyMapClientUpdatable(Collection<Map<String, Object>> entities, MapUpdateExpressionBuilder mapUpdateExpressionBuilder) {
        super(entities, mapUpdateExpressionBuilder);
    }
}
