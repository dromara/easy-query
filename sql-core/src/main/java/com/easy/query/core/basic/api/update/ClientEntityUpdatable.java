package com.easy.query.core.basic.api.update;

import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.SQLExecuteStrategy;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.ToSQLResult;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;

/**
 * create time 2023/7/2 21:50
 * 属性模式下的对象更新
 *
 * @author xuejiaming
 */
public interface ClientEntityUpdatable<T> extends Updatable<T, ClientEntityUpdatable<T>>,
        SQLExecuteStrategy<ClientEntityUpdatable<T>>,
        ConfigureVersionable<ClientEntityUpdatable<T>>
{
    /**
     *  update set columns...  针对set的column进行选择
     * @param columnSelectorExpression
     * @return
     */
    default ClientEntityUpdatable<T> setColumns(SQLExpression1<ColumnOnlySelector<T>> columnSelectorExpression) {
        return setColumns(true, columnSelectorExpression);
    }

    /**
     *  update set columns...  针对set的column进行选择
     * @param condition
     * @param columnSelectorExpression
     * @return
     */
    ClientEntityUpdatable<T> setColumns(boolean condition, SQLExpression1<ColumnOnlySelector<T>> columnSelectorExpression);

    /**
     * 忽略的是set的id,并不是where的
     * @param columnSelectorExpression
     * @return
     */
    default ClientEntityUpdatable<T> setIgnoreColumns(SQLExpression1<ColumnOnlySelector<T>> columnSelectorExpression) {
        return setIgnoreColumns(true, columnSelectorExpression);
    }

    /**
     * 忽略的是set的id,并不是where的
     * @param condition
     * @param columnSelectorExpression
     * @return
     */
    ClientEntityUpdatable<T> setIgnoreColumns(boolean condition, SQLExpression1<ColumnOnlySelector<T>> columnSelectorExpression);

    /**
     * 当前环境如果是追踪的情况下设置的column值将以原始值original value作为条件,新值作为set进行更新
     * setColumns被调用后id也不会默认添加到条件中去,需要自行添加
     * @param columnSelectorExpression
     * @return
     */
    default ClientEntityUpdatable<T> whereColumns(SQLExpression1<ColumnOnlySelector<T>> columnSelectorExpression) {
        return whereColumns(true, columnSelectorExpression);
    }

    /**
     * 当前环境如果是追踪的情况下设置的column值将以原始值original value作为条件,新值作为set进行更新
     * setColumns被调用后id也不会默认添加到条件中去,需要自行添加
     * @param condition
     * @param columnSelectorExpression
     * @return
     */
    ClientEntityUpdatable<T> whereColumns(boolean condition, SQLExpression1<ColumnOnlySelector<T>> columnSelectorExpression);

    ClientEntityUpdatable<T> columnConfigure(SQLExpression1<ColumnConfigurer<T>> columnConfigureExpression);
    String toSQL(Object entity);
    String toSQL(Object entity, ToSQLContext toSQLContext);
    /**
     * 传入生成sql的上下文用来获取生成sql后的表达式内部的参数
     * @return 包含sql和sql结果比如参数
     */

    default ToSQLResult toSQLResult(Object entity) {
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(getUpdateExpressionBuilder().getExpressionContext().getTableContext(),true);
        String sql = toSQL(entity, toSQLContext);
        return new ToSQLResult(sql,toSQLContext);
    }
}
