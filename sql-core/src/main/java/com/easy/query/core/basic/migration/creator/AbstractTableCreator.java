package com.easy.query.core.basic.migration.creator;

import com.easy.query.core.basic.migration.TableCreator;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;

import java.util.function.Function;

/**
 * create time 2023/11/29 21:50
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractTableCreator<TEntity> implements TableCreator<TEntity> {
    protected final Class<TEntity> entityClass;
    protected final QueryRuntimeContext runtimeContext;
    protected Function<String, String> tableNameAs;
    protected EntityMetadata entityMetadata;
    protected SQLKeyword SQLKeyWord;

    public AbstractTableCreator(Class<TEntity> entityClass, QueryRuntimeContext runtimeContext){
        this.entityClass = entityClass;
        this.runtimeContext = runtimeContext;
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        this.entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
        this.SQLKeyWord = runtimeContext.getQueryConfiguration().getDialect();
    }

    @Override
    public TableCreator<TEntity> asTable(Function<String, String> tableNameAs) {
        this.tableNameAs = tableNameAs;
        return this;
    }

    @Override
    public TableCreator<TEntity> columnsConfigure() {
        return null;
    }

    @Override
    public boolean migrate(String fromMigration, String toMigration) {
        return false;
    }

    @Override
    public String generateScript(String fromMigration, String toMigration) {
        if(fromMigration==null&&toMigration==null){
            return generateCreateScript();
        }
        throw new EasyQueryInvalidOperationException("not support from to migration");
    }
    protected abstract String generateCreateScript();
    protected String getTableName(){
        if(this.tableNameAs!=null){
            return tableNameAs.apply(entityMetadata.getTableName());
        }
        return entityMetadata.getTableName();
    }
}
