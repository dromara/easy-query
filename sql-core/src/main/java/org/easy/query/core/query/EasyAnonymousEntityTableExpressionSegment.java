package org.easy.query.core.query;

import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;
import org.easy.query.core.util.LambdaUtil;

/**
 * @FileName: EasyAnonymousEntityTableExpressionSegment.java
 * @Description: 匿名实体表表达式
 * @Date: 2023/3/3 23:31
 * @Created by xuejiaming
 */
public class EasyAnonymousEntityTableExpressionSegment extends EasyEntityTableExpressionSegment {
    private final SqlExpressionSegment sqlExpressionSegment;

    public EasyAnonymousEntityTableExpressionSegment(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType, SqlExpressionSegment sqlExpressionSegment) {
        super(entityMetadata, index, alias, multiTableType);
        this.sqlExpressionSegment = sqlExpressionSegment;
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

        sql.append(getSelectTableSource()).append("(").append(sqlExpressionSegment.toSql()).append(")");
        if (alias != null) {
            sql.append(" ").append(alias);
        }
        return sql.toString();
    }

}
