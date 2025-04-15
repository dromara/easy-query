package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2023/8/7 11:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertUpdateColumnConfigureSegment2Impl extends AbstractSQLNativeSegment2Impl implements InsertUpdateSetColumnSQLSegment {

    private final InsertUpdateSetColumnSQLSegment insertUpdateSetColumnSQLSegment;

    public InsertUpdateColumnConfigureSegment2Impl(InsertUpdateSetColumnSQLSegment insertUpdateSetColumnSQLSegment, ExpressionContext expressionContext, SQLSegment sqlSegment, SQLNativeExpression sqlNativeExpression) {
        super(expressionContext, sqlSegment,s->s, sqlNativeExpression);
        this.insertUpdateSetColumnSQLSegment = insertUpdateSetColumnSQLSegment;
    }

    @Override
    public TableAvailable getTable() {
        return insertUpdateSetColumnSQLSegment.getTable();
    }

    @Override
    public String getPropertyName() {
        return insertUpdateSetColumnSQLSegment.getPropertyName();
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        return insertUpdateSetColumnSQLSegment.getColumnNameWithOwner(toSQLContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new InsertUpdateColumnConfigureSegment2Impl(insertUpdateSetColumnSQLSegment,expressionContext,sqlSegment, sqlNativeExpression);
    }
}
