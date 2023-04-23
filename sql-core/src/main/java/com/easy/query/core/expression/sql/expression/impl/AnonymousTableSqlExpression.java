package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.sql.expression.EasyAnonymousTableSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/4/23 16:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousTableSqlExpression extends TableSqlExpression implements EasyAnonymousTableSqlExpression {
    private final EasyQuerySqlExpression easyQuerySqlExpression;

    public AnonymousTableSqlExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType, EasyQuerySqlExpression easyQuerySqlExpression) {
        super(entityMetadata, index, alias, multiTableType);
        this.easyQuerySqlExpression = easyQuerySqlExpression;
    }

    @Override
    public EasyQuerySqlExpression getEasyQuerySqlExpression() {
        return easyQuerySqlExpression;
    }

    @Override
    public String getTableName() {
        if(tableNameAs!=null){
            return tableNameAs.apply(alias);
        }
        return alias;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {

        StringBuilder sql = new StringBuilder();

        sql.append(getSelectTableSource()).append("(").append(easyQuerySqlExpression.toSql(sqlParameterCollector)).append(")");
        String tableName = getTableName();
        if (tableName != null) {
            sql.append(" ").append(tableName);
        }
        return sql.toString();
    }
}
