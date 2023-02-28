package org.easy.query.core.basic.api.delete;

import org.easy.query.core.abstraction.EasyExecutor;
import org.easy.query.core.abstraction.ExecutorContext;
import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.basic.api.context.DeleteContext;
import org.easy.query.core.basic.sql.segment.builder.SqlSegmentBuilder;
import org.easy.query.core.basic.sql.segment.segment.ColumnPredicateSegment;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @FileName: AbstractEntityDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:33
 * @Created by xuejiaming
 */
public abstract class AbstractEntityDelete<T> implements EasyDelete<T> {
    protected final List<T> entities= new ArrayList<>();
    protected final Class<T> clazz;
    protected final EntityMetadata entityMetadata;
    protected final DeleteContext deleteContext;

    public AbstractEntityDelete(Collection<T> entities, DeleteContext deleteContext){
        if(entities==null||entities.isEmpty()){
            throw new EasyQueryException("不支持空对象的delete");
        }
        this.entities.addAll(entities);
        this.deleteContext = deleteContext;

        this.clazz = (Class<T>)entities.iterator().next().getClass();
        this.entityMetadata = deleteContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        deleteContext.addSqlTable(new SqlTableInfo(entityMetadata, null, 0, MultiTableTypeEnum.FROM));
    }
    @Override
    public long executeRows() {

        if (!entities.isEmpty()) {
//            EasyQueryRuntimeContext runtimeContext = updateContext.getRuntimeContext();
//            EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();

            //如果没有指定where那么就使用主键作为更新条件
            SqlSegmentBuilder whereColumns = deleteContext.getWhereColumns();

            Collection<String> keyProperties = entityMetadata.getKeyProperties();
            if(keyProperties.isEmpty()){
                throw new EasyQueryException("对象:"+clazz.getSimpleName()+" 未找到主键信息");
            }
            for (String keyProperty : keyProperties) {
                ColumnMetadata column = entityMetadata.getColumn(keyProperty);
                String propertyName = column.getProperty().getName();
                whereColumns.append(new ColumnPredicateSegment(0, column.getName(),propertyName,deleteContext));
            }


//            String updateSql = toSql();
//            System.out.println("更新sql："+updateSql);
//            if (!StringUtil.isBlank(updateSql)) {
//                EasyExecutor easyExecutor = updateContext.getRuntimeContext().getEasyExecutor();
//                return easyExecutor.update(ExecutorContext.create(updateContext.getRuntimeContext()), clazz, updateSql, entities, updateContext.getProperties());
//            }
        }
        return 0;
    }

    @Override
    public void executeRows(Long expectRow, String error) {

    }
}
