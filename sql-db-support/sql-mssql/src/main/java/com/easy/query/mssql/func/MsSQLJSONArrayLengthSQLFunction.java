package com.easy.query.mssql.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2023/10/11 22:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLJSONArrayLengthSQLFunction extends AbstractExpressionSQLFunction {

    private final List<ColumnExpression> columnExpressions;

    public MsSQLJSONArrayLengthSQLFunction(List<ColumnExpression> concatExpressions) {
        if (EasyCollectionUtil.isEmpty(concatExpressions)) {
            throw new IllegalArgumentException("MsSQLJSONArrayLengthSQLFunction columns empty");
        }
        this.columnExpressions = concatExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()!=1){
            throw new IllegalArgumentException("MsSQLJSONArrayLengthSQLFunction columns size != 1");
        }
        return "(SELECT COUNT(*) FROM OPENJSON({0}))";
    }

    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }


    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }
}
