package com.easy.query.sqlite.func;

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
public class SQLiteJSONArrayByIndexSQLFunction extends AbstractExpressionSQLFunction {

    private final List<ColumnExpression> columnExpressions;

    public SQLiteJSONArrayByIndexSQLFunction(List<ColumnExpression> concatExpressions) {
        if (EasyCollectionUtil.isEmpty(concatExpressions)) {
            throw new IllegalArgumentException("SQLiteJSONArrayByIndexSQLFunction columns empty");
        }
        this.columnExpressions = concatExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()!=2){
            throw new IllegalArgumentException("SQLiteJSONArrayByIndexSQLFunction columns size != 2");
        }
        List<ColumnExpression> jsonKeyExpressions = columnExpressions.subList(1, columnExpressions.size());
        Iterable<String> params = EasyCollectionUtil.select(jsonKeyExpressions, (t, i) -> "{" + i+1 + "}");
        return String.format("JSON_EXTRACT({0},'’'$[%s]')", String.join(".", params));
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
