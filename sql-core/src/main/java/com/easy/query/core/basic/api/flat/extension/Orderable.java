package com.easy.query.core.basic.api.flat.extension;

import com.easy.query.core.basic.api.flat.MapQueryable;
import com.easy.query.core.basic.api.flat.provider.MapOrderBy;
import com.easy.query.core.expression.lambda.SQLActionExpression1;

/**
 * create time 2024/3/26 17:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Orderable {
    MapQueryable orderBy(SQLActionExpression1<MapOrderBy> orderByExpression, boolean asc);
}
