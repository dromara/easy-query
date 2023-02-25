package org.easy.query.core.impl;

import org.easy.query.core.abstraction.*;
import org.easy.query.core.abstraction.lambda.SqlExpression;
import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.abstraction.metadata.EntityMetadataManager;
import org.easy.query.core.abstraction.sql.base.ColumnSelector;
import org.easy.query.core.abstraction.sql.base.SqlColumnSelector;
import org.easy.query.core.basic.api.EntityUpdate;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.exception.JDQCException;
import org.easy.query.core.impl.lambda.select.DefaultSqlColumnSelector;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.segments.ColumnSegment;
import org.easy.query.core.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:06
 * @Created by xuejiaming
 */
public abstract class AbstractEntityUpdate<T> implements EntityUpdate<T> {
    protected final List<T> entities= new ArrayList<>();
    protected final Class<T> clazz;
    protected final EntityMetadata entityMetadata;
    protected final UpdateContext updateContext;

    public AbstractEntityUpdate(Collection<T> entities, UpdateContext updateContext) {
        if(entities==null||entities.isEmpty()){
            throw new JDQCException("不支持空对象的update");
        }
        this.entities.addAll(entities);
        this.clazz = (Class<T>)entities.stream().findFirst().orElse(null).getClass();
        this.updateContext = updateContext;
        this.entityMetadata = updateContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        updateContext.addSqlTable(new SqlTableInfo(entityMetadata, null, 0, MultiTableTypeEnum.FROM));
    }

    @Override
    public long executeRows() {
        if (!entities.isEmpty()) {
//            EasyQueryRuntimeContext runtimeContext = updateContext.getRuntimeContext();
//            EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();

            //如果没有指定where那么就使用主键作为更新条件
            SqlSegment0Builder whereColumns = updateContext.getWhereColumns();
            if (whereColumns.isEmpty()) {
                Collection<String> keyProperties = entityMetadata.getKeyProperties();
                if(keyProperties.isEmpty()){
                    throw new JDQCException("对象:"+clazz.getSimpleName()+" 未找到主键信息");
                }
                for (String keyProperty : keyProperties) {
                    ColumnMetadata column = entityMetadata.getColumn(keyProperty);
                    String propertyName = column.getProperty().getName();
                    whereColumns.append(new ColumnSegment(0, column.getName(),propertyName,updateContext));
                }
            }
            if (updateContext.getSetColumns().isEmpty()) {
                SqlExpression<SqlColumnSelector<T>> selectExpression = ColumnSelector::columnAll;
                DefaultSqlColumnSelector<T> columnSelector = new DefaultSqlColumnSelector<>(0, updateContext, updateContext.getSetColumns());
                selectExpression.apply(columnSelector);//获取set的值
                //非手动指定的那么需要移除where的那一部分
                List<SqlSegment> sqlSegments = updateContext.getSetColumns().getSqlSegments();
                Set<String> whereProperties = whereColumns.getSqlSegments().stream().filter(o -> o instanceof ColumnSegment).map(o -> ((ColumnSegment) o).getPropertyName()).collect(Collectors.toSet());
                sqlSegments.removeIf(o->{
                    if(o instanceof ColumnSegment){
                        String propertyName = ((ColumnSegment) o).getPropertyName();
                        return whereProperties.contains(propertyName);
                    }
                    return false;
                });
            }

            String updateSql = toSql();
            System.out.println("更新sql："+updateSql);
            if (!StringUtil.isBlank(updateSql)) {
//                List<String> properties = insertContext.getColumns().getSqlSegments().stream().map(o -> ((ColumnSegment) o).getPropertyName()).collect(Collectors.toList());
//                EasyExecutor easyExecutor = updateContext.getRuntimeContext().getEasyExecutor();
//                return easyExecutor.insert(ExecutorContext.create(insertContext.getRuntimeContext()), clazz, insertSql, entities, properties);
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
            DefaultSqlColumnSelector<T> columnSelector = new DefaultSqlColumnSelector<>(0, updateContext, updateContext.getSetColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public EntityUpdate<T> setIgnoreColumns(boolean condition,SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        if(condition){
            DefaultSqlColumnSelector<T> columnSelector = new DefaultSqlColumnSelector<>(0, updateContext, updateContext.getSetIgnoreColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public EntityUpdate<T> whereColumns(boolean condition,SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        if(condition){
            DefaultSqlColumnSelector<T> columnSelector = new DefaultSqlColumnSelector<>(0, updateContext, updateContext.getWhereColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }
}
