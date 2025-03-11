package com.easy.query.mssql.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;

import java.util.List;

/**
 * create time 2023/12/21 11:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLJoiningSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final boolean distinct;

    public MsSQLJoiningSQLFunction(List<ColumnExpression> columnExpressions, boolean distinct) {

        this.columnExpressions = columnExpressions;
        this.distinct = distinct;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() != 2) {
            throw new IllegalArgumentException("joining arguments != 2");
        }
//        if(defaultTable!=null){
//            ColumnPropertyExpression columnPropertyExpression = (ColumnPropertyExpression)columnExpressions.get(1);
//            ColumnFuncValueExpression columnFuncValueExpression = (ColumnFuncValueExpression)columnExpressions.get(0);
//            String property = columnPropertyExpression.getProperty();
//            EasyQueryClient easyQueryClient=serviceProvider.getService(EasyQueryClient.class);
//            ClientQueryable<?> forXmlQuery = easyQueryClient.queryable(defaultTable.getEntityClass())
//                    .where(o -> o.getFilter().eq(o.getTable(), property, defaultTable, property))
//                    .select(o -> o.sqlNativeSegment("{0} + {1}", c -> c.value(columnFuncValueExpression.getValue()).expression(property)));
//            columnExpressions.clear();
//            columnExpressions.add(new ColumnFuncValueExpressionImpl(columnFuncValueExpression.getValue()));
//            columnExpressions.add(new ColumnSubQueryExpressionImpl(forXmlQuery));
//            return "STUFF(({1} FOR XML PATH('')),1,LEN({0}),'')";
//        }
        if (distinct) {
            return "STRING_AGG(DISTINCT {1}, {0})";
        }
        return "STRING_AGG({1}, {0})";
        //
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
