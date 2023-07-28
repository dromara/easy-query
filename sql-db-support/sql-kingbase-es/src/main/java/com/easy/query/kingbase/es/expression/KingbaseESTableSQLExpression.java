package com.easy.query.kingbase.es.expression;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.expression.impl.TableSQLExpressionImpl;

/**
 * create time 2023/7/28 21:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class KingbaseESTableSQLExpression extends TableSQLExpressionImpl {

    public KingbaseESTableSQLExpression(TableAvailable entityTable, MultiTableTypeEnum multiTableType, QueryRuntimeContext runtimeContext) {
        super(entityTable, multiTableType, runtimeContext);
    }
}
