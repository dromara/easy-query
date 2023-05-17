package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.expression.EasyAnonymousTableSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.SqlExpressionUtil;

import java.util.Iterator;
import java.util.List;

/**
 * create time 2023/4/23 16:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousTableSqlExpression extends TableSqlExpression implements EasyAnonymousTableSqlExpression {
    private final EasyQuerySqlExpression easyQuerySqlExpression;

    public AnonymousTableSqlExpression(TableAvailable entityTable, MultiTableTypeEnum multiTableType, EasyQuerySqlExpression easyQuerySqlExpression, EasyQueryRuntimeContext runtimeContext) {
        super(entityTable, multiTableType, runtimeContext);
        this.easyQuerySqlExpression = easyQuerySqlExpression;
    }


    @Override
    public String getTableName() {
        if (tableNameAs != null) {
            return tableNameAs.apply(getAlias());
        }
        return getAlias();
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        SqlExpressionUtil.expressionInvokeRoot(sqlParameterCollector);
//        if (EasyCollectionUtil.isSingle(easyQuerySqlExpressions)) {
//            return toSingleSql(sqlParameterCollector);
//        } else {
//            return toMultiSql(sqlParameterCollector);
//        }

        StringBuilder sql = new StringBuilder();

        sql.append(getSelectTableSource()).append("(").append(easyQuerySqlExpression.toSql(sqlParameterCollector)).append(")");
        String tableName = getTableName();
        if (tableName != null) {
            sql.append(" ").append(tableName);
        }
        return sql.toString();
    }

//    private String toSingleSql(SqlParameterCollector sqlParameterCollector) {
//        EasyQuerySqlExpression first = EasyCollectionUtil.first(easyQuerySqlExpressions);
//        StringBuilder sql = new StringBuilder();
//
//        sql.append(getSelectTableSource()).append("(").append(first.toSql(sqlParameterCollector)).append(")");
//        String tableName = getTableName();
//        if (tableName != null) {
//            sql.append(" ").append(tableName);
//        }
//        return sql.toString();
//    }
//
//    private String toMultiSql(SqlParameterCollector sqlParameterCollector) {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append(getSelectTableSource()).append("( ");
//        Iterator<EasyQuerySqlExpression> iterator = easyQuerySqlExpressions.iterator();
//        EasyQuerySqlExpression first = iterator.next();
//        sql.append(first.toSql(sqlParameterCollector));
//        while (iterator.hasNext()) {
//            EasyQuerySqlExpression easyQuerySqlExpression = iterator.next();
//            sql.append(" UNION ALL ");
//            sql.append(easyQuerySqlExpression.toSql(sqlParameterCollector));
//        }
//        sql.append(")");
//        String tableName = getTableName();
//        if (tableName != null) {
//            sql.append(" ").append(tableName);
//        }
//        return sql.toString();
//    }
}
