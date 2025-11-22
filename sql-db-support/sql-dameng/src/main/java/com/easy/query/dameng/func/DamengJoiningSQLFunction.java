package com.easy.query.dameng.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.impl.ColumnFuncExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnFuncFormatExpressionImpl;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * create time 2023/12/21 11:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengJoiningSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final boolean distinct;

    public DamengJoiningSQLFunction(List<ColumnExpression> columnExpressions, boolean distinct) {

        this.columnExpressions = columnExpressions;
        this.distinct = distinct;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() < 2) {
            throw new IllegalArgumentException("joining arguments < 2");
        }
        if (columnExpressions.size() == 2) {
            if (distinct) {
                return "LISTAGG(DISTINCT TO_CHAR({1}), {0})";
            }
            return "LISTAGG(TO_CHAR({1}), {0})";
        } else {
            if (distinct) {
                return "LISTAGG(DISTINCT TO_CHAR({1}), {0}) WITHIN GROUP(ORDER BY {2})";
            }
            return "LISTAGG(TO_CHAR({1}), {0}) WITHIN GROUP(ORDER BY {2})";
        }
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
