package com.easy.query.core.basic.api.delete;

import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.SQLBatchExecute;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;

import java.util.List;

/**
 * @author xuejiaming
 * @FileName: EasyEntityDeletable.java
 * @Description: 文件说明
 * create time 2023/3/6 13:07
 */
public interface ClientEntityDeletable<T> extends Deletable<T, ClientEntityDeletable<T>>
        , ConfigureVersionable<ClientEntityDeletable<T>>, SQLBatchExecute<ClientEntityDeletable<T>> {
    List<T> getEntities();
    /**
     * 自定义条件当做where
     * @param columnSelectorExpression
     * @return
     */
    default ClientEntityDeletable<T> whereColumns(SQLActionExpression1<ColumnOnlySelector<T>> columnSelectorExpression) {
        return whereColumns(true, columnSelectorExpression);
    }

    /**
     * 自定义条件当做where
     * @param condition
     * @param columnSelectorExpression
     * @return
     */
    ClientEntityDeletable<T> whereColumns(boolean condition, SQLActionExpression1<ColumnOnlySelector<T>> columnSelectorExpression);
}
