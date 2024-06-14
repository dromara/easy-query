package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.OnlySelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.function.Function;

/**
 * create time 2023/12/19 23:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeSegmentExpressionImpl implements PropTypeColumn<Object>, DSLSQLFunctionAvailable {


    private final EntitySQLContext entitySQLContext;
    private final String sqlSegment;
    private final SQLExpression1<SQLNativeChainExpressionContext> consume;
    private Class<?> propType;

    public SQLNativeSegmentExpressionImpl(EntitySQLContext entitySQLContext, String sqlSegment, SQLExpression1<SQLNativeChainExpressionContext> consume){
        this.entitySQLContext = entitySQLContext;
        this.sqlSegment = sqlSegment;
        this.consume = consume;

        this.propType = Object.class;
    }

    @Override
    public SQLSelectAsExpression as(String propertyAlias) {
        return new SQLSelectAsImpl(s -> {
            SQLFunc fx = s.getRuntimeContext().fx();
            s.columnFunc(this.getTable(), func().apply(fx),propertyAlias);
        }, s -> {
            SQLFunc fx = s.getRuntimeContext().fx();
            s.columnFunc(this.getTable(), func().apply(fx),propertyAlias);
        }, s -> {
            throw new UnsupportedOperationException();
        });
    }

    @Override
    public void accept(GroupSelector s) {
        SQLFunc fx = s.getRuntimeContext().fx();
        s.columnFunc(this.getTable(), func().apply(fx));
    }

    @Override
    public void accept(AsSelector s) {
       SQLFunc fx = s.getRuntimeContext().fx();
        s.columnFunc(this.getTable(), func().apply(fx), null);
    }

    @Override
    public void accept(Selector s) {
        SQLFunc fx = s.getRuntimeContext().fx();
        s.columnFunc(this.getTable(), func().apply(fx),null);
    }

    @Override
    public void accept(OnlySelector s) {
        SQLFunc fx = s.getRuntimeContext().fx();
        SQLFunction sqlFunction = func().apply(fx);
        String sqlSegment = sqlFunction.sqlSegment(null);
        s.sqlNativeSegment(sqlSegment,c->{
            sqlFunction.consume(new SQLNativeChainExpressionContextImpl(null,c));
        });
    }

//        return new SQLSelectAsImpl(s -> {
//        SQLFunc fx = s.getRuntimeContext().fx();
//        s.columnFunc(this.getTable(), func().apply(fx), propertyAlias);
//    }, s -> {
//        SQLFunc fx = s.getRuntimeContext().fx();
//        s.columnFunc(this.getTable(), this.getValue(), func().apply(fx), propertyAlias, () -> {
//        });
//    }, s -> {
//        throw new UnsupportedOperationException();
//    });

    @Override
    public TableAvailable getTable() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public Class<?> getPropertyType() {
        return propType;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }

    @Override
    public <TR> void _setPropertyType(Class<TR> clazz) {
        this.propType=clazz;
    }

    @Override
    public <TR> PropTypeColumn<TR> asAnyType(Class<TR> clazz) {
        _setPropertyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }

    @Override
    public Function<SQLFunc, SQLFunction> func() {
        return f->{
            return f.nativeSql(sqlSegment,consume);
        };
    }
}
