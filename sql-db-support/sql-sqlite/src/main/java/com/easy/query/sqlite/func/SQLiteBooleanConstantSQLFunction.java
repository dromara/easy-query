package com.easy.query.sqlite.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2025/3/10 16:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteBooleanConstantSQLFunction extends AbstractExpressionSQLFunction {

    private final boolean trueOrFalse;

    public SQLiteBooleanConstantSQLFunction(boolean trueOrFalse) {
        this.trueOrFalse = trueOrFalse;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (trueOrFalse) {
            return "1";
        }
        return "0";
    }

    @Override
    public int paramMarks() {
        return 0;
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return EasyCollectionUtil.emptyList();
    }

}