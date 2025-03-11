package com.easy.query.db2.func;

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
public class DB2JoiningSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final boolean distinct;

    public DB2JoiningSQLFunction(List<ColumnExpression> columnExpressions, boolean distinct) {

        this.columnExpressions = columnExpressions;
        this.distinct = distinct;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() < 2) {
            throw new IllegalArgumentException("joining arguments < 2");
        }
        if (columnExpressions.size() == 2) {
            if (defaultTable != null) {
                Collection<String> keyProperties = defaultTable.getEntityMetadata().getKeyProperties();
                if (EasyCollectionUtil.isNotEmpty(keyProperties)) {
                    for (String keyProperty : keyProperties) {
                        columnExpressions.add(new ColumnFuncExpressionImpl(defaultTable, keyProperty));
                    }
                } else {
                    columnExpressions.add(new ColumnFuncFormatExpressionImpl("1"));
                }
            } else {
                columnExpressions.add(new ColumnFuncFormatExpressionImpl("1"));
            }
        }
        int i1 = columnExpressions.size() - 2;
        ArrayList<String> orders = new ArrayList<>(i1);
        for (int i = 0; i < i1; i++) {
            orders.add("{" + (i + 2) + "}");
        }
        if (distinct) {
            return String.format("LISTAGG(DISTINCT TO_CHAR({1}), {0}) WITHIN GROUP(ORDER BY %s)", String.join(",", orders));
        }
        return String.format("LISTAGG(TO_CHAR({1}), {0}) WITHIN GROUP(ORDER BY %s)", String.join(",", orders));
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
