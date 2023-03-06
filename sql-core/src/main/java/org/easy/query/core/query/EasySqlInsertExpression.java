package org.easy.query.core.query;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;
import org.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: EasySqlInsertExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:49
 * @Created by xuejiaming
 */
public abstract class EasySqlInsertExpression extends AbstractSqlEntityExpression implements SqlEntityInsertExpression{
    private final SqlBuilderSegment columns;
    public EasySqlInsertExpression(SqlExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
        this.columns=new ProjectSqlBuilderSegment();
    }

    @Override
    public SqlBuilderSegment getColumns() {
        return columns;
    }
}
