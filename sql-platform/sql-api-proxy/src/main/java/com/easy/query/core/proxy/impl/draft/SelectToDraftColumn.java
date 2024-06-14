package com.easy.query.core.proxy.impl.draft;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/23 22:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class SelectToDraftColumn<TProperty> implements PropTypeColumn<TProperty> {
    private final SQLSelectExpression sqlSelectExpression;
    private Class<?> propType;

    public SelectToDraftColumn(SQLSelectExpression sqlSelectExpression, Class<TProperty> propType) {

        this.sqlSelectExpression = sqlSelectExpression;
        this.propType = propType;
    }

    @Override
    public TableAvailable getTable() {
        return sqlSelectExpression.getTable();
    }

    @Override
    public String getValue() {
        return sqlSelectExpression.getValue();
    }

    @Override
    public Class<?> getPropertyType() {
        return propType;
    }

    @Override
    public <TR> void _setPropertyType(Class<TR> clazz) {
        this.propType = clazz;
    }

    @Override
    public <TR> PropTypeColumn<TR> asAnyType(Class<TR> clazz) {
        _setPropertyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return sqlSelectExpression.getEntitySQLContext();
    }

    @Override
    public SQLSelectAsExpression as(String propertyAlias) {
        return sqlSelectExpression.as(propertyAlias);
    }
}
