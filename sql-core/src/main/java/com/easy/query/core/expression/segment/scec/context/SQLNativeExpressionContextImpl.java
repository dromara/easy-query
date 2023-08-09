package com.easy.query.core.expression.segment.scec.context;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstSQLParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnSQLParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ConstValueParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/7/29 17:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeExpressionContextImpl implements SQLNativeExpressionContext {
    private final List<ParamExpression> expressions=new ArrayList<>();
    private String alias;
    public SQLNativeExpressionContextImpl expression(TableAvailable table, String property){
        Objects.requireNonNull(table, "table cannot be null");
        Objects.requireNonNull(property, "property cannot be null");
        ColumnPropertyExpressionImpl columnPropertyExpression = new ColumnPropertyExpressionImpl(table, property);
        expressions.add(columnPropertyExpression);
        return this;
    }
    public SQLNativeExpressionContextImpl value(Object val){
        if(val instanceof SQLParameter){
            ColumnSQLParameterExpressionImpl columnParamValueExpression = new ColumnSQLParameterExpressionImpl((SQLParameter) val);
            expressions.add(columnParamValueExpression);
        }else{
            ColumnConstSQLParameterExpressionImpl columnConstValueExpression = new ColumnConstSQLParameterExpressionImpl(val);
            expressions.add(columnConstValueExpression);
        }
        return this;
    }

    @Override
    public SQLNativeExpressionContext constValue(Object constVal) {
        ConstValueParamExpressionImpl constValueParamExpression = new ConstValueParamExpressionImpl(constVal);
        expressions.add(constValueParamExpression);
        return this;
    }

    public List<ParamExpression> getExpressions() {
        return expressions;
    }

    public String getAlias() {
        return alias;
    }

    public SQLNativeExpressionContext setAlias(String alias) {
        this.alias = alias;
        return this;
    }
}
