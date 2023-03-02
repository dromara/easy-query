package org.easy.query.core.basic.api.insert;

import org.easy.query.core.abstraction.EasyExecutor;
import org.easy.query.core.abstraction.ExecutorContext;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.expression.parser.abstraction.internal.ColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.expression.parser.impl.DefaultInsertSqlColumnSelector;
import org.easy.query.core.expression.context.InsertContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: AbstractInsert.java
 * @Description: 文件说明
 * @Date: 2023/2/20 12:30
 * @Created by xuejiaming
 */
public abstract class AbstractInsert<T> implements Insert<T> {
    protected final List<T> entities;
    protected final InsertContext insertContext;

    public AbstractInsert(Class<T> clazz, InsertContext insertContext) {
        this.insertContext = insertContext;
        this.entities = new ArrayList<>();
        EntityMetadata entityMetadata = insertContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        insertContext.addSqlTable(new SqlTableInfo(entityMetadata,null,0, MultiTableTypeEnum.FROM));
    }

    @Override
    public Insert<T> insert(T entity) {
        if (entity != null) {
            entities.add(entity);
        }
        return this;
    }

//
//    @Override
//    public Insert<T> ignoreColumns(SqlExpression<SqlColumnSelector<T>> columnExpression) {
//        return null;
//    }

    @Override
    public long executeRows() {
        if (!entities.isEmpty()) {
            SqlExpression<SqlColumnSelector<T>> selectExpression= ColumnSelector::columnAll;
            DefaultInsertSqlColumnSelector<T> columnSelector = new DefaultInsertSqlColumnSelector<>(0, insertContext, insertContext.getColumns());
            selectExpression.apply(columnSelector);
            String insertSql = toSql();
            System.out.println("插入sql："+insertSql);
            if (!StringUtil.isBlank(insertSql)) {
                EasyExecutor easyExecutor = insertContext.getRuntimeContext().getEasyExecutor();
                return easyExecutor.insert(ExecutorContext.create(insertContext.getRuntimeContext()),insertSql,entities,insertContext.getParameters());
            }
        }

        return 0;
    }


    @Override
    public abstract String toSql();
}
