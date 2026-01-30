package com.easy.query.core.basic.extension.logicdel;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.metadata.LogicDeleteMetadata;

/**
 * create time 2026/1/30 20:18
 * 文件说明
 *
 * @author xuejiaming
 */
public interface LogicDeleteMetadataBuilder {
    default LogicDeleteMetadata build(SQLActionExpression1<WherePredicate<Object>> sqlWherePredicateSQLExpression, SQLActionExpression1<ColumnSetter<Object>> sqlColumnSetterSQLExpression) {
        return new LogicDeleteMetadata(sqlWherePredicateSQLExpression, sqlColumnSetterSQLExpression);
    }
}
