package com.easy.query.cache.core.impl;

import com.easy.query.cache.core.CacheEntity;
import com.easy.query.cache.core.EasyCacheOption;
import com.easy.query.cache.core.CacheRuntimeContext;
import com.easy.query.cache.core.EasyCacheManager;
import com.easy.query.cache.core.Pair;
import com.easy.query.cache.core.annotation.CacheEntitySchema;
import com.easy.query.cache.core.base.CacheHashKeyFactory;
import com.easy.query.cache.core.base.CachePredicate;
import com.easy.query.cache.core.queryable.CacheQueryable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;
import com.easy.query.core.util.EasySQLUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * create time 2023/5/16 10:19
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractBaseCacheQueryable<TEntity extends CacheEntity> implements CacheQueryable, RuntimeContextAvailable {
    protected final EasyCacheOption easyCacheOption;
    protected final EasyCacheManager easyCacheManager;
    protected final EasyQueryClient easyQueryClient;
    protected final ServiceProvider serviceProvider;
    protected final CacheHashKeyFactory cacheHashKeyFactory;
    protected final Class<TEntity> entityClass;
    protected final CacheEntitySchema cacheEntitySchema;
    protected final QueryRuntimeContext runtimeContext;
    protected final EntityMetadata entityMetadata;
    protected final ClientQueryable<TEntity> queryable;
    protected List<Function<ClientQueryable<TEntity>, ClientQueryable<TEntity>>> functions;

    public AbstractBaseCacheQueryable(CacheRuntimeContext cacheRuntimeContext, Class<TEntity> entityClass) {

        this.serviceProvider = cacheRuntimeContext.getServiceProvider();
        this.easyCacheOption = cacheRuntimeContext.getEasyCacheOption();
        this.easyCacheManager = cacheRuntimeContext.getEasyCacheManager();
        this.easyQueryClient = cacheRuntimeContext.getEasyQueryClient();
        this.cacheHashKeyFactory = serviceProvider.getService(CacheHashKeyFactory.class);
        this.runtimeContext = this.easyQueryClient.getRuntimeContext();
        this.entityClass = entityClass;
        this.entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(entityClass);
        CacheEntitySchema cacheEntitySchema = EasyClassUtil.getAnnotation(entityClass, CacheEntitySchema.class);
        if (cacheEntitySchema == null) {
            throw new IllegalArgumentException(entityClass.getSimpleName() + " plz add annotation @CacheEntitySchema");
        }
        this.cacheEntitySchema = cacheEntitySchema;
        this.queryable = easyQueryClient.queryable(entityClass)
                .asNoTracking();
        this.functions = new ArrayList<>();
    }


    protected ClientQueryable<TEntity> getEndEntityQueryable(ClientQueryable<TEntity> queryable) {
        ClientQueryable<TEntity> cloneQueryable = queryable.cloneQueryable();
        if (EasyCollectionUtil.isNotEmpty(functions)) {
            for (Function<ClientQueryable<TEntity>, ClientQueryable<TEntity>> function : functions) {
                cloneQueryable = function.apply(cloneQueryable);
            }
        }
        return cloneQueryable;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public String getEntityKey() {
        return this.easyCacheOption.getEntityKey(entityClass);
    }

    protected Pair<String, TEntity> getKeyAndEntity(TEntity entity) {
        return new Pair<>(getKey(entity), entity);
    }

    protected String getKey(TEntity entity) {
        Object val = entityMetadata.getColumnNotNull(cacheEntitySchema.value()).getGetterCaller().apply(entity);
        if (val == null) {
            throw new IllegalArgumentException(String.format("CacheEntitySchema.value [%s] is null", cacheEntitySchema.value()));
        }
        return val.toString();
    }

    protected String getIdProperty() {
        return cacheEntitySchema.value();
    }


    protected Stream<TEntity> filterResult(Stream<TEntity> source) {
        return source;
    }


    protected String getQueryableKey(ClientQueryable<TEntity> entityQueryable) {


        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = entityQueryable.getSQLEntityExpressionBuilder();

        SQLExpressionInvokeFactory easyQueryLambdaFactory = getRuntimeContext().getSQLExpressionInvokeFactory();
        TableAvailable entityTable = sqlEntityExpressionBuilder.getFromTable().getEntityTable();
        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(true);
        WherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createWherePredicate(entityTable, sqlEntityExpressionBuilder, andPredicateSegment);
        ExpressionContext expressionContext = sqlEntityExpressionBuilder.getExpressionContext();
        EasySQLExpressionUtil.invokeInterceptors(entityQueryable.queryEntityMetadata(), sqlEntityExpressionBuilder, expressionContext, sqlPredicate);
        if (EasySQLSegmentUtil.isEmpty(andPredicateSegment)) {
            return cacheHashKeyFactory.getKey(null);
        }
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(expressionContext.getTableContext());
        String sql = andPredicateSegment.toSQL(toSQLContext);
        List<SQLParameter> parameters = toSQLContext.getParameters();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("sql", sql);
        map.put("parameters", EasySQLUtil.sqlParameterToString(parameters));
        return cacheHashKeyFactory.getKey(map);
    }

}
