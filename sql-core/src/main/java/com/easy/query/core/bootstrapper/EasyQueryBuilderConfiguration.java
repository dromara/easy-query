package com.easy.query.core.bootstrapper;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.api.client.DefaultEasyQueryClient;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.api.def.DefaultSQLClientApiFactory;
import com.easy.query.core.api.dynamic.executor.query.DefaultWhereObjectQueryExecutor;
import com.easy.query.core.api.dynamic.executor.query.WhereObjectQueryExecutor;
import com.easy.query.core.api.dynamic.executor.search.DefaultEasySearchConfigurationProvider;
import com.easy.query.core.api.dynamic.executor.search.EasySearchConfigurationProvider;
import com.easy.query.core.api.dynamic.executor.search.executor.DefaultEasySearchQueryExecutor;
import com.easy.query.core.api.dynamic.executor.search.executor.EasySearchParamParser;
import com.easy.query.core.api.dynamic.executor.search.executor.EasySearchQueryExecutor;
import com.easy.query.core.api.dynamic.executor.search.meta.DefaultEasySearchMetaDataManager;
import com.easy.query.core.api.dynamic.executor.search.meta.EasySearchMetaDataManager;
import com.easy.query.core.api.dynamic.executor.sort.DefaultObjectSortQueryExecutor;
import com.easy.query.core.api.dynamic.executor.sort.ObjectSortQueryExecutor;
import com.easy.query.core.basic.api.cte.CteTableNamedProvider;
import com.easy.query.core.basic.api.cte.DefaultCteTableNamedProvider;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.api.database.DefaultDatabaseCodeFirst;
import com.easy.query.core.basic.entity.ColumnEntityMappingRule;
import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.entity.PropertyFirstEntityMappingRule;
import com.easy.query.core.basic.extension.formater.DefaultSQLParameterPrintFormat;
import com.easy.query.core.basic.extension.formater.SQLParameterPrintFormat;
import com.easy.query.core.basic.extension.listener.EmptyJdbcExecutorListener;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.extension.print.DefaultJdbcSQLPrinter;
import com.easy.query.core.basic.extension.print.JdbcSQLPrinter;
import com.easy.query.core.basic.extension.track.DefaultTrackManager;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.conn.EasyConnectionFactory;
import com.easy.query.core.basic.jdbc.conn.EasyDataSourceConnectionFactory;
import com.easy.query.core.basic.jdbc.conn.impl.DefaultConnectionManager;
import com.easy.query.core.basic.jdbc.conn.impl.DefaultEasyConnectionFactory;
import com.easy.query.core.basic.jdbc.conn.impl.DefaultEasyDataSourceConnectionFactory;
import com.easy.query.core.basic.jdbc.executor.DefaultEntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.pagination.DefaultEasyPageResultProvider;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.basic.thread.DefaultEasyShardingExecutorService;
import com.easy.query.core.basic.thread.ShardingExecutorService;
import com.easy.query.core.common.DefaultMapColumnNameChecker;
import com.easy.query.core.common.MapColumnNameChecker;
import com.easy.query.core.configuration.DefaultEasyInitConfiguration;
import com.easy.query.core.configuration.EasyInitConfiguration;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.configuration.EasyQueryOptionBuilder;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.bean.def.DefaultPropertyDescriptorMatcher;
import com.easy.query.core.configuration.bean.PropertyDescriptorMatcher;
import com.easy.query.core.configuration.column2mapkey.Column2MapKeyConversion;
import com.easy.query.core.configuration.column2mapkey.DefaultColumn2MapKeyConversion;
import com.easy.query.core.configuration.dialect.DefaultSQLKeyword;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.configuration.nameconversion.MapKeyNameConversion;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.DefaultMapKeyNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UnderlinedNameConversion;
import com.easy.query.core.context.DefaultEasyQueryRuntimeContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.datasource.DataSourceManager;
import com.easy.query.core.datasource.DataSourceUnitFactory;
import com.easy.query.core.datasource.DefaultDataSourceManager;
import com.easy.query.core.datasource.DefaultDataSourceUnitFactory;
import com.easy.query.core.datasource.replica.DefaultReplicaDataSourceManager;
import com.easy.query.core.exception.AssertExceptionFactory;
import com.easy.query.core.exception.DefaultAssertExceptionFactory;
import com.easy.query.core.expression.builder.core.AnyValueFilterFactory;
import com.easy.query.core.expression.builder.core.ValueFilterFactory;
import com.easy.query.core.expression.executor.parser.DefaultEasyPrepareParser;
import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
import com.easy.query.core.expression.executor.query.DefaultExecutionContextFactory;
import com.easy.query.core.expression.executor.query.ExecutionContextFactory;
import com.easy.query.core.expression.include.EasyIncludeProcessorFactory;
import com.easy.query.core.expression.include.IncludeProcessorFactory;
import com.easy.query.core.expression.parser.factory.DefaultSQLExpressionInvokeFactory;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.segment.factory.DefaultSQLSegmentFactory;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.factory.DefaultEasyExpressionBuilderFactory;
import com.easy.query.core.expression.sql.builder.factory.ExpressionBuilderFactory;
import com.easy.query.core.expression.sql.expression.factory.DefaultEasyExpressionFactory;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.sql.include.DefaultIncludeParserEngine;
import com.easy.query.core.expression.sql.include.DefaultIncludeProvider;
import com.easy.query.core.expression.sql.include.DefaultRelationNullValueValidator;
import com.easy.query.core.expression.sql.include.IncludeParserEngine;
import com.easy.query.core.expression.sql.include.IncludeProvider;
import com.easy.query.core.expression.sql.include.RelationNullValueValidator;
import com.easy.query.core.expression.sql.include.relation.DefaultRelationValueColumnMetadataFactory;
import com.easy.query.core.expression.sql.include.relation.DefaultRelationValueFactory;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadataFactory;
import com.easy.query.core.expression.sql.include.relation.RelationValueFactory;
import com.easy.query.core.extension.casewhen.DefaultSQLCaseWhenBuilderFactory;
import com.easy.query.core.extension.casewhen.SQLCaseWhenBuilderFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFuncImpl;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.inject.impl.ServiceCollectionImpl;
import com.easy.query.core.job.DefaultEasyTimeJobManager;
import com.easy.query.core.job.EasyTimeJobManager;
import com.easy.query.core.metadata.DefaultEntityMetadataManager;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.core.migration.DefaultDatabaseMigrationProvider;
import com.easy.query.core.migration.DefaultMigrationEntityParser;
import com.easy.query.core.migration.DefaultMigrationsSQLGenerator;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.migration.MigrationsSQLGenerator;
import com.easy.query.core.sharding.DefaultEasyQueryDataSource;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.sharding.comparer.JavaLanguageShardingComparer;
import com.easy.query.core.sharding.comparer.ShardingComparer;
import com.easy.query.core.sharding.manager.DefaultShardingQueryCountManager;
import com.easy.query.core.sharding.manager.ShardingQueryCountManager;
import com.easy.query.core.sharding.rewrite.DefaultRewriteContextFactory;
import com.easy.query.core.sharding.rewrite.RewriteContextFactory;
import com.easy.query.core.sharding.router.DefaultRouteContextFactory;
import com.easy.query.core.sharding.router.RouteContextFactory;
import com.easy.query.core.sharding.router.datasource.DataSourceRouter;
import com.easy.query.core.sharding.router.datasource.ShardingDataSourceRouter;
import com.easy.query.core.sharding.router.datasource.engine.DataSourceRouteEngine;
import com.easy.query.core.sharding.router.datasource.engine.DefaultDataSourceRouteEngine;
import com.easy.query.core.sharding.router.descriptor.DefaultRouteDescriptorFactor;
import com.easy.query.core.sharding.router.descriptor.RouteDescriptorFactory;
import com.easy.query.core.sharding.router.manager.DataSourceRouteManager;
import com.easy.query.core.sharding.router.manager.TableRouteManager;
import com.easy.query.core.sharding.router.manager.impl.DefaultDataSourceRouteManager;
import com.easy.query.core.sharding.router.manager.impl.DefaultTableRouteManager;
import com.easy.query.core.sharding.router.table.ShardingTableRouter;
import com.easy.query.core.sharding.router.table.TableRouter;
import com.easy.query.core.sharding.router.table.engine.DefaultTableRouteEngine;
import com.easy.query.core.sharding.router.table.engine.TableRouteEngine;
import com.easy.query.core.sql.DefaultJdbcSQLExecutor;
import com.easy.query.core.sql.JdbcSQLExecutor;

import javax.sql.DataSource;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * create time 2023/4/26 20:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryBuilderConfiguration {
    protected DataSource dataSource;
    private String name;
    protected final EasyQueryOptionBuilder easyQueryOptionBuilder = new EasyQueryOptionBuilder();
    private final ServiceCollection serviceCollection = new ServiceCollectionImpl();

    public EasyQueryBuilderConfiguration() {
        defaultConfiguration();
    }

    public String getName() {
        return name;
    }

    public EasyQueryBuilderConfiguration setName(String name) {
        this.name = name;
        return this;
    }

    private void defaultConfiguration() {
        replaceService(EasyQueryDataSource.class, DefaultEasyQueryDataSource.class)
                .replaceService(SQLKeyword.class, DefaultSQLKeyword.class)
                .replaceService(NameConversion.class, UnderlinedNameConversion.class)
                .replaceService(QueryConfiguration.class)
                .replaceService(EntityMetadataManager.class, DefaultEntityMetadataManager.class)
                .replaceService(SQLExpressionInvokeFactory.class, DefaultSQLExpressionInvokeFactory.class)
                .replaceService(ExpressionBuilderFactory.class, DefaultEasyExpressionBuilderFactory.class)
                .replaceService(SQLClientApiFactory.class, DefaultSQLClientApiFactory.class)
                .replaceService(TrackManager.class, DefaultTrackManager.class)
                .replaceService(EasyPageResultProvider.class, DefaultEasyPageResultProvider.class)
                .replaceService(EasyPrepareParser.class, DefaultEasyPrepareParser.class)
                .replaceService(ConnectionManager.class, DefaultConnectionManager.class)
                .replaceService(DataSourceRouteManager.class, DefaultDataSourceRouteManager.class)
                .replaceService(DataSourceRouter.class, ShardingDataSourceRouter.class)
                .replaceService(DataSourceRouteEngine.class, DefaultDataSourceRouteEngine.class)
                .replaceService(TableRouteManager.class, DefaultTableRouteManager.class)
                .replaceService(TableRouter.class, ShardingTableRouter.class)
                .replaceService(TableRouteEngine.class, DefaultTableRouteEngine.class)
                .replaceService(RouteContextFactory.class, DefaultRouteContextFactory.class)
                .replaceService(RewriteContextFactory.class, DefaultRewriteContextFactory.class)
                .replaceService(ExecutionContextFactory.class, DefaultExecutionContextFactory.class)
                .replaceService(EntityExpressionExecutor.class, DefaultEntityExpressionExecutor.class)
                .replaceService(EasyInitConfiguration.class, DefaultEasyInitConfiguration.class)
//                .replaceService(EntityExpressionExecutor.class, ShardingEntityExpressionExecutor.class)
                .replaceService(ShardingExecutorService.class, DefaultEasyShardingExecutorService.class)
                .replaceService(ExpressionFactory.class, DefaultEasyExpressionFactory.class)
                .replaceService(ShardingComparer.class, JavaLanguageShardingComparer.class)
                .replaceService(JdbcTypeHandlerManager.class, EasyJdbcTypeHandlerManager.class)
                .replaceService(QueryRuntimeContext.class, DefaultEasyQueryRuntimeContext.class)
                .replaceService(EasyDataSourceConnectionFactory.class, DefaultEasyDataSourceConnectionFactory.class)
                .replaceService(EasyConnectionFactory.class, DefaultEasyConnectionFactory.class)
                .replaceService(DataSourceManager.class, DefaultDataSourceManager.class)
                .replaceService(ShardingQueryCountManager.class, DefaultShardingQueryCountManager.class)
                .replaceService(RouteDescriptorFactory.class, DefaultRouteDescriptorFactor.class)
                .replaceService(DataSourceUnitFactory.class, DefaultDataSourceUnitFactory.class)
                .replaceService(SQLSegmentFactory.class, DefaultSQLSegmentFactory.class)
                .replaceService(EasyTimeJobManager.class, DefaultEasyTimeJobManager.class)
                .replaceService(IncludeProcessorFactory.class, EasyIncludeProcessorFactory.class)
                .replaceService(IncludeParserEngine.class, DefaultIncludeParserEngine.class)
                .replaceService(MapColumnNameChecker.class, DefaultMapColumnNameChecker.class)
                .replaceService(JdbcSQLExecutor.class, DefaultJdbcSQLExecutor.class)
                //whereObject的默认实现
                .replaceService(WhereObjectQueryExecutor.class, DefaultWhereObjectQueryExecutor.class)
                //orderByObject的默认实现
                .replaceService(ObjectSortQueryExecutor.class, DefaultObjectSortQueryExecutor.class)
                //jdbc执行的监听用于统计耗时sql
                .replaceService(JdbcExecutorListener.class, EmptyJdbcExecutorListener.class)
                //断言错误的异常工厂(firstNotNull singleNotNull findNotNull...)
                .replaceService(AssertExceptionFactory.class, DefaultAssertExceptionFactory.class)
                //sql参数打印格式化
                .replaceService(SQLParameterPrintFormat.class, DefaultSQLParameterPrintFormat.class)
                .replaceService(SQLFunc.class, SQLFuncImpl.class)
                .replaceService(JdbcSQLPrinter.class, DefaultJdbcSQLPrinter.class)
                .replaceService(Column2MapKeyConversion.class, DefaultColumn2MapKeyConversion.class)
                .replaceService(RelationValueFactory.class, DefaultRelationValueFactory.class)
                .replaceService(RelationValueColumnMetadataFactory.class, DefaultRelationValueColumnMetadataFactory.class)
                .replaceService(PropertyDescriptorMatcher.class, DefaultPropertyDescriptorMatcher.class)
                .replaceService(ValueFilterFactory.class, AnyValueFilterFactory.class)
                .replaceService(EntityMappingRule.class, PropertyFirstEntityMappingRule.class)
                .replaceService(MigrationsSQLGenerator.class, DefaultMigrationsSQLGenerator.class)
                .replaceService(DatabaseMigrationProvider.class, DefaultDatabaseMigrationProvider.class)
                .replaceService(MigrationEntityParser.class, DefaultMigrationEntityParser.class)
                .replaceService(CteTableNamedProvider.class, DefaultCteTableNamedProvider.class)
                .replaceService(MapKeyNameConversion.class, DefaultMapKeyNameConversion.class)
                .replaceService(DatabaseCodeFirst.class, DefaultDatabaseCodeFirst.class)
                .replaceService(IncludeProvider.class, DefaultIncludeProvider.class)
                .replaceService(RelationNullValueValidator.class, DefaultRelationNullValueValidator.class)
                .replaceService(SQLCaseWhenBuilderFactory.class, DefaultSQLCaseWhenBuilderFactory.class)
//                .replaceService(NavigateNamedGuess.class, DefaultNavigateNamedGuess.class)
                .replaceService(EasyQueryClient.class, DefaultEasyQueryClient.class)
                .replaceService(EasySearchMetaDataManager.class, DefaultEasySearchMetaDataManager.class)
                .replaceService(EasySearchQueryExecutor.class, DefaultEasySearchQueryExecutor.class)
                .replaceService(EasySearchConfigurationProvider.class, DefaultEasySearchConfigurationProvider.class);
    }

    public EasyQueryBuilderConfiguration setDefaultDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public EasyQueryBuilderConfiguration customConfigure(Consumer<ServiceCollection> configurer) {
        configurer.accept(serviceCollection);
        return this;
    }


    /**
     * 添加服务如果已经存在则替换
     *
     * @param implementType 依赖注入当前实例类型
     * @param <TImplement>  实现类型
     * @return 当前服务集合
     */
    public <TImplement> EasyQueryBuilderConfiguration replaceService(Class<TImplement> implementType) {
        serviceCollection.addService(implementType);
        return this;
    }

    /**
     * 添加服务如果已经存在则替换
     *
     * @param implementInstance 依赖注入当前实例
     * @param <TImplement>      实现类型
     * @return 当前服务集合
     */
    public <TImplement> EasyQueryBuilderConfiguration replaceService(TImplement implementInstance) {
        serviceCollection.addService(implementInstance);
        return this;
    }

    /**
     * 添加服务如果已经存在则替换
     *
     * @param serviceType   依赖注入的接口
     * @param implementType 依赖注入的实现类
     * @param <TService>    接口类型
     * @param <TImplement>  实现类型
     * @return 当前服务集合
     */
    public <TService, TImplement extends TService> EasyQueryBuilderConfiguration replaceService(Class<TService> serviceType, Class<TImplement> implementType) {
        serviceCollection.addService(serviceType, implementType);
        return this;
    }

    /**
     * 添加服务如果已经存在则替换
     *
     * @param serviceType       依赖注入的接口
     * @param implementInstance 依赖注入的实现
     * @param <TService>        接口类型
     * @param <TImplement>      实现类型
     * @return 当前服务集合
     */
    public <TService, TImplement extends TService> EasyQueryBuilderConfiguration replaceService(Class<TService> serviceType, TImplement implementInstance) {
        serviceCollection.addService(serviceType, implementInstance);
        return this;
    }

    /**
     * 添加服务如果已经存在则替换
     *
     * @param serviceType      依赖注入的接口
     * @param implementFactory 依赖注入的实现工厂
     * @param <TService>       接口类型
     * @param <TImplement>     实现类型
     * @return 当前服务集合
     */
    public <TService, TImplement extends TService> EasyQueryBuilderConfiguration replaceServiceFactory(Class<TService> serviceType, Function<ServiceProvider, TImplement> implementFactory) {
        serviceCollection.addServiceFactory(serviceType, implementFactory);
        return this;
    }

    public EasyQueryBuilderConfiguration useDatabaseConfigure(DatabaseConfiguration databaseConfiguration) {
        databaseConfiguration.configure(serviceCollection);
        return this;
    }

    public EasyQueryBuilderConfiguration useStarterConfigure(StarterConfigurer starterConfigurer) {
        starterConfigurer.configure(serviceCollection);
        return this;
    }

    public EasyQueryBuilderConfiguration optionConfigure(Consumer<EasyQueryOptionBuilder> configure) {
        configure.accept(this.easyQueryOptionBuilder);
        if (this.easyQueryOptionBuilder.isUseReplica()) {
            replaceService(DataSourceManager.class, DefaultReplicaDataSourceManager.class);
        } else {
            replaceService(DataSourceManager.class, DefaultDataSourceManager.class);
        }
        return this;
    }

    /**
     * 创建对应的查询器
     *
     * @return EasyQuery实例
     */
    public EasyQueryClient build() {
        if (this.dataSource == null) {
            throw new IllegalArgumentException("data source null");
        }
        replaceService(DataSource.class, this.dataSource);
        EasyQueryOption easyQueryOption = easyQueryOptionBuilder.build();
        replaceService(easyQueryOption);
        ServiceProvider serviceProvider = serviceCollection.build();
        EasyInitConfiguration service = serviceProvider.getService(EasyInitConfiguration.class);
        service.init();
        return serviceProvider.getService(EasyQueryClient.class);
    }

}
