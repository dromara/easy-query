package com.easy.query.core.func.def.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.expression.ColumnFormatExpression;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncFormatExpression;
import com.easy.query.core.func.column.ColumnFuncValueExpression;
import com.easy.query.core.func.column.impl.ColumnFuncFormatExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnFunctionExpressionImpl;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.AbstractSubStringExpressionSQLFunction;
import com.easy.query.core.func.def.AnySQLFunction;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * create time 2023/10/18 09:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class SubStringSQLFunction extends AbstractSubStringExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions=new ArrayList<>(3);

    public SubStringSQLFunction(List<ColumnExpression> columnExpressions) {
        if (columnExpressions.size() != 3) {
            throw new UnsupportedOperationException("substring sql function must have 3 params");
        }
        for (int i = 0; i < columnExpressions.size(); i++) {
            ColumnExpression columnExpression = columnExpressions.get(i);
            if(i==1){
                this.columnExpressions.add(getBeginColumnExpression(columnExpression));
            }else{
                this.columnExpressions.add(columnExpression);
            }
        }
    }
    

    @Override
    public String sqlSegment(TableAvailable defaultTable) {

        Iterable<String> params = EasyCollectionUtil.select(columnExpressions, (t, i) -> "{" + i + "}");
        return String.format("SUBSTR(%s)", String.join(",", params));
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
