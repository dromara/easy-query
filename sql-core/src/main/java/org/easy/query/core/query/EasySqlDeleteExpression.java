package org.easy.query.core.query;

import org.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * @FileName: EasySqlDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:32
 * @Created by xuejiaming
 */
public abstract class EasySqlDeleteExpression extends AbstractSqlEntityExpression implements SqlEntityDeleteExpression {
    protected final PredicateSegment where;
    protected SqlBuilderSegment whereColumns;

    public EasySqlDeleteExpression(SqlExpressionContext sqlExpressionContext) {
        super(sqlExpressionContext);
        this.where = new AndPredicateSegment(true);
    }

    @Override
    public PredicateSegment getWhere() {
        return where;
    }

    @Override
    public boolean hasWhere() {
        return where.isNotEmpty();
    }

    @Override
    public SqlBuilderSegment getWhereColumns() {
        if (whereColumns == null) {
            whereColumns = new ProjectSqlBuilderSegment();
        }
        return whereColumns;
    }

    @Override
    public boolean hasWhereColumns() {
        return whereColumns != null && whereColumns.isNotEmpty();
    }

}
