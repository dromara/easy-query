package com.easy.query.core.basic.api.flat.provider;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * create time 2024/3/26 16:59
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractMapTable implements MapTable{
    protected final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    protected final SQLExpressionProvider<?> sqlExpressionProvider;

    public AbstractMapTable(ClientQueryable<?> queryable){
        this.entityQueryExpressionBuilder = queryable.getSQLEntityExpressionBuilder();
        this.sqlExpressionProvider = queryable.getSQLExpressionProvider1();
    }
    @Override
    public TableAvailable getTable(int tableIndex) {
        return entityQueryExpressionBuilder.getTable(tableIndex).getEntityTable();
    }
}
