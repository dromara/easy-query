package org.easy.query.core.query;

import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

/**
 * @FileName: EasyAnonymousEntityTableExpressionSegment.java
 * @Description: 匿名实体表表达式
 * @Date: 2023/3/3 23:31
 * @Created by xuejiaming
 */
public class EasyAnonymousEntityTableExpression extends EasyEntityTableExpression implements AnonymousEntityTableExpression {
    private final SqlEntityQueryExpression sqlEntityExpression;

    public EasyAnonymousEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType, SqlEntityQueryExpression sqlEntityExpression) {
        super(entityMetadata, index, alias, multiTableType);
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public SqlExpression<SqlPredicate<?>> getQueryFilterExpression() {
        return null;
    }

    @Override
    public SqlExpression<SqlColumnSetter<?>> getDeletedSqlExpression() {
        return null;
    }

    @Override
    public String toSql() {
        StringBuilder sql = new StringBuilder();

        sql.append(getSelectTableSource()).append("(").append(sqlEntityExpression.toSql()).append(")");
        if (alias != null) {
            sql.append(" ").append(alias);
        }
        return sql.toString();
    }

    @Override
    public SqlEntityQueryExpression getSqlEntityQueryExpression() {
        return sqlEntityExpression;
    }

    @Override
    public String getColumnName(String propertyName) {
        return propertyName;
    }
}
