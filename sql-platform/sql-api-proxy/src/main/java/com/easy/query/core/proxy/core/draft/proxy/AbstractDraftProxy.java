package com.easy.query.core.proxy.core.draft.proxy;

import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.BasicResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.EntityResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.props.BasicJdbcProperty;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.ValueObjectProxyEntity;

/**
 * create time 2024/1/26 22:02
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractDraftProxy<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> extends AbstractProxyEntity<TProxy, TEntity> implements DraftProxy {
    private final ResultColumnMetadata[] propTypes;

    public AbstractDraftProxy(int capacity) {
        this.propTypes = new ResultColumnMetadata[capacity];
    }

    protected  <TProperty> void fetch(int index, PropTypeColumn<TProperty> column, String alias) {
        SQLSelectAsExpression sqlSelectAsExpression = column.as(alias);
        if (sqlSelectAsExpression instanceof ValueObjectProxyEntity) {
            throw new EasyQueryInvalidOperationException("draft result not support value object columns");
        }
        if (column instanceof SQLColumn && column.getTable() != null) {
            EntityMetadata entityMetadata = column.getTable().getEntityMetadata();
            ColumnMetadata columnMetadata = entityMetadata.getColumnOrNull(column.getValue());
            if (columnMetadata != null) {
                propTypes[index] = new EntityResultColumnMetadata(index, entityMetadata, columnMetadata);
            } else {
                propTypes[index] = new BasicResultColumnMetadata(column.getPropertyType(), null, new BasicJdbcProperty(index, column.getPropertyType()));
            }
        } else {
            propTypes[index] = new BasicResultColumnMetadata(column.getPropertyType(), null, new BasicJdbcProperty(index, column.getPropertyType()));
        }
        selectExpression(sqlSelectAsExpression);
    }

    @Override
    public ResultColumnMetadata[] getDraftPropTypes() {
        return propTypes;
    }
}
