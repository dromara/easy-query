package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/7/29 19:02
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ColumnPropertyExpressionImpl implements ColumnPropertyParamExpression {
    private final TableAvailable table;
    private final String property;

    public ColumnPropertyExpressionImpl(TableAvailable table, String property) {
        this.table = table;
        this.property = property;
    }

    @Override
    public String toSQL(ExpressionContext expressionContext, ToSQLContext toSQLContext) {

        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
        if(columnMetadata.isRealColumn()){
            return EasySQLExpressionUtil.getSQLOwnerColumn(expressionContext.getRuntimeContext(), table, columnMetadata.getName(), toSQLContext);
        }
//        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
//        if(columnValueSQLConverter==null){
//            return EasySQLExpressionUtil.getSQLOwnerColumn(runtimeContext, table, columnMetadata.getName(), toSQLContext);
//        }
//        return new ColumnSegmentImpl(table,columnMetadata,runtimeContext).toSQL(toSQLContext);
        return EasySQLExpressionUtil.getSQLOwnerColumnMetadata(expressionContext, table, columnMetadata, toSQLContext,true,false);
    }
}
