package com.easy.query.dameng.func;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncValueExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.impl.AbstractLikeSQLFunction;

import java.util.List;

/**
 * create time 2024/3/11 20:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengLikeSQLFunction extends AbstractLikeSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final SQLLikeEnum sqlLikeEnum;

    public DamengLikeSQLFunction(List<ColumnExpression> columnExpressions, SQLLikeEnum sqlLikeEnum) {

        this.columnExpressions = columnExpressions;
        this.sqlLikeEnum = sqlLikeEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        DamengLikeFunctionTemplateGenerator templateGenerator = new DamengLikeFunctionTemplateGenerator(
                columnExpressions, sqlLikeEnum, o -> getColumnFuncValueExpression(o)
                , "INSTR({0},{1},1,1) = 1"
                , "INSTR({0},{1},1,1) = (LENGTH({0}) - LENGTH({1}) + 1)"
                , "INSTR({0},{1},1,1) > 0"
                , "{0} LIKE (TO_CHAR({1})||'%')"
                , "{0} LIKE ('%'||TO_CHAR({1}))"
                , "{0} LIKE ('%'||TO_CHAR({1})||'%')"
        );
        return templateGenerator.sqlSegment(defaultTable);
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
