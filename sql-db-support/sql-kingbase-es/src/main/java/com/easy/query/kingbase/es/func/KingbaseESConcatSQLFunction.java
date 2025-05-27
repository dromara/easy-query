package com.easy.query.kingbase.es.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Iterator;
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
        if(EasyCollectionUtil.isSingle(concatExpressions)){
            throw new IllegalArgumentException("KingbaseESConcatSQLFunction columns single");
        }
        this.columnExpressions = concatExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        StringBuilder sb = new StringBuilder("CONCAT({0},{1})");

        for (int i = 2; i < columnExpressions.size(); i++) {
            sb.insert(0, "CONCAT(");
            sb.append(",{").append(i).append("})");
        }

        return sb.toString();
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
