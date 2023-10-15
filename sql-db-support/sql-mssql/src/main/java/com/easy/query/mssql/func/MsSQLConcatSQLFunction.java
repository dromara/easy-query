package com.easy.query.mssql.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2023/10/11 22:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLConcatSQLFunction extends AbstractExpressionSQLFunction {

    private final List<ColumnExpression> columnExpressions;

    public MsSQLConcatSQLFunction(List<ColumnExpression> concatExpressions) {
        if (EasyCollectionUtil.isEmpty(concatExpressions)) {
            throw new IllegalArgumentException("MsSQLConcatSQLFunction columns empty");
        }
        this.columnExpressions = concatExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return getSQLSegment(defaultTable);
    }

    protected String getSQLSegment(TableAvailable defaultTable) {
        int i = 0;
        String[] params = new String[columnExpressions.size()];
        for (ColumnExpression columnExpression : columnExpressions) {
            if (columnExpression instanceof ColumnFuncExpression) {
                ColumnFuncExpression columnFuncExpression = (ColumnFuncExpression) columnExpression;
                TableAvailable table = getTableByExpression(defaultTable,columnFuncExpression);
                ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(columnFuncExpression.getProperty());
                Class<?> propertyType = columnMetadata.getPropertyType();
                if (String.class == propertyType) {
                    params[i]=String.format("{%s}",i);
                } else if (EasyClassUtil.isBasicType(propertyType)) {
                    params[i]=String.format("CAST({%s} AS VARCHAR)", i);
                }else {
                    params[i]=String.format("CAST({%s} AS NVARCHAR(MAX))", i);
                }
            } else {
                params[i]=String.format("{%s}",i);
            }
            i++;
        }
        return String.join(" + ",params);
    }


    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {
        invokeExpression(context);
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }
}
