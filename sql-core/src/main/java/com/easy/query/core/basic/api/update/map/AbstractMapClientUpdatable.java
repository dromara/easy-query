package com.easy.query.core.basic.api.update.map;

import com.easy.query.core.basic.api.internal.AbstractSQLExecuteRows;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.builder.impl.ConfigurerImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;
import com.easy.query.core.expression.parser.core.base.impl.ColumnConfigurerImpl;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.MapUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurerImpl;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * create time 2023/10/3 12:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractMapClientUpdatable extends AbstractSQLExecuteRows<MapClientUpdatable<Map<String, Object>>> implements MapClientUpdatable<Map<String, Object>> {
    protected final List<Map<String, Object>> entities;
    protected final EntityTableExpressionBuilder table;
    protected final EntityMetadata entityMetadata;
    protected final MapUpdateExpressionBuilder mapUpdateExpressionBuilder;

    public AbstractMapClientUpdatable(Collection<Map<String, Object>> entities, MapUpdateExpressionBuilder mapUpdateExpressionBuilder) {
        super(mapUpdateExpressionBuilder);
        if (entities == null || entities.isEmpty()) {
            throw new EasyQueryException("不支持空对象的update");
        }
        this.entities = new ArrayList<>(entities);

        this.mapUpdateExpressionBuilder = mapUpdateExpressionBuilder;
        QueryRuntimeContext runtimeContext = this.mapUpdateExpressionBuilder.getRuntimeContext();
        entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(Map.class);
        entityMetadata.checkTable();
        table = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.NONE, runtimeContext);
        this.mapUpdateExpressionBuilder.addSQLEntityTableExpression(table);
    }

    @Override
    public long executeRows() {
        if (EasyCollectionUtil.isNotEmpty(entities)) {
            EntityExpressionExecutor entityExpressionExecutor = mapUpdateExpressionBuilder.getRuntimeContext().getEntityExpressionExecutor();
            return entityExpressionExecutor.executeRows(ExecutorContext.create(mapUpdateExpressionBuilder.getExpressionContext(), false, ExecuteMethodEnum.UPDATE), mapUpdateExpressionBuilder, entities);
        }
        return 0;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        if (condition) {
            mapUpdateExpressionBuilder.getExpressionContext().useSQLStrategy(sqlStrategy);
        }
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> asTable(Function<String, String> tableNameAs) {
        mapUpdateExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> asSchema(Function<String, String> schemaAs) {
        mapUpdateExpressionBuilder.getRecentlyTable().setSchemaAs(schemaAs);
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> asAlias(String alias) {
        mapUpdateExpressionBuilder.getRecentlyTable().asAlias(alias);
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> asTableLink(Function<String, String> linkAs) {
        mapUpdateExpressionBuilder.getRecentlyTable().setTableLinkAs(linkAs);
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> configure(SQLExpression1<ContextConfigurer> configurer) {
        if (configurer != null) {
            configurer.apply(new ContextConfigurerImpl(mapUpdateExpressionBuilder.getExpressionContext()));
        }
        return this;
    }

    @Override
    public MapUpdateExpressionBuilder getUpdateExpressionBuilder() {
        return mapUpdateExpressionBuilder;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> whereColumns(String... columnNames) {
        mapUpdateExpressionBuilder.addWhereColumns(columnNames);
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> columnConfigure(SQLExpression1<ColumnConfigurer<Map<String, Object>>> columnConfigureExpression) {
        ColumnConfigurerImpl<Map<String, Object>> columnConfigurer = new ColumnConfigurerImpl<>(table.getEntityTable(), new ConfigurerImpl(this.mapUpdateExpressionBuilder));
        columnConfigureExpression.apply(columnConfigurer);
        return this;
    }
}
