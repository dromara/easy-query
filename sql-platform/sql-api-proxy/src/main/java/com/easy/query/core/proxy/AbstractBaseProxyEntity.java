package com.easy.query.core.proxy;

import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.EmptyQueryRuntimeContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleEntitySQLTableOwner;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.columns.SQLAnyColumn;
import com.easy.query.core.proxy.columns.SQLBooleanColumn;
import com.easy.query.core.proxy.columns.SQLDateTimeColumn;
import com.easy.query.core.proxy.columns.SQLManyQueryable;
import com.easy.query.core.proxy.columns.SQLNavigateColumn;
import com.easy.query.core.proxy.columns.SQLNumberColumn;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.columns.SQLStringColumn;
import com.easy.query.core.proxy.columns.impl.EasySQLManyQueryable;
import com.easy.query.core.proxy.columns.impl.EasySQLQueryable;
import com.easy.query.core.proxy.columns.impl.EmptySQLManyQueryable;
import com.easy.query.core.proxy.columns.impl.EmptySQLQueryable;
import com.easy.query.core.proxy.columns.impl.SQLAnyColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLBooleanColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLDateTimeColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLNavigateColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLNumberColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLStringColumnImpl;
import com.easy.query.core.proxy.columns.types.SQLAnyTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLBigDecimalTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLBooleanTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLByteTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLDateTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLDoubleTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLFloatTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLIntegerTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLLocalDateTimeTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLLocalDateTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLLocalTimeTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLLongTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLShortTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLTimeTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLTimestampTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLUUIDTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLUtilDateTypeColumn;
import com.easy.query.core.proxy.columns.types.impl.SQLAnyTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLBigDecimalTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLBooleanTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLByteTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLDateTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLDoubleTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLFloatTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLIntegerTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLLocalDateTimeTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLLocalDateTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLLocalTimeTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLLongTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLShortTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLStringTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLTimeTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLTimestampTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLUUIDTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLUtilDateTypeColumnImpl;
import com.easy.query.core.proxy.core.ColumnSelectSQLContext;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyRelationalUtil;

import java.util.Objects;

/**
 * create time 2023/6/25 12:39
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractBaseProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> implements ProxyEntity<TProxy, TEntity>, EntitySQLContextAvailable {

    protected TableAvailable table;
    protected EntitySQLContext entitySQLContext = new ColumnSelectSQLContext();

    private String propValue;

    @Override
    public String getNavValue() {
        return propValue;
    }

    @Override
    public void setNavValue(String val) {
        this.propValue = val;
    }

    @Override
    public @Nullable TableAvailable getTable() {
        return table;
    }

    @Override
    public TableAvailable getTableOrNull() {
        return table;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        Objects.requireNonNull(entitySQLContext, "cant found entitySQLContext in sql context");
        return entitySQLContext;
    }


    @Override
    public TProxy create(TableAvailable table, EntitySQLContext entitySQLContext) {
        this.table = table;
        this.entitySQLContext = entitySQLContext;
        return EasyObjectUtil.typeCastNullable(this);
    }

    @Deprecated
    protected <TProperty> SQLColumn<TProxy, TProperty> get(String property) {
        return get(property, null);
    }

    protected <TProperty> SQLColumn<TProxy, TProperty> get(String property, @Nullable Class<TProperty> propType) {
        SQLColumn<TProxy, TProperty> column = new SQLColumnImpl<>(entitySQLContext, table, property, propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TProperty> SQLDateTimeColumn<TProxy, TProperty> getDateTimeColumn(String property, @Nullable Class<TProperty> propType) {
        SQLDateTimeColumn<TProxy, TProperty> column = new SQLDateTimeColumnImpl<>(entitySQLContext, table, property, propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TProperty> SQLNumberColumn<TProxy, TProperty> getNumberColumn(String property, @Nullable Class<TProperty> propType) {
        SQLNumberColumn<TProxy, TProperty> column = new SQLNumberColumnImpl<>(entitySQLContext, table, property, propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TProperty> SQLStringColumn<TProxy, TProperty> getStringColumn(String property, @Nullable Class<TProperty> propType) {
        SQLStringColumn<TProxy, TProperty> column = new SQLStringColumnImpl<>(entitySQLContext, table, property, propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TProperty> SQLBooleanColumn<TProxy, TProperty> getBooleanColumn(String property, @Nullable Class<TProperty> propType) {
        SQLBooleanColumn<TProxy, TProperty> column = new SQLBooleanColumnImpl<>(entitySQLContext, table, property, propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TProperty> SQLAnyColumn<TProxy, TProperty> getAnyColumn(String property, @Nullable Class<TProperty> propType) {
        SQLAnyColumn<TProxy, TProperty> column = new SQLAnyColumnImpl<>(entitySQLContext, table, property, propType);
        column._setProxy(castChain());
        return column;
    }


    protected <TProperty> SQLAnyTypeColumn<TProxy, TProperty> getAnyTypeColumn(String property, @Nullable Class<TProperty> propType) {
        SQLAnyTypeColumn<TProxy, TProperty> column = new SQLAnyTypeColumnImpl<>(entitySQLContext, table, property, propType);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLBigDecimalTypeColumn<TProxy> getBigDecimalTypeColumn(String property) {
        SQLBigDecimalTypeColumn<TProxy> column = new SQLBigDecimalTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLBooleanTypeColumn<TProxy> getBooleanTypeColumn(String property) {
        SQLBooleanTypeColumn<TProxy> column = new SQLBooleanTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLByteTypeColumn<TProxy> getByteTypeColumn(String property) {
        SQLByteTypeColumn<TProxy> column = new SQLByteTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLDateTypeColumn<TProxy> getSQLDateTypeColumn(String property) {
        SQLDateTypeColumn<TProxy> column = new SQLDateTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLDoubleTypeColumn<TProxy> getDoubleTypeColumn(String property) {
        SQLDoubleTypeColumn<TProxy> column = new SQLDoubleTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLFloatTypeColumn<TProxy> getFloatTypeColumn(String property) {
        SQLFloatTypeColumn<TProxy> column = new SQLFloatTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLIntegerTypeColumn<TProxy> getIntegerTypeColumn(String property) {
        SQLIntegerTypeColumn<TProxy> column = new SQLIntegerTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLLocalDateTimeTypeColumn<TProxy> getLocalDateTimeTypeColumn(String property) {
        SQLLocalDateTimeTypeColumn<TProxy> column = new SQLLocalDateTimeTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLLocalDateTypeColumn<TProxy> getLocalDateTypeColumn(String property) {
        SQLLocalDateTypeColumn<TProxy> column = new SQLLocalDateTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLLocalTimeTypeColumn<TProxy> getLocalTimeTypeColumn(String property) {
        SQLLocalTimeTypeColumn<TProxy> column = new SQLLocalTimeTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLLongTypeColumn<TProxy> getLongTypeColumn(String property) {
        SQLLongTypeColumn<TProxy> column = new SQLLongTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLShortTypeColumn<TProxy> getShortTypeColumn(String property) {
        SQLShortTypeColumn<TProxy> column = new SQLShortTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLStringTypeColumn<TProxy> getStringTypeColumn(String property) {
        SQLStringTypeColumn<TProxy> column = new SQLStringTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLTimestampTypeColumn<TProxy> getTimestampTypeColumn(String property) {
        SQLTimestampTypeColumn<TProxy> column = new SQLTimestampTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLTimeTypeColumn<TProxy> getTimeTypeColumn(String property) {
        SQLTimeTypeColumn<TProxy> column = new SQLTimeTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLUtilDateTypeColumn<TProxy> getUtilDateTypeColumn(String property) {
        SQLUtilDateTypeColumn<TProxy> column = new SQLUtilDateTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLUUIDTypeColumn<TProxy> getUUIDTypeColumn(String property) {
        SQLUUIDTypeColumn<TProxy> column = new SQLUUIDTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }


    @Deprecated
    protected <TProperty> SQLNavigateColumn<TProxy, TProperty> getNavigate(String property, Class<TProperty> propType) {
        SQLNavigateColumnImpl<TProxy, TProperty> column = new SQLNavigateColumnImpl<>(this.getEntitySQLContext(), table, property, propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TPropertyProxy getNavigate(String property, TPropertyProxy propertyProxy) {
        Objects.requireNonNull(this.entitySQLContext, "entitySQLContext is null");
        EntityExpressionBuilder entityExpressionBuilder = entitySQLContext.getEntityExpressionBuilder();
        //vo
        if (entityExpressionBuilder == null || entitySQLContext.methodIsInclude() || entityExpressionBuilder.getRuntimeContext() instanceof EmptyQueryRuntimeContext) {
            TPropertyProxy tPropertyProxy = propertyProxy.create(getTable(), this.getEntitySQLContext());
            tPropertyProxy.setNavValue(getFullNavValue(property));
            return tPropertyProxy;
        } else {
            TableAvailable leftTable = getTable();
            if (leftTable == null) {
                throw new EasyQueryInvalidOperationException(String.format("getNavigate %s cant not found table", property));
            }
            String fullName = getFullNavValue(property);
            TableAvailable relationTable = EasyRelationalUtil.getRelationTable(entityExpressionBuilder, leftTable, property,fullName);
            TPropertyProxy tPropertyProxy = propertyProxy.create(relationTable, this.getEntitySQLContext());
            tPropertyProxy.setNavValue(fullName);
            return tPropertyProxy;
        }
    }

    protected <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> SQLQueryable<TPropertyProxy, TProperty> getNavigates(String property, TPropertyProxy propertyProxy) {
        Objects.requireNonNull(this.entitySQLContext, "entitySQLContext is null");
        QueryRuntimeContext runtimeContext = this.entitySQLContext.getRuntimeContext();
        EntityExpressionBuilder entityExpressionBuilder = entitySQLContext.getEntityExpressionBuilder();
        if (entityExpressionBuilder == null || runtimeContext instanceof EmptyQueryRuntimeContext) {
            propertyProxy.setNavValue(getFullNavValue(property));
            return new EmptySQLQueryable<>(this.getEntitySQLContext(), propertyProxy);
        } else {
            TableAvailable leftTable = getTable();
            if (leftTable == null) {
                throw new EasyQueryInvalidOperationException(String.format("getNavigate %s cant not found table", property));
            }
            NavigateMetadata navigateMetadata = leftTable.getEntityMetadata().getNavigateNotNull(property);
            ClientQueryable<TProperty> clientQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(propertyProxy.getEntityClass(), runtimeContext);
            if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
                ClientQueryable<?> mappingQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(navigateMetadata.getMappingClass(), runtimeContext);
                clientQueryable.where(x -> {
                    x.and(() -> {
                        ClientQueryable<?> subMappingQueryable = mappingQueryable.where(m -> {
                            m.multiEq(true, x, navigateMetadata.getTargetMappingProperties(), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext));
                            m.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getSelfMappingProperties(), navigateMetadata.getSelfPropertiesOrPrimary());
                            navigateMetadata.predicateMappingClassFilterApply(m);
                        }).limit(1);
                        x.exists(subMappingQueryable);
                    });
                });
            } else {
                clientQueryable.where(t -> {
                    t.and(() -> {
                        t.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getSelfPropertiesOrPrimary());
                        navigateMetadata.predicateFilterApply(t);
                    });
                });
            }
            EasyEntityQueryable<TPropertyProxy, TProperty> queryable = new EasyEntityQueryable<>(propertyProxy, clientQueryable);
            queryable.get1Proxy().setNavValue(getFullNavValue(property));
            return new EasySQLQueryable<>(this.getEntitySQLContext(), queryable, leftTable);
        }
    }

    protected <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> SQLManyQueryable<TProxy, TPropertyProxy, TProperty> getNavigateMany(String property, TPropertyProxy propertyProxy) {
        Objects.requireNonNull(this.entitySQLContext, "entitySQLContext is null");
        EntityExpressionBuilder entityExpressionBuilder = entitySQLContext.getEntityExpressionBuilder();
        QueryRuntimeContext runtimeContext = this.entitySQLContext.getRuntimeContext();
        if (entityExpressionBuilder == null || runtimeContext instanceof EmptyQueryRuntimeContext) {
            propertyProxy.setNavValue(getFullNavValue(property));
            SQLManyQueryable<TProxy, TPropertyProxy, TProperty> query = new EmptySQLManyQueryable<>(this.getEntitySQLContext(), propertyProxy);
            query._setProxy(castChain());
            return query;
        } else {
            TableAvailable leftTable = getTable();
            if (leftTable == null) {
                throw new EasyQueryInvalidOperationException(String.format("getNavigate %s cant not found table", property));
            }
            NavigateMetadata navigateMetadata = leftTable.getEntityMetadata().getNavigateNotNull(property);
            ClientQueryable<TProperty> clientQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(propertyProxy.getEntityClass(), runtimeContext);
            if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
                ClientQueryable<?> mappingQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(navigateMetadata.getMappingClass(), runtimeContext);
                clientQueryable.where(x -> {
                    x.and(() -> {
                        ClientQueryable<?> subMappingQueryable = mappingQueryable.where(m -> {
                            m.multiEq(true,x, navigateMetadata.getTargetMappingProperties(), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext));
                            m.multiEq(true,new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getSelfMappingProperties(), navigateMetadata.getSelfPropertiesOrPrimary());
                            navigateMetadata.predicateMappingClassFilterApply(m);
                        }).limit(1);
                        x.exists(subMappingQueryable);
                    });
                });
            } else {
                clientQueryable.where(t -> {
                    t.and(() -> {
                        t.multiEq(true,new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getSelfPropertiesOrPrimary());
                        navigateMetadata.predicateFilterApply(t);
                    });
                });
            }
            EasyEntityQueryable<TPropertyProxy, TProperty> queryable = new EasyEntityQueryable<>(propertyProxy, clientQueryable);
            queryable.get1Proxy().setNavValue(getFullNavValue(property));
            EasySQLManyQueryable<TProxy, TPropertyProxy, TProperty> query = new EasySQLManyQueryable<>(this.getEntitySQLContext(), queryable, leftTable);
            query._setProxy(castChain());
            return query;
        }
    }

    private String getFullNavValue(String navValue) {
        String parentNavValue = getNavValue();
        if (parentNavValue == null) {
            return navValue;
        }
        return parentNavValue + "." + navValue;
    }

    protected TProxy castChain() {
        return (TProxy) this;
    }
}
