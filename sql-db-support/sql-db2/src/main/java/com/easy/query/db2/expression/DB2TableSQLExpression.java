package com.easy.query.db2.expression;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.expression.impl.TableSQLExpressionImpl;

/**
 * create time 2023/7/27 18:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class DB2TableSQLExpression extends TableSQLExpressionImpl {
    public DB2TableSQLExpression(TableAvailable entityTable, MultiTableTypeEnum multiTableType, QueryRuntimeContext runtimeContext) {
        super(entityTable, multiTableType, runtimeContext);
    }
}
