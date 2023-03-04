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
import org.easy.query.core.query.EasyEntityTableExpression;
import org.easy.query.core.query.SqlEntityTableExpression;
import org.easy.query.core.query.SqlEntityUpdateExpression;
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
    protected final SqlEntityTableExpression table;
    protected final SqlEntityUpdateExpression sqlEntityUpdateExpression;

    public AbstractEntityUpdate(Collection<T> entities, SqlEntityUpdateExpression sqlEntityUpdateExpression) {
        if(entities==null||entities.isEmpty()){
            throw new EasyQueryException("不支持空对象的update");
        }
        this.entities.addAll(entities);

        Class<?> clazz = entities.iterator().next().getClass();
        this.sqlEntityUpdateExpression = sqlEntityUpdateExpression;

        EntityMetadata entityMetadata = this.sqlEntityUpdateExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        table = new EasyEntityTableExpression(entityMetadata,  0,null, MultiTableTypeEnum.FROM);
        this.sqlEntityUpdateExpression.addSqlEntityTableExpression(table);
    }

    @Override
    public long executeRows() {
        if (!entities.isEmpty()) {

            String updateSql = toSql();
            System.out.println("更新sql："+updateSql);
            if (!StringUtil.isBlank(updateSql)) {
                EasyExecutor easyExecutor = sqlEntityUpdateExpression.getRuntimeContext().getEasyExecutor();
                return easyExecutor.update(ExecutorContext.create(sqlEntityUpdateExpression.getRuntimeContext()), updateSql, entities, sqlEntityUpdateExpression.getParameters());
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
            DefaultSqlColumnSetSelector<T> columnSelector = new DefaultSqlColumnSetSelector<>(0, sqlEntityUpdateExpression, sqlEntityUpdateExpression.getSetColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public EntityUpdate<T> setIgnoreColumns(boolean condition,SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        if(condition){
            DefaultSqlColumnSetSelector<T> columnSelector = new DefaultSqlColumnSetSelector<>(0, sqlEntityUpdateExpression, sqlEntityUpdateExpression.getSetIgnoreColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public EntityUpdate<T> whereColumns(boolean condition,SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        if(condition){
            DefaultSqlColumnSetSelector<T> columnSelector = new DefaultSqlColumnSetSelector<>(0, sqlEntityUpdateExpression, sqlEntityUpdateExpression.getWhereColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }
}
