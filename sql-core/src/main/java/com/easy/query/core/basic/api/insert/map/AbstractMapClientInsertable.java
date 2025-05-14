package com.easy.query.core.basic.api.insert.map;

import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.builder.impl.ConfigurerImpl;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;
import com.easy.query.core.expression.parser.core.base.impl.ColumnConfigurerImpl;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurerImpl;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * create time 2023/10/2 12:01
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractMapClientInsertable implements MapClientInsertable<Map<String,Object>> {
    protected final List<Map<String,Object>> maps;
    protected final EntityMetadata entityMetadata;
    protected final EntityInsertExpressionBuilder entityInsertExpressionBuilder;
    protected final EntityTableExpressionBuilder entityTableExpressionBuilder;

    public AbstractMapClientInsertable(EntityInsertExpressionBuilder entityInsertExpressionBuilder) {
        this.entityInsertExpressionBuilder = entityInsertExpressionBuilder;
        this.maps = new ArrayList<>();
        QueryRuntimeContext runtimeContext = entityInsertExpressionBuilder.getRuntimeContext();
        entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(Map.class);

        this.entityTableExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.NONE, runtimeContext);
        this.entityInsertExpressionBuilder.addSQLEntityTableExpression(entityTableExpressionBuilder);
    }

    @Override
    public EntityInsertExpressionBuilder getEntityInsertExpressionBuilder() {
        return entityInsertExpressionBuilder;
    }

    @Override
    public MapClientInsertable<Map<String,Object>> insert(Map<String,Object> map) {
        if (map != null) {
            maps.add(map);
        }
        return this;
    }

    @Override
    public MapClientInsertable<Map<String, Object>> columnConfigure(SQLActionExpression1<ColumnConfigurer<Map<String, Object>>> columnConfigureExpression) {
        columnConfigureExpression.apply(new ColumnConfigurerImpl<>(entityTableExpressionBuilder.getEntityTable(), new ConfigurerImpl(entityInsertExpressionBuilder)));
        return this;
    }

    @Override
    public long executeRows(boolean fillAutoIncrement) {
        if (!maps.isEmpty()) {
            EntityExpressionExecutor entityExpressionExecutor = entityInsertExpressionBuilder.getRuntimeContext().getEntityExpressionExecutor();
            return entityExpressionExecutor.insert(ExecutorContext.create(entityInsertExpressionBuilder.getExpressionContext(), false, ExecuteMethodEnum.INSERT), maps, entityInsertExpressionBuilder, fillAutoIncrement);
        }

        return 0;
    }

    @Override
    public MapClientInsertable<Map<String,Object>> asTable(Function<String, String> tableNameAs) {
        entityInsertExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public MapClientInsertable<Map<String,Object>> asSchema(Function<String, String> schemaAs) {
        entityInsertExpressionBuilder.getRecentlyTable().setSchemaAs(schemaAs);
        return this;
    }

    @Override
    public MapClientInsertable<Map<String,Object>> asAlias(String alias) {
        entityInsertExpressionBuilder.getRecentlyTable().asAlias(alias);
        return this;
    }

    @Override
    public MapClientInsertable<Map<String,Object>> asTableLink(Function<String, String> linkAs) {
        entityInsertExpressionBuilder.getRecentlyTable().setTableLinkAs(linkAs);
        return this;
    }

    @Override
    public MapClientInsertable<Map<String, Object>> asTableSegment(BiFunction<String, String, String> segmentAs) {
        entityInsertExpressionBuilder.getRecentlyTable().setTableSegmentAs(segmentAs);
        return this;
    }

    @Override
    public MapClientInsertable<Map<String,Object>> noInterceptor() {
        entityInsertExpressionBuilder.getExpressionContext().noInterceptor();
        return this;
    }

    @Override
    public MapClientInsertable<Map<String,Object>> useInterceptor(String name) {
        entityInsertExpressionBuilder.getExpressionContext().useInterceptor(name);
        return this;
    }

    @Override
    public MapClientInsertable<Map<String,Object>> noInterceptor(String name) {
        entityInsertExpressionBuilder.getExpressionContext().noInterceptor(name);
        return this;
    }
    @Override
    public MapClientInsertable<Map<String,Object>> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        if(configurer!=null){
            configurer.apply(new ContextConfigurerImpl(entityInsertExpressionBuilder.getExpressionContext()));
        }
        return this;
    }

    @Override
    public MapClientInsertable<Map<String,Object>> useInterceptor() {
        entityInsertExpressionBuilder.getExpressionContext().useInterceptor();
        return this;
    }

    @Override
    public MapClientInsertable<Map<String,Object>> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        if (condition) {
            entityInsertExpressionBuilder.getExpressionContext().useSQLStrategy(sqlStrategy);
        }
        return this;
    }

    @Override
    public MapClientInsertable<Map<String,Object>> onDuplicateKeyIgnore() {
        insertOrIgnoreBehavior();
        return this;
    }

    private void insertOrIgnoreBehavior() {
        entityInsertExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE);
        entityInsertExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_IGNORE);
    }

    private void insertOrUpdateBehavior() {
        entityInsertExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_IGNORE);
        entityInsertExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE);
    }

    @Override
    public MapClientInsertable<Map<String,Object>> batch(boolean use) {
        if (use) {
            entityInsertExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.EXECUTE_NO_BATCH);
            entityInsertExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.EXECUTE_BATCH);
        } else {
            entityInsertExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.EXECUTE_BATCH);
            entityInsertExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.EXECUTE_NO_BATCH);
        }
        return this;
    }

    @Override
    public String toSQL(Map<String,Object> entity) {
        return toSQL(entity, DefaultToSQLContext.defaultToSQLContext(entityInsertExpressionBuilder.getExpressionContext().getTableContext()));
    }

    @Override
    public String toSQL(Map<String,Object> entity, ToSQLContext toSQLContext) {
        return toSQLWithParam(entity, toSQLContext);
    }

    private String toSQLWithParam(Map<String,Object> entity, ToSQLContext toSQLContext) {
        return entityInsertExpressionBuilder.toExpression(entity).toSQL(toSQLContext);
    }

}
