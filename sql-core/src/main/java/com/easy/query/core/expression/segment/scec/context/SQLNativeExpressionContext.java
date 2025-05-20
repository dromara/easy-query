package com.easy.query.core.expression.segment.scec.context;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.metadata.EntityMetadata;

import java.text.MessageFormat;
import java.util.Collection;

/**
 * create time 2023/7/29 21:44
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNativeExpressionContext extends SQLNativeExpression , RuntimeContextAvailable {

    ExpressionContext getExpressionContext();
    void expression(TableAvailable table, String property);
    void columnName(TableAvailable table, String columnName);
    <TEntity> void expression(Query<TEntity> subQuery);
    void value(Object val);
    void sql(SQLSegment sqlSegment);
    void sqlFunction(SQLFunction sqlFunction);
    <T> void collection(Collection<T> values);

    void format(Object formatVal);


    /**
     * 别名 column_name
     * @param alias
     * @return
     */
    void setAlias(String alias);
    /**
     * 不将单引号设置为双引号
     * @return
     */
    void messageFormat();

    void expressionAlias(String property);
    void setPropertyAlias(String property);

    /**
     * 添加返回对象结果
     * @param resultEntityMetadata
     */
    void setResultEntityMetadata(EntityMetadata resultEntityMetadata);
}
