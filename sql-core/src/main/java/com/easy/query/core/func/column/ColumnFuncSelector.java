package com.easy.query.core.func.column;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFuncSelector {
    ColumnFuncSelector column(String property);
    ColumnFuncSelector column(SQLTableOwner tableOwner, String property);
    ColumnFuncSelector column(TableAvailable table, String property);
    ColumnFuncSelector value(Object val);
//    ColumnFuncSelector sqlParameter(SQLParameter sqlParameter);
    ColumnFuncSelector format(Object valFormat);
    ColumnFuncSelector predicate(SQLExpression1<Filter> filterExpression);
    ColumnFuncSelector sql(SQLSegment sqlSegment);
   default ColumnFuncSelector sqlFunc(SQLFunction sqlFunction){
       return sqlFunc(null,sqlFunction);
   }
    ColumnFuncSelector sqlFunc(TableAvailable table,SQLFunction sqlFunction);
    ColumnFuncSelector subQuery(Query<?> subQuery);
}
