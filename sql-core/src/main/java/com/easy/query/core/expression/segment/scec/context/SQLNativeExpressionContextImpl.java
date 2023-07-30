package com.easy.query.core.expression.segment.scec.context;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstValueExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ConstParamExpression;

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
    private final List<ConstParamExpression> expressions=new ArrayList<>();
    private String alias;

    public SQLNativeExpressionContextImpl expression(TableAvailable table, String property){
        Objects.requireNonNull(table, "table cannot be null");
        Objects.requireNonNull(property, "property cannot be null");
        ColumnPropertyExpressionImpl columnPropertyExpression = new ColumnPropertyExpressionImpl(table, property);
        expressions.add(columnPropertyExpression);
        return this;
    }
    public SQLNativeExpressionContextImpl value(Object val){
        Objects.requireNonNull(val, "val cannot be null");
        ColumnConstValueExpressionImpl columnConstValueExpression = new ColumnConstValueExpressionImpl(val);
        expressions.add(columnConstValueExpression);
        return this;
    }

    public List<ConstParamExpression> getExpressions() {
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
