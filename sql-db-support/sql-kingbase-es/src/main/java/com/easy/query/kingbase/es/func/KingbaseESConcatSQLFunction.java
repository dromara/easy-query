package com.easy.query.kingbase.es.func;

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
public class KingbaseESConcatSQLFunction extends AbstractExpressionSQLFunction {

    private final List<ColumnExpression> columnExpressions;

    public KingbaseESConcatSQLFunction(List<ColumnExpression> concatExpressions) {
        if (EasyCollectionUtil.isEmpty(concatExpressions)) {
            throw new IllegalArgumentException("KingbaseESConcatSQLFunction columns empty");
        }
        this.columnExpressions = concatExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        Iterable<String> params = EasyCollectionUtil.select(columnExpressions, (t, i) ->i==0?"{" + i + "}::TEXT": "{" + i + "}");
//        return String.format("%s", String.join(" || ", params));
        return String.format("CONCAT(%s)", String.join(",", params));
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
