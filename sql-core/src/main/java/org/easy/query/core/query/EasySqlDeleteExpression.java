package org.easy.query.core.query;

import jdk.nashorn.internal.objects.annotations.Where;
import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;
import org.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: EasySqlDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:32
 * @Created by xuejiaming
 */
public abstract class EasySqlDeleteExpression extends AbstractSqlEntityExpression implements SqlEntityDeleteExpression{
    protected final PredicateSegment where;
    protected SqlBuilderSegment whereColumns;
    public EasySqlDeleteExpression(SqlExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
        this.where=new AndPredicateSegment(true);
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
        if(whereColumns==null){
            whereColumns=new ProjectSqlBuilderSegment();
        }
        return whereColumns;
    }

    @Override
    public boolean hasWhereColumns() {
        return whereColumns!=null&&whereColumns.isNotEmpty();
    }
}
