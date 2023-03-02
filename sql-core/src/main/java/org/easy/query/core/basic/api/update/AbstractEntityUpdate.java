package org.easy.query.core.basic.api.update;

import org.easy.query.core.abstraction.EasyExecutor;
import org.easy.query.core.abstraction.ExecutorContext;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.parser.impl.DefaultSqlColumnSetSelector;
import org.easy.query.core.expression.context.UpdateContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.util.StringUtil;

import java.util.*;

/**
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:06
 * @Created by xuejiaming
 */
public abstract class AbstractEntityUpdate<T> implements EntityUpdate<T> {
    protected final List<T> entities= new ArrayList<>();
    protected final SqlTableInfo table;
    protected final UpdateContext updateContext;

    public AbstractEntityUpdate(Collection<T> entities, UpdateContext updateContext) {
        if(entities==null||entities.isEmpty()){
            throw new EasyQueryException("不支持空对象的update");
        }
        this.entities.addAll(entities);

        Class<?> clazz = entities.iterator().next().getClass();
        this.updateContext = updateContext;
        EntityMetadata entityMetadata = updateContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        table=new SqlTableInfo(entityMetadata, null, 0, MultiTableTypeEnum.FROM);
        updateContext.addSqlTable(table);
    }

    @Override
    public long executeRows() {
        if (!entities.isEmpty()) {

            String updateSql = toSql();
            System.out.println("更新sql："+updateSql);
            if (!StringUtil.isBlank(updateSql)) {
                EasyExecutor easyExecutor = updateContext.getRuntimeContext().getEasyExecutor();
                return easyExecutor.update(ExecutorContext.create(updateContext.getRuntimeContext()), updateSql, entities, updateContext.getParameters());
            }
        }
        return 0;
    }

    @Override
    public void executeRows(Long expectRow, String error) {

    }

    @Override
    public EntityUpdate<T> setColumns(boolean condition,SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        if(condition){
            DefaultSqlColumnSetSelector<T> columnSelector = new DefaultSqlColumnSetSelector<>(0, updateContext, updateContext.getSetColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public EntityUpdate<T> setIgnoreColumns(boolean condition,SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        if(condition){
            DefaultSqlColumnSetSelector<T> columnSelector = new DefaultSqlColumnSetSelector<>(0, updateContext, updateContext.getSetIgnoreColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public EntityUpdate<T> whereColumns(boolean condition,SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        if(condition){
            DefaultSqlColumnSetSelector<T> columnSelector = new DefaultSqlColumnSetSelector<>(0, updateContext, updateContext.getWhereColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }
}
