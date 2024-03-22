package com.easy.query.core.proxy.core.chain;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;

import java.text.MessageFormat;
import java.util.Collection;

/**
 * create time 2024/3/22 11:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLExecutor {
    private final Expression expression;
    private final String sqlSegment;
    private SQLExpression1<SQLNativeProxyExpressionContext> contextConsume;

    public SQLExecutor(Expression expression, String sqlSegment) {
        this.expression = expression;

        this.sqlSegment = sqlSegment;
        this.contextConsume = content -> {
        };
    }
    public <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> SQLExecutor expression(SQLColumn<TEntityProxy, TProperty> sqlColumn){
        contextConsume=context->{
            contextConsume.apply(context);
            context.expression(sqlColumn);
        };
        return this;
    }

    public <TEntity> SQLExecutor expression(Query<TEntity> subQuery){
        contextConsume=context->{
            contextConsume.apply(context);
            context.expression(subQuery);
        };
        return this;
    }

    public <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> SQLExecutor columnName(SQLColumn<TEntityProxy, TProperty> sqlColumn, String columnName){

        contextConsume=context->{
            contextConsume.apply(context);
            context.columnName(sqlColumn,columnName);
        };
        return this;
    }


    public SQLExecutor value(Object val){

        contextConsume=context->{
            contextConsume.apply(context);
            context.value(val);
        };
        return this;
    }
    /**
     * 将参数以[?,?,?]参数化形式拼接到sql中
     * @param values
     * @return
     * @param <T>
     */
    public <T> SQLExecutor collection(Collection<T> values){

        contextConsume=context->{
            contextConsume.apply(context);
            context.collection(values);
        };
        return this;
    }

    public SQLExecutor format(Object formatVal){

        contextConsume=context->{
            contextConsume.apply(context);
            context.format(formatVal);
        };
        return this;
    }

    public SQLExecutor setAlias(String alias){

        contextConsume=context->{
            contextConsume.apply(context);
            context.setAlias(alias);
        };
        return this;
    }
    /**
     * 因为默认{@link MessageFormat#format(Object)}会将参数"ifnull(id,'')"改为"ifnull(id,')"
     * 会认为单引号是转义符所以这边需要将单引号全部改为双引号,所以采用和输入文本一样的风格
     * 如果需要参数化将文本单引号改成双引号
     * @return
     */
    public SQLExecutor keepStyle(){

        contextConsume=context->{
            contextConsume.apply(context);
            context.keepStyle();
        };
        return this;
    }

    /**
     * 使用message format 格式化风格 单引号作为转义
     * @return
     */
    public SQLExecutor messageFormat(){

        contextConsume=context->{
            contextConsume.apply(context);
            context.messageFormat();
        };
        return this;
    }

    public void executeSQL(){
        expression.sql(sqlSegment,contextConsume);
    }
}
