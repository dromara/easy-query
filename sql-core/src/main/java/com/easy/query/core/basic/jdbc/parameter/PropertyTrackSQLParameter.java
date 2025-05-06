package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyBeanUtil;

import java.util.Objects;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/28 20:47
 */
public final class PropertyTrackSQLParameter implements BeanSQLParameter {
    private final TableAvailable table;
    private final String propertyName;
    private final QueryRuntimeContext runtimeContext;
    private final TrackContext currentTrackContext;
    private Object bean;

    public PropertyTrackSQLParameter(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext) {
        TrackContext currentTrackContext = runtimeContext.getTrackManager().getCurrentTrackContext();
        Objects.requireNonNull(currentTrackContext, "PropertyTrackSQLParameter track context is null");
        this.table = table;
        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
        this.currentTrackContext = currentTrackContext;
    }

    @Override
    public TableAvailable getTableOrNull() {
        return table;
    }

    @Override
    public Object getValue() {
        if (bean == null) {
            throw new EasyQueryException("cant get sql parameter value," + table.getEntityMetadata().getEntityClass() + "." + propertyName + ",bean is null");
        }

        EntityMetadata entityMetadata = table.getEntityMetadata();

        EntityState trackEntityState = currentTrackContext.getTrackEntityState(bean);
        Objects.requireNonNull(trackEntityState, "PropertyTrackSQLParameter trackEntityState is null");
//        ColumnMetadata column = entityMetadata.getColumnNotNull(propertyName);
//        Property<Object, ?> propertyLambda = column.getGetterCaller();
        return EasyBeanUtil.getPropertyValue(trackEntityState.getOriginalValue(), entityMetadata, propertyName);
//        return propertyLambda.apply(bean);
    }

    @Override
    public void setBean(Object bean) {
        this.bean = bean;
    }

    @Override
    public boolean hasBean() {
        return this.bean != null;
    }

    @Override
    public String getPropertyNameOrNull() {
        return propertyName;
    }
}
