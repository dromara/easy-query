package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.sql.SqlEntityTableExpression;
import com.easy.query.core.expression.sql.SqlTableExpressionSegment;
import com.easy.query.core.util.EasyUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @FileName: EntitySqlParameter.java
 * @Description: 文件说明
 * @Date: 2023/2/28 20:47
 * @author xuejiaming
 */
public final class PropertySQLParameter implements BeanSqlParameter {
    private final SqlEntityTableExpression table;
    private final String propertyName;
    private  Object bean;

    public PropertySQLParameter(SqlEntityTableExpression table, String propertyName){
        this.table = table;
        this.propertyName = propertyName;
    }

    @Override
    public SqlEntityTableExpression getTable() {
        return table;
    }

    @Override
    public Object getValue() {
        if(bean==null){
            throw new EasyQueryException("cant get sql parameter value,"+table.getEntityMetadata().getEntityClass()+"."+propertyName+",bean is null");
        }
        EntityMetadata entityMetadata = table.getEntityMetadata();
        ColumnMetadata column = entityMetadata.getColumnNotNull(propertyName);
        FastBean fastBean = EasyUtil.getFastBean(table.entityClass());
        Property<Object, ?> propertyLambda = fastBean.getBeanGetter(column.getProperty());
        return propertyLambda.apply(bean);
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
