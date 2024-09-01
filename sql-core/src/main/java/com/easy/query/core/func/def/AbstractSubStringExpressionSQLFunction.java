package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncFormatExpression;
import com.easy.query.core.func.column.ColumnFuncValueExpression;
import com.easy.query.core.func.column.impl.ColumnFuncFormatExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnFunctionExpressionImpl;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * create time 2023/10/18 09:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractSubStringExpressionSQLFunction extends AbstractExpressionSQLFunction{

    protected ColumnExpression getBeginColumnExpression(ColumnExpression columnExpression){
        if(columnExpression instanceof ColumnFuncFormatExpression){
            ColumnFuncFormatExpression columnFuncFormatExpression = (ColumnFuncFormatExpression) columnExpression;
            Object format = columnFuncFormatExpression.getFormat();
            if(format instanceof Number){
                return new ColumnFuncFormatExpressionImpl(((Number) format).intValue()+1);
            }
        }
        if(columnExpression instanceof ColumnFuncValueExpression){
            ColumnFuncValueExpression columnFuncValueExpression = (ColumnFuncValueExpression) columnExpression;
            Object value = columnFuncValueExpression.getValue();
            if(value instanceof Number){
                return new ColumnFuncFormatExpressionImpl(((Number) value).intValue()+1);
            }
        }
        return new ColumnFunctionExpressionImpl(null,new AnySQLFunction("{0}+1", Collections.singletonList(columnExpression)));
    }
}
