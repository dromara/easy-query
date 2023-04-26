package com.easy.query.core.bootstrapper;

import com.easy.query.core.abstraction.DefaultEasyQueryRuntimeContext;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.abstraction.EasySqlApiFactory;
import com.easy.query.core.api.def.DefaultEasySqlApiFactory;
import com.easy.query.core.basic.jdbc.con.DefaultConnectionManager;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.executor.DefaultEntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.types.DefaultJdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.basic.pagination.DefaultEasyPageResultProvider;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.basic.plugin.track.DefaultTrackManager;
import com.easy.query.core.basic.plugin.track.TrackManager;
import com.easy.query.core.basic.thread.DefaultEasyShardingExecutorService;
import com.easy.query.core.basic.thread.EasyShardingExecutorService;
import com.easy.query.core.config.IDialect;
import com.easy.query.core.config.NameConversion;
import com.easy.query.core.config.NullDialect;
import com.easy.query.core.config.UnderlinedNameConversion;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.enums.SqlExecuteStrategyEnum;
import com.easy.query.core.expression.executor.parser.DefaultEasyPrepareParser;
import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
import com.easy.query.core.expression.executor.query.DefaultExecutionContextFactory;
import com.easy.query.core.expression.executor.query.ExecutionContextFactory;
import com.easy.query.core.expression.parser.factory.DefaultEasyQueryLambdaFactory;
import com.easy.query.core.expression.parser.factory.EasyQueryLambdaFactory;
import com.easy.query.core.expression.sql.builder.factory.DefaultEasyExpressionBuilderFactory;
import com.easy.query.core.expression.sql.builder.factory.EasyExpressionBuilderFactory;
import com.easy.query.core.expression.sql.expression.factory.DefaultEasyExpressionFactory;
import com.easy.query.core.expression.sql.expression.factory.EasyExpressionFactory;
import com.easy.query.core.metadata.DefaultEntityMetadataManager;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.DefaultEasyDataSource;
import com.easy.query.core.sharding.EasyDataSource;
import com.easy.query.core.sharding.EasyShardingOption;
import com.easy.query.core.sharding.rewrite.DefaultRewriteContextFactory;
import com.easy.query.core.sharding.rewrite.RewriteContextFactory;
import com.easy.query.core.sharding.route.DefaultRouteContextFactory;
import com.easy.query.core.sharding.route.RouteContextFactory;
import com.easy.query.core.sharding.route.abstraction.DataSourceRouteManager;
import com.easy.query.core.sharding.route.abstraction.DefaultDataSourceRouteManager;
import com.easy.query.core.sharding.route.abstraction.DefaultTableRouteManager;
import com.easy.query.core.sharding.route.abstraction.TableRouteManager;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteEngine;
import com.easy.query.core.sharding.route.datasource.engine.DefaultDataSourceRouteEngine;
import com.easy.query.core.sharding.route.table.engine.DefaultTableRouteEngine;
import com.easy.query.core.sharding.route.table.engine.TableRouteEngine;

import javax.sql.DataSource;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * create time 2023/4/26 20:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQuerytBuilderConfiguration {
    protected  DataSource dataSource;
    protected EasyJdbcTypeHandlerManager jdbcTypeHandlerManager;
    protected EasyQueryOption easyQueryOption;
    protected EasyQueryConfiguration configuration;
    protected EntityMetadataManager entityMetadataManager;
    protected EasyQueryLambdaFactory easyQueryLambdaFactory;
    protected DefaultEasyExpressionBuilderFactory defaultEasyExpressionBuilderFactory;
    protected EasySqlApiFactory easyQueryableFactory;
    protected DefaultTrackManager defaultTrackManager;
    protected DefaultEasyPageResultProvider defaultEasyPageResultProvider;
    protected DefaultEasyPrepareParser prepareParser;
    protected DefaultEasyDataSource defaultEasyDataSource;
    protected EasyConnectionManager connectionManager;
    protected DefaultDataSourceRouteManager defaultDataSourceRouteManager;
    protected DefaultDataSourceRouteEngine defaultDataSourceRouteEngine;
    protected DefaultTableRouteManager defaultTableRouteManager;
    protected DefaultTableRouteEngine defaultTableRouteEngine;
    protected DefaultRouteContextFactory defaultRouteContextFactory;
    protected DefaultRewriteContextFactory defaultRewriteContextFactory;
    protected DefaultExecutionContextFactory defaultQueryCompilerContextFactory;
    protected DefaultEntityExpressionExecutor defaultEntityExpressionExecutor;
    protected EasyShardingOption easyShardingOption;
    protected DefaultEasyShardingExecutorService defaultEasyShardingExecutorService;
    protected DefaultEasyExpressionFactory defaultEasyExpressionFactory;
    protected NameConversion nameConversion;
    protected IDialect dialect;

    public EasyQuerytBuilderConfiguration() {

        defaultConfiguration();

    }

    private void defaultConfiguration() {

        this.nameConversion=new UnderlinedNameConversion();
        this.dialect=new NullDialect();
    }
    public EasyQuerytBuilderConfiguration setDataSource(DataSource dataSource){
        this.dataSource=dataSource;
        return this;
    }

    public EasyQuerytBuilderConfiguration setDialect(IDialect dialect) {
        this.dialect=dialect;
        return this;
    }

    public EasyQuerytBuilderConfiguration setNameConversion(NameConversion nameConversion) {
        this.nameConversion = nameConversion;
        return this;
    }

    protected   <TConfiguration> TConfiguration getDefaultIfNull(TConfiguration configuration, Supplier<TConfiguration> supplier){
        if(configuration==null){
            return supplier.get();
        }
        return configuration;
    }

    public EasyQueryRuntimeContext build() {
        if(this.defaultEasyDataSource==null&&this.dataSource==null){
            throw new IllegalArgumentException("easy data source and data source null");
        }

        EasyJdbcTypeHandlerManager jdbcTypeHandlerManager = getDefaultIfNull(this.jdbcTypeHandlerManager, DefaultJdbcTypeHandlerManager::new);
        EasyQueryOption easyQueryOption = getDefaultIfNull(this.easyQueryOption,()->new EasyQueryOption(false, SqlExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS, SqlExecuteStrategyEnum.ALL_COLUMNS));
        IDialect dialect=getDefaultIfNull(this.dialect, NullDialect::new);
        NameConversion nameConversion=getDefaultIfNull(this.nameConversion, UnderlinedNameConversion::new);
        EasyQueryConfiguration configuration = getDefaultIfNull(this.configuration,()->new EasyQueryConfiguration(easyQueryOption,dialect,nameConversion));

        EntityMetadataManager entityMetadataManager = getDefaultIfNull(this.entityMetadataManager,()->new DefaultEntityMetadataManager(configuration));
        EasyQueryLambdaFactory easyQueryLambdaFactory = getDefaultIfNull(this.easyQueryLambdaFactory, DefaultEasyQueryLambdaFactory::new);
        EasyExpressionBuilderFactory defaultEasyExpressionBuilderFactory = getDefaultIfNull(this.defaultEasyExpressionBuilderFactory,DefaultEasyExpressionBuilderFactory::new);
        EasySqlApiFactory easyQueryableFactory = getDefaultIfNull(this.easyQueryableFactory,()->new DefaultEasySqlApiFactory(defaultEasyExpressionBuilderFactory));
        TrackManager defaultTrackManager = getDefaultIfNull(this.defaultTrackManager,()->new DefaultTrackManager(entityMetadataManager));
        EasyPageResultProvider defaultEasyPageResultProvider = getDefaultIfNull(this.defaultEasyPageResultProvider,DefaultEasyPageResultProvider::new);

        EasyPrepareParser prepareParser = getDefaultIfNull(this.prepareParser,DefaultEasyPrepareParser::new);
        EasyDataSource defaultEasyDataSource = getDefaultIfNull(this.defaultEasyDataSource,()->new DefaultEasyDataSource("ds0", dataSource));
        EasyConnectionManager connectionManager = getDefaultIfNull(this.connectionManager,()->new DefaultConnectionManager(defaultEasyDataSource));
        DataSourceRouteManager defaultDataSourceRouteManager = getDefaultIfNull(this.defaultDataSourceRouteManager,()-> new DefaultDataSourceRouteManager(entityMetadataManager, defaultEasyDataSource));
        DataSourceRouteEngine defaultDataSourceRouteEngine = getDefaultIfNull(this.defaultDataSourceRouteEngine,()->new DefaultDataSourceRouteEngine(defaultEasyDataSource, entityMetadataManager, defaultDataSourceRouteManager));

        TableRouteManager defaultTableRouteManager = getDefaultIfNull(this.defaultTableRouteManager,()->new DefaultTableRouteManager(entityMetadataManager));
        TableRouteEngine defaultTableRouteEngine = getDefaultIfNull(this.defaultTableRouteEngine,()->new DefaultTableRouteEngine(entityMetadataManager, defaultTableRouteManager));

        RouteContextFactory defaultRouteContextFactory = getDefaultIfNull(this.defaultRouteContextFactory,()->new DefaultRouteContextFactory(defaultDataSourceRouteEngine, defaultTableRouteEngine));
        RewriteContextFactory defaultRewriteContextFactory = getDefaultIfNull(this.defaultRewriteContextFactory,DefaultRewriteContextFactory::new);
        ExecutionContextFactory defaultQueryCompilerContextFactory = getDefaultIfNull(this.defaultQueryCompilerContextFactory,()->new DefaultExecutionContextFactory(defaultRouteContextFactory, defaultRewriteContextFactory, defaultEasyDataSource));
        EntityExpressionExecutor defaultEntityExpressionExecutor = getDefaultIfNull(this.defaultEntityExpressionExecutor,()->new DefaultEntityExpressionExecutor(prepareParser, defaultQueryCompilerContextFactory));

        EasyShardingOption easyShardingOption = getDefaultIfNull(this.easyShardingOption,()->new EasyShardingOption(10, 20));
        EasyShardingExecutorService defaultEasyShardingExecutorService = getDefaultIfNull(this.defaultEasyShardingExecutorService,()->new DefaultEasyShardingExecutorService(easyShardingOption));
        EasyExpressionFactory defaultEasyExpressionFactory = getDefaultIfNull(this.defaultEasyExpressionFactory,DefaultEasyExpressionFactory::new);
        return new DefaultEasyQueryRuntimeContext(configuration, entityMetadataManager, easyQueryLambdaFactory, connectionManager, defaultEntityExpressionExecutor, jdbcTypeHandlerManager, easyQueryableFactory, defaultEasyExpressionBuilderFactory, defaultTrackManager, defaultEasyPageResultProvider, easyShardingOption, defaultEasyShardingExecutorService, defaultEasyExpressionFactory,defaultTableRouteManager,defaultDataSourceRouteManager);
    }

}
