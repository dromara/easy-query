package com.easy.query.mssql.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.inject.ServiceProvider;

import java.util.List;

/**
 * create time 2023/12/21 11:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLJoinSQLFunction extends AbstractExpressionSQLFunction {
    private final ServiceProvider serviceProvider;
    private final List<ColumnExpression> columnExpressions;

    public MsSQLJoinSQLFunction(ServiceProvider serviceProvider, List<ColumnExpression> columnExpressions) {
        this.serviceProvider = serviceProvider;

        this.columnExpressions = columnExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()!=2){
                throw new IllegalArgumentException("join arguments != 2");
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
