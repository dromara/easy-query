package com.easy.query.core.basic.api.delete.abstraction;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.api.internal.AbstractSQLExecuteRows;
import com.easy.query.core.basic.api.delete.EntityDeletable;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.sql.builder.impl.TableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractEntityDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:33
 */
public abstract class AbstractEntityDeletable<T> extends AbstractSQLExecuteRows<EntityDeletable<T>> implements EntityDeletable<T> {
    protected final List<T> entities = new ArrayList<>();
    protected final EntityTableExpressionBuilder table;
    protected final EntityDeleteExpressionBuilder entityDeleteExpressionBuilder;

    public AbstractEntityDeletable(Collection<T> entities, EntityDeleteExpressionBuilder entityDeleteExpressionBuilder) {
        super(entityDeleteExpressionBuilder);
        if (entities == null || entities.isEmpty()) {
            throw new EasyQueryException("不支持空对象的delete");
        }
        this.entities.addAll(entities);
        this.entityDeleteExpressionBuilder = entityDeleteExpressionBuilder;

        Class<?> clazz = entities.iterator().next().getClass();
        QueryRuntimeContext runtimeContext = entityDeleteExpressionBuilder.getRuntimeContext();
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        table = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata,0,null,MultiTableTypeEnum.NONE,runtimeContext);
        this.entityDeleteExpressionBuilder.addSQLEntityTableExpression(table);
    }

    @Override
    public long executeRows() {

        if (!entities.isEmpty()) {
            QueryRuntimeContext runtimeContext = entityDeleteExpressionBuilder.getRuntimeContext();
            EntityExpressionExecutor entityExpressionExecutor = runtimeContext.getEntityExpressionExecutor();
            return entityExpressionExecutor.executeRows(ExecutorContext.create(runtimeContext, false, ExecuteMethodEnum.DELETE), entityDeleteExpressionBuilder, entities);
        }
        return 0;
    }


    @Override
    public EntityDeletable<T> useLogicDelete(boolean enable) {
        entityDeleteExpressionBuilder.setLogicDelete(enable);
        return this;
    }

    @Override
    public EntityDeletable<T> allowDeleteCommand(boolean allow) {
        entityDeleteExpressionBuilder.getExpressionContext().deleteThrow(!allow);
        return this;
    }

    @Override
    public EntityDeletable<T> asTable(Function<String, String> tableNameAs) {
        entityDeleteExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }


    @Override
    public String toSQL() {
        return toSQLWithParam(null);
    }

    private String toSQLWithParam(ToSQLContext toSQLContext) {
        return entityDeleteExpressionBuilder.toExpression().toSQL(toSQLContext);
    }
}
