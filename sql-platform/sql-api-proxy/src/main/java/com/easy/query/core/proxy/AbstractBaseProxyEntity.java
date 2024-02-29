package com.easy.query.core.proxy;

import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.RelationEntityTableAvailable;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleEntitySQLTableOwner;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnWithColumnPredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.TableExpressionBuilder;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.columns.SQLAnyColumn;
import com.easy.query.core.proxy.columns.SQLBooleanColumn;
import com.easy.query.core.proxy.columns.SQLDateTimeColumn;
import com.easy.query.core.proxy.columns.SQLNavigateColumn;
import com.easy.query.core.proxy.columns.SQLNumberColumn;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.columns.SQLStringColumn;
import com.easy.query.core.proxy.columns.impl.EasySQLQueryable;
import com.easy.query.core.proxy.columns.impl.EmptySQLQueryable;
import com.easy.query.core.proxy.columns.impl.SQLAnyColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLBooleanColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLDateTimeColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLNavigateColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLNumberColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLStringColumnImpl;
import com.easy.query.core.proxy.core.ColumnSelectSQLContext;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;
import com.easy.query.core.util.EasyObjectUtil;

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
        this.propValue=val;
    }

    @Override
    public TableAvailable getTable() {
        Objects.requireNonNull(table, "cant found table in sql context");
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
        return new SQLColumnImpl<>(entitySQLContext, table, property, propType);
    }

    protected <TProperty> SQLDateTimeColumn<TProxy, TProperty> getDateTimeColumn(String property, @Nullable Class<TProperty> propType) {
        return new SQLDateTimeColumnImpl<>(entitySQLContext, table, property, propType);
    }

    protected <TProperty> SQLNumberColumn<TProxy, TProperty> getNumberColumn(String property, @Nullable Class<TProperty> propType) {
        return new SQLNumberColumnImpl<>(entitySQLContext, table, property, propType);
    }

    protected <TProperty> SQLStringColumn<TProxy, TProperty> getStringColumn(String property, @Nullable Class<TProperty> propType) {
        return new SQLStringColumnImpl<>(entitySQLContext, table, property, propType);
    }

    protected <TProperty> SQLBooleanColumn<TProxy, TProperty> getBooleanColumn(String property, @Nullable Class<TProperty> propType) {
        return new SQLBooleanColumnImpl<>(entitySQLContext, table, property, propType);
    }

    protected <TProperty> SQLAnyColumn<TProxy, TProperty> getAnyColumn(String property, @Nullable Class<TProperty> propType) {
        return new SQLAnyColumnImpl<>(entitySQLContext, table, property, propType);
    }
    @Deprecated

    protected <TProperty> SQLNavigateColumn<TProxy, TProperty> getNavigate(String property, Class<TProperty> propType) {
        return new SQLNavigateColumnImpl<>(entitySQLContext, table, property, propType);
    }

    protected <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TPropertyProxy getNavigate(String property, TPropertyProxy propertyProxy) {
        Objects.requireNonNull(this.entitySQLContext, "entitySQLContext is null");
        EntityExpressionBuilder entityExpressionBuilder = entitySQLContext.getEntityExpressionBuilder();
        //vo
        if(entityExpressionBuilder == null || entitySQLContext.methodIsInclude()){
            TPropertyProxy tPropertyProxy = propertyProxy.create(null, this.getEntitySQLContext());
            tPropertyProxy.setNavValue(property);
            return tPropertyProxy;
        }else{

            QueryRuntimeContext runtimeContext = this.entitySQLContext.getRuntimeContext();
            EntityTableExpressionBuilder entityTableExpressionBuilder = entityExpressionBuilder.addRelationEntityTableExpression(new RelationTableKey(table.getEntityClass(), propertyProxy.getEntityClass()), key -> {
                EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(propertyProxy.getEntityClass());
                TableAvailable leftTable = getTable();
                RelationEntityTableAvailable rightTable = new RelationEntityTableAvailable(key,leftTable, entityMetadata, false);
                TableExpressionBuilder tableExpressionBuilder = new TableExpressionBuilder(rightTable, MultiTableTypeEnum.LEFT_JOIN, runtimeContext);
                AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
                NavigateMetadata navigateMetadata = leftTable.getEntityMetadata().getNavigateNotNull(property);
                andPredicateSegment.setPredicate(new ColumnWithColumnPredicate(leftTable, navigateMetadata.getSelfPropertyOrPrimary(), rightTable, navigateMetadata.getTargetPropertyOrPrimary(runtimeContext), SQLPredicateCompareEnum.EQ, runtimeContext));
                tableExpressionBuilder.getOn().addPredicateSegment(andPredicateSegment);
                return tableExpressionBuilder;
            });
            TPropertyProxy tPropertyProxy = propertyProxy.create(entityTableExpressionBuilder.getEntityTable(), this.entitySQLContext);
            tPropertyProxy.setNavValue(getFullNavValue(property));
            return tPropertyProxy;
        }
    }

    protected <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> SQLQueryable<TPropertyProxy, TProperty> getNavigates(String property, TPropertyProxy propertyProxy) {
        Objects.requireNonNull(this.entitySQLContext, "entitySQLContext is null");
        EntityExpressionBuilder entityExpressionBuilder = entitySQLContext.getEntityExpressionBuilder();
        if(entityExpressionBuilder==null){
            return new EmptySQLQueryable<>(this.entitySQLContext,null,property);
        }else{
            QueryRuntimeContext runtimeContext = this.entitySQLContext.getRuntimeContext();
            TableAvailable leftTable = getTable();
            NavigateMetadata navigateMetadata = leftTable.getEntityMetadata().getNavigateNotNull(property);
            ClientQueryable<TProperty> clientQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(propertyProxy.getEntityClass(), runtimeContext)                    ;
            if(navigateMetadata.getRelationType()== RelationTypeEnum.ManyToMany){
                ClientQueryable<?> mappingQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(navigateMetadata.getMappingClass(), runtimeContext);
                clientQueryable.where(x->{
                    x.and(()->{
                        ClientQueryable<?> subMappingQueryable = mappingQueryable.where(m -> {
                            m.eq(x, navigateMetadata.getTargetMappingProperty(), navigateMetadata.getTargetPropertyOrPrimary(runtimeContext));
                            m.eq(new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getSelfMappingProperty(), navigateMetadata.getSelfPropertyOrPrimary());
                        }).limit(1);
                        x.exists(subMappingQueryable);
                    });
                });
            }else{
                clientQueryable.where(t -> t.eq(new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getTargetPropertyOrPrimary(runtimeContext), navigateMetadata.getSelfPropertyOrPrimary()));
            }
            EasyEntityQueryable<TPropertyProxy, TProperty> queryable = new EasyEntityQueryable<>(propertyProxy, clientQueryable);
            queryable.get1Proxy().setNavValue(getFullNavValue(property));
            return new EasySQLQueryable<>(this.entitySQLContext, queryable,leftTable);
        }
    }
    private String getFullNavValue(String navValue){
        String parentNavValue = getNavValue();
        if(parentNavValue==null){
            return navValue;
        }
        return parentNavValue+"."+navValue;
    }
}
