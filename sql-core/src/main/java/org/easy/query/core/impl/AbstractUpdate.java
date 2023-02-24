package org.easy.query.core.impl;

import org.easy.query.core.abstraction.lambda.SqlExpression;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.abstraction.sql.base.SqlColumnSetter;
import org.easy.query.core.abstraction.sql.base.SqlPredicate;
import org.easy.query.core.basic.api.Update;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.query.builder.SqlTableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:06
 * @Created by xuejiaming
 */
public class AbstractUpdate<T> implements Update<T> {
    protected final List<T> entities;
    protected final Class<T> clazz;
    protected final UpdateContext updateContext;

    public AbstractUpdate(Class<T> clazz, UpdateContext updateContext){
        this.clazz = clazz;
        this.updateContext = updateContext;
        this.entities = new ArrayList<>();
        EntityMetadata entityMetadata = updateContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        updateContext.addSqlTable(new SqlTableInfo(entityMetadata,null,0, MultiTableTypeEnum.FROM));
    }

    @Override
    public long executeRows() {
        return 0;
    }


    @Override
    public String toSql() {
        return null;
    }
}
