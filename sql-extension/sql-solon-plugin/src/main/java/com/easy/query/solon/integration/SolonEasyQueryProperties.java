package com.easy.query.solon.integration;

import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.solon.integration.option.DatabaseEnum;
import com.easy.query.solon.integration.option.MapKeyConversionEnum;
import com.easy.query.solon.integration.option.NameConversionEnum;
import com.easy.query.solon.integration.option.SQLParameterPrintEnum;
import org.noear.solon.Utils;
import org.noear.solon.core.Props;

import java.util.concurrent.Executors;
import java.util.function.Function;

/**
 * create time 2023/7/25 08:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class SolonEasyQueryProperties {
//    private final static Boolean enable = false;
    private final static Boolean deleteThrow = true;
    private final static DatabaseEnum database = DatabaseEnum.MYSQL;
    private final static NameConversionEnum nameConversion = NameConversionEnum.UNDERLINED;
    private final static MapKeyConversionEnum mapKeyConversion = MapKeyConversionEnum.DEFAULT;
    private final static SQLExecuteStrategyEnum insertStrategy = SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS;
    private final static SQLExecuteStrategyEnum updateStrategy = SQLExecuteStrategyEnum.ALL_COLUMNS;
    private final static ConnectionModeEnum connectionMode = ConnectionModeEnum.SYSTEM_AUTO;
    /**
     * 仅分片时有效默认同时5个线程5
     */
    private final static int maxShardingQueryLimit = 5;
    /**
     * 仅分片时有效默认0如果需要建议大于 {@link com.easy.query.solon.integration.SolonEasyQueryProperties#maxShardingQueryLimit} * 分库数目
     * 执行线程数 如果为0那么采用无界线程池{@link Executors#newCachedThreadPool},如果是大于0采用长度为{@link com.easy.query.solon.integration.SolonEasyQueryProperties#executorQueueSize}的有界队列
     * 核心线程数采用{@link com.easy.query.solon.integration.SolonEasyQueryProperties#executorCorePoolSize}并且需要比 {@link com.easy.query.solon.integration.SolonEasyQueryProperties#executorCorePoolSize}值大
     */
    private final static int executorMaximumPoolSize = 0;
    /**
     * 当且仅当{@link com.easy.query.solon.integration.SolonEasyQueryProperties#executorMaximumPoolSize}>0生效
     */
    private final static int executorCorePoolSize = Math.min(Runtime.getRuntime().availableProcessors(), 4);
    /**
     * 当且仅当{@link com.easy.query.solon.integration.SolonEasyQueryProperties#executorMaximumPoolSize}>0生效
     */
    private final static int executorQueueSize = 1024;
//    private final static String logClass = "com.easy.query.solon.integration.logging.Slf4jImpl";
    /**
     * 当查询没有路由匹配的时候查询是否报错
     * true:表示报错
     * false:表示返回默认值
     */
    private final static boolean throwIfRouteNotMatch = true;

    /**
     * 分片聚合超时时间默认60秒单位(ms)
     */
    private final static long shardingExecuteTimeoutMillis = 60000L;

    private final static boolean queryLargeColumn = true;
    /**
     * 当出现条件分片大于多少时报错默认128,
     * 就是比如select where update where delete where路由到过多的表就会报错
     * entity操作比如update对象，insert，delete对象不会判断这个条件
     */
    private final static int maxShardingRouteCount = 128;
    /**
     * 默认数据源分库有效
     */
    private final static String defaultDataSourceName = "ds0";
    /**
     * 默认数据源的数据源连接池大小分表有效,一般设置为最少最少 >= maxShardingQueryLimit
     * 如果当前没有分表操作建议设置为0
     * 当小于maxShardingQueryLimit后启动会抛出警告
     */
    private final static int defaultDataSourceMergePoolSize = 0;
    /**
     * 默认5秒分表聚合多链接获取分表插入更新删除同理多个线程间等待获取时间单位毫秒(ms)
     */
    private final static long multiConnWaitTimeoutMillis = 5000L;
    /**
     * 分片获取连接数繁忙是否打印 获取耗时大于{@param multiConnWaitTimeoutMillis}的80%视为繁忙
     */
    private final static boolean warningBusy = true;


    /**
     * 对象插入数量到达多少后使用批处理
     */
    private final static int insertBatchThreshold = 1024;
    /**
     * 对象修改数量达到多少后使用批量处理
     */
    private final static int updateBatchThreshold = 1024;
    /**
     * 是否打印sql
     */
    private final static boolean printSql = true;
    /**
     * 分片按时间分表的时候需要开启
     */
    private final static boolean startTimeJob = false;
    /**
     * 默认是否启用追踪
     */
    private final static boolean defaultTrack = false;
    /**
     * 关联查询每组多少关联id
     */
    private final static int relationGroupSize = 512;
    private final static boolean keepNativeStyle = false;
    private final static boolean warningColumnMiss = true;
    private final static int shardingFetchSize = 1000;
    private final static boolean mapToBeanStrict = true;
    private final static String defaultSchema = null;
    private final static long reverseOffsetThreshold = 0;
    private final static long resultSizeLimit = -1;

    private final Props props;

    public SolonEasyQueryProperties(Props props) {

        this.props = props;
    }


//    public Boolean getEnable() {
//        return this.props.getBool("enable", enable);
//    }


//    public String getLogClass() {
//        return this.props.getProperty("log-class", logClass);
//    }


    public Boolean getDeleteThrow() {
        return this.props.getBool("delete-throw", deleteThrow);
    }


    public DatabaseEnum getDatabase() {
       return getOrDef("database",database,v->{
           String vl = v.toLowerCase();
           switch (vl){
                case "mysql":return DatabaseEnum.MYSQL;
                case "pgsql":return DatabaseEnum.PGSQL;
                case "mssql":return DatabaseEnum.MSSQL;
                case "h2":return DatabaseEnum.H2;
                case "dameng":return DatabaseEnum.DAMENG;
                case "kingbase_es":return DatabaseEnum.KINGBASE_ES;
                case "mssql_row_number":return DatabaseEnum.MSSQL_ROW_NUMBER;
                case "oracle":return DatabaseEnum.ORACLE;
                case "sqlite":return DatabaseEnum.SQLITE;
                case "clickhouse":return DatabaseEnum.CLICKHOUSE;
                case "gauss_db":return DatabaseEnum.GAUSS_DB;
                case "db2":return DatabaseEnum.DB2;
            }
            return null;
        });
    }
    public SQLParameterPrintEnum getSQLParameterPrint() {
       return getOrDef("sql-parameter-print",SQLParameterPrintEnum.DEFAULT,v->{
           String vl = v.toLowerCase();
           switch (vl){
                case "mybatis":return SQLParameterPrintEnum.MYBATIS;
            }
           return SQLParameterPrintEnum.DEFAULT;
        });
    }
    private <T> T getOrDef(String key, T def, Function<String, T> convert) {
        String temp = this.props.get(key);
        T t = Utils.isEmpty(temp) ? def : convert.apply(temp);
        if(t==null){
            return def;
        }
        return t;
    }



    public NameConversionEnum getNameConversion() {
        return getOrDef("name-conversion",nameConversion,v->{
            switch (v){
                case "default":return NameConversionEnum.DEFAULT;
                case "underlined":return NameConversionEnum.UNDERLINED;
                case "lower_camel_case":return NameConversionEnum.LOWER_CAMEL_CASE;
                case "upper_camel_case":return NameConversionEnum.UPPER_CAMEL_CASE;
                case "upper_underlined":return NameConversionEnum.UPPER_UNDERLINED;
            }
            return null;
        });
    }

    public MapKeyConversionEnum getMapKeyConversionEnum() {
        return getOrDef("map-key-conversion",mapKeyConversion,v->{
            switch (v){
                case "default":return MapKeyConversionEnum.DEFAULT;
                case "lower":return MapKeyConversionEnum.LOWER;
                case "upper":return MapKeyConversionEnum.UPPER;
                case "lower_underlined":return MapKeyConversionEnum.LOWER_UNDERLINED;
                case "upper_underlined":return MapKeyConversionEnum.UPPER_UNDERLINED;
            }
            return null;
        });
    }


    public SQLExecuteStrategyEnum getInsertStrategy() {
        return getOrDef("insert-strategy",insertStrategy,v->{
            switch (v){
                case "all_columns":return SQLExecuteStrategyEnum.ALL_COLUMNS;
                case "only_not_null_columns":return SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS;
                case "only_null_columns":return SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS;
            }
            return null;
        });
    }


    public SQLExecuteStrategyEnum getUpdateStrategy() {
        return getOrDef("update-strategy",updateStrategy,v->{
            switch (v){
                case "all_columns":return SQLExecuteStrategyEnum.ALL_COLUMNS;
                case "only_not_null_columns":return SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS;
                case "only_null_columns":return SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS;
            }
            return null;
        });
    }


    public ConnectionModeEnum getConnectionMode() {
        return getOrDef("connection-mode",connectionMode,v->{
            switch (v){
                case "system_auto":return ConnectionModeEnum.SYSTEM_AUTO;
                case "memory_strictly":return ConnectionModeEnum.MEMORY_STRICTLY;
                case "connection_strictly":return ConnectionModeEnum.CONNECTION_STRICTLY;
            }
            return null;
        });
    }

    public int getMaxShardingQueryLimit() {
        return this.props.getInt("max-sharding-query-limit",maxShardingQueryLimit);
    }

    public int getExecutorMaximumPoolSize() {
        return this.props.getInt("executor-maximum-pool-size",executorMaximumPoolSize);
    }


    public int getExecutorCorePoolSize() {
        return this.props.getInt("executor-core-pool-size",executorCorePoolSize);
    }


    public boolean isThrowIfRouteNotMatch() {
        return this.props.getBool("throw-if-route-not-match",throwIfRouteNotMatch);
    }

    public long getShardingExecuteTimeoutMillis() {
        return this.props.getLong("sharding-execute-timeout-millis",shardingExecuteTimeoutMillis);
    }

    public boolean isQueryLargeColumn() {
        return this.props.getBool("query-large-column",queryLargeColumn);
    }


    public int getMaxShardingRouteCount() {
        return this.props.getInt("max-sharding-route-count",maxShardingRouteCount);
    }

    public int getExecutorQueueSize() {
        return this.props.getInt("executor-queue-size",executorQueueSize);
    }


    public String getDefaultDataSourceName() {
        return this.props.getProperty("default-data-source-name",defaultDataSourceName);
    }

    public int getDefaultDataSourceMergePoolSize() {
        return this.props.getInt("default-data-source-merge-pool-size",defaultDataSourceMergePoolSize);
    }

    public long getMultiConnWaitTimeoutMillis() {
        return this.props.getLong("multi-conn-wait-timeout-millis",multiConnWaitTimeoutMillis);
    }

    public boolean isWarningBusy() {
        return this.props.getBool("warning-busy",warningBusy);
    }


    public int getInsertBatchThreshold() {
        return this.props.getInt("insert-batch-threshold",insertBatchThreshold);
    }


    public int getUpdateBatchThreshold() {
        return this.props.getInt("update-batch-threshold",updateBatchThreshold);
    }


    public boolean isPrintSql() {
        return this.props.getBool("print-sql",printSql);
    }


    public boolean isStartTimeJob() {
        return this.props.getBool("start-time-job",startTimeJob);
    }


    public boolean isDefaultTrack() {
        return this.props.getBool("default-track",defaultTrack);
    }


    public int getRelationGroupSize() {
        return this.props.getInt("relation-group-size",relationGroupSize);
    }
    public boolean isKeepNativeStyle() {
        return this.props.getBool("keep-native-style",keepNativeStyle);
    }
    public boolean isWarningColumnMiss() {
        return this.props.getBool("warning-column-miss",warningColumnMiss);
    }
    public int getShardingFetchSize() {
        return this.props.getInt("sharding-fetch-size",shardingFetchSize);
    }
    public boolean getMapToBeanStrict() {
        return this.props.getBool("map-to-bean-strict",mapToBeanStrict);
    }
    public String getDefaultSchema() {
        return this.props.getProperty("default-schema",defaultSchema);
    }
    public long getReverseOffsetThreshold() {
        return this.props.getLong("reverse-offset-threshold",reverseOffsetThreshold);
    }
    public long getResultSizeLimit() {
        return this.props.getLong("result-size-limit",resultSizeLimit);
    }


}
