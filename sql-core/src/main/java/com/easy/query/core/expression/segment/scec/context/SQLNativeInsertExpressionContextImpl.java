package com.easy.query.core.expression.segment.scec.context;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstValueExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnParamValueExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ConstParamExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/8/4 22:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeInsertExpressionContextImpl implements SQLNativeExpressionContext{
    private final List<ConstParamExpression> expressions=new ArrayList<>();
    @Override
    public SQLNativeExpressionContext expression(TableAvailable table, String property) {
        Objects.requireNonNull(table, "table cannot be null");
        Objects.requireNonNull(property, "property cannot be null");
        ColumnPropertyExpressionImpl columnPropertyExpression = new ColumnPropertyExpressionImpl(table, property);
        expressions.add(columnPropertyExpression);
        return this;
    }

    @Override
    public SQLNativeExpressionContext value(Object val) {
        Objects.requireNonNull(val, "val cannot be null");
        if(val instanceof SQLParameter){
            ColumnParamValueExpressionImpl columnParamValueExpression = new ColumnParamValueExpressionImpl((SQLParameter) val);
            expressions.add(columnParamValueExpression);
        }else{
            ColumnConstValueExpressionImpl columnConstValueExpression = new ColumnConstValueExpressionImpl(val);
            expressions.add(columnConstValueExpression);
        }
        return this;
    }

    @Override
    public List<ConstParamExpression> getExpressions() {
        return expressions;
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public SQLNativeExpressionContext setAlias(String alias) {
        return this;
    }
}
