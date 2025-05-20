package com.easy.query.core.expression.parser.core.base.scec.core;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;

import java.text.MessageFormat;
import java.util.Collection;

/**
 * create time 2023/10/12 13:39
 * 原生sql的表达式链上下文
 *
 * @author xuejiaming
 */
public interface SQLNativeChainExpressionContext extends RuntimeContextAvailable {
    SQLNativeExpressionContext getSQLNativeExpressionContext();
    ExpressionContext getExpressionContext();
    TableAvailable getDefaultTable();
    void expression(String property);

    void expression(TableAvailable table, String property);

    <TEntity> void expression(Query<TEntity> subQuery);
    void columnName(String columnName);
    void columnName(TableAvailable table, String columnName);

    void value(Object val);

    <T> void collection(Collection<T> values);

    void sql(SQLSegment sqlSegment);

    void format(Object formatVal);


    /**
     * 别名 column_name
     *
     * @param alias
     * @return
     */
    void setAlias(String alias);


    /**
     * 不将单引号设置为双引号
     *
     * @return
     */
    void messageFormat();

    void expressionAlias(String property);

    void setPropertyAlias(String property);

    /**
     * 添加返回对象结果
     *
     * @param entityMetadata
     */
    void setResultEntityMetadata(EntityMetadata entityMetadata);
}
