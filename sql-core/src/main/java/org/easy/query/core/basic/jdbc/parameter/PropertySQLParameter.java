package org.easy.query.core.basic.jdbc.parameter;

import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.query.builder.SqlTableInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @FileName: EntitySqlParameter.java
 * @Description: 文件说明
 * @Date: 2023/2/28 20:47
 * @Created by xuejiaming
 */
public final class PropertySQLParameter implements BeanSqlParameter {
    private final SqlTableInfo table;
    private final String propertyName;
    private  Object bean;

    public PropertySQLParameter(SqlTableInfo table, String propertyName){
        this.table = table;
        this.propertyName = propertyName;
    }

    @Override
    public SqlTableInfo getTable() {
        return table;
    }

    @Override
    public Object getValue() {
        if(bean==null){
            throw new EasyQueryException("cant get sql parameter value,"+table.getEntityMetadata().getEntityClass()+"."+propertyName+",bean is null");
        }
        EntityMetadata entityMetadata = table.getEntityMetadata();
        ColumnMetadata column = entityMetadata.getColumn(propertyName);
        Method readMethod = column.getProperty().getReadMethod();
        try {
            return readMethod.invoke(bean);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new EasyQueryException(e);
        }
    }

    @Override
    public void setBean(Object bean) {
        this.bean=bean;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }
}
