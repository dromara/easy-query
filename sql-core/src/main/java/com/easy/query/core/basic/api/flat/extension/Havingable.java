package com.easy.query.core.basic.api.flat.extension;

import com.easy.query.core.basic.api.flat.MapQueryable;
import com.easy.query.core.basic.api.flat.provider.MapHaving;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * create time 2024/3/26 17:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Havingable {
    MapQueryable having(SQLExpression1<MapHaving> havingExpression);
}
