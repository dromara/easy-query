package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.util.BeanUtil;
import com.easy.query.core.util.EasyUtil;

/**
 * @author xuejiaming
 * @FileName: EntitySqlParameter.java
 * @Description: 文件说明
 * @Date: 2023/2/28 20:47
 */
public final class PropertySQLParameter implements BeanSqlParameter {
    private final TableAvailable table;
    private final String propertyName;
    private Object bean;

    public PropertySQLParameter(TableAvailable table, String propertyName) {
        this.table = table;
        this.propertyName = propertyName;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Object getValue() {
        if (bean == null) {
            throw new EasyQueryException("cant get sql parameter value," + table.getEntityMetadata().getEntityClass() + "." + propertyName + ",bean is null");
        }
        EntityMetadata entityMetadata = table.getEntityMetadata();
        ColumnMetadata column = entityMetadata.getColumnNotNull(propertyName);
        FastBean fastBean = BeanUtil.getFastBean(table.getEntityClass());
        Property<Object, ?> propertyLambda = fastBean.getBeanGetter(column.getProperty());
        return propertyLambda.apply(bean);
    }

    @Override
    public void setBean(Object bean) {
        this.bean = bean;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }
}
