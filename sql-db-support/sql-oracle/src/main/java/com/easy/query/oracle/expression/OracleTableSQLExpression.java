package com.easy.query.oracle.expression;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.expression.impl.TableSQLExpressionImpl;

/**
 * create time 2023/8/15 17:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleTableSQLExpression extends TableSQLExpressionImpl {
    public OracleTableSQLExpression(TableAvailable entityTable, MultiTableTypeEnum multiTableType, QueryRuntimeContext runtimeContext) {
        super(entityTable, multiTableType, runtimeContext);
    }
}
