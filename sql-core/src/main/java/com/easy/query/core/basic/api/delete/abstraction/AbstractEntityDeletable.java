package com.easy.query.core.basic.api.delete.abstraction;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.api.internal.AbstractSqlExecuteRows;
import com.easy.query.core.basic.api.delete.EntityDeletable;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.sql.def.EasyEntityTableExpression;
import com.easy.query.core.expression.sql.EntityDeleteExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.util.StringUtil;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @FileName: AbstractEntityDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:33
 * @author xuejiaming
 */
public abstract class AbstractEntityDeletable<T> extends AbstractSqlExecuteRows<EntityDeletable<T>> implements EntityDeletable<T> {
    protected final List<T> entities= new ArrayList<>();
    protected final EntityTableExpression table;
    protected final EntityDeleteExpression entityDeleteExpression;

    public AbstractEntityDeletable(Collection<T> entities, EntityDeleteExpression entityDeleteExpression){
        super(entityDeleteExpression);
        if(entities==null||entities.isEmpty()){
            throw new EasyQueryException("不支持空对象的delete");
        }
        this.entities.addAll(entities);
        this.entityDeleteExpression = entityDeleteExpression;

        Class<?> clazz = entities.iterator().next().getClass();
        EntityMetadata entityMetadata = entityDeleteExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        table = new EasyEntityTableExpression(entityMetadata,  0,null, MultiTableTypeEnum.FROM);
        this.entityDeleteExpression.addSqlEntityTableExpression(table);
    }

    @Override
    public long executeRows() {

        if (!entities.isEmpty()) {
            String deleteSql = toSql();
            if(StringUtil.isNotBlank(deleteSql)){
                EasyQueryRuntimeContext runtimeContext = entityDeleteExpression.getRuntimeContext();
                EasyExecutor easyExecutor = runtimeContext.getEasyExecutor();
                return easyExecutor.executeRows(ExecutorContext.create(runtimeContext), deleteSql,entities, entityDeleteExpression.getParameters());
            }
        }
        return 0;
    }


    @Override
    public EntityDeletable<T> useLogicDelete(boolean enable) {
        entityDeleteExpression.setLogicDelete(enable);
        return this;
    }

    @Override
    public EntityDeletable<T> allowDeleteCommand(boolean allow) {
        entityDeleteExpression.getExpressionContext().deleteThrow(!allow);
        return this;
    }

    @Override
    public EntityDeletable<T> asTable(Function<String, String> tableNameAs) {
        entityDeleteExpression.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }
}
