package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyColumnSegmentUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.function.Function;

/**
 * create time 2023/7/29 19:02
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ColumnPropertyExpressionImpl implements ColumnPropertyParamExpression {
    private final TableAvailable table;
    private final String property;
    private final ExpressionContext expressionContext;
    private final Function<ToSQLContext, String> toSQLFunction;

    public ColumnPropertyExpressionImpl(TableAvailable table, String property, ExpressionContext expressionContext) {
        this.table = table;
        this.property = property;
        this.expressionContext = expressionContext;
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
        if (columnValueSQLConverter == null) {
            toSQLFunction = toSQLContext -> EasySQLExpressionUtil.getSQLOwnerColumn(expressionContext.getRuntimeContext(), table, columnMetadata.getName(), toSQLContext);
        } else {
            if (columnValueSQLConverter.isRealColumn()) {
                toSQLFunction = toSQLContext -> EasySQLExpressionUtil.getSQLOwnerColumn(expressionContext.getRuntimeContext(), table, columnMetadata.getName(), toSQLContext);
            } else {

                Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext, false, true);
                toSQLFunction = toSQLContext -> column2Segment.toSQL(toSQLContext);
            }
        }
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return toSQLFunction.apply(toSQLContext);
//        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
//        if(columnValueSQLConverter==null){
//            return EasySQLExpressionUtil.getSQLOwnerColumn(runtimeContext, table, columnMetadata.getName(), toSQLContext);
//        }
//        return new ColumnSegmentImpl(table,columnMetadata,runtimeContext).toSQL(toSQLContext);
//        return EasySQLExpressionUtil.getSQLOwnerColumnMetadata(expressionContext, table, columnMetadata, toSQLContext,true,false);
    }
}
