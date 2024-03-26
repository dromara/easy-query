package com.easy.query.core.basic.api.flat.extension;

import com.easy.query.core.basic.api.flat.MapQueryable;
import com.easy.query.core.basic.api.flat.provider.MapGroupSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * create time 2024/3/26 16:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Groupable {

    MapQueryable groupBy(SQLExpression1<MapGroupSelector> selectExpression);
}
