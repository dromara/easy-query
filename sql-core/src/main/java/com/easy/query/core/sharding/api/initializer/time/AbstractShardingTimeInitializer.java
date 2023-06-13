package com.easy.query.core.sharding.api.initializer.time;

import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.job.TimeJob;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.initializer.EntityShardingInitializer;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyDynamicUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * create time 2023/5/19 14:03
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractShardingTimeInitializer<T> implements EntityShardingInitializer<T>, TimeJob {
    public AbstractShardingTimeInitializer() {
        Type superClass = this.getClass().getGenericSuperclass();
        if (superClass instanceof Class) {
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        } else {
            this.entityClass = (Class<?>) ((ParameterizedType) superClass).getActualTypeArguments()[0];
        }
    }

    protected final Class<?> entityClass;

    /**
     * 分片起始时间,用于自动计算分片目前实际表,比如当前2023年如果你设置2021那么如果您是按年分片那么系统可以自动知道你现在有table_2021,table_2022,table_2023
     *
     * @return
     */
    protected abstract LocalDateTime getBeginTime();

    /**
     * 结束时间不需要设置默认当前时间
     *
     * @return
     */
    protected LocalDateTime getEndTime() {
        return LocalDateTime.now();
    }

    /**
     * 开始时间的起始时间是多少,假设按天分表那么返回没有时分秒的时间,如果是按周那么就返回周一且没有时分秒,如果是按月就返回月初没有时分秒的时间
     *
     * @param beginTime
     * @return
     */
    protected abstract LocalDateTime getBeginTimeToStart(LocalDateTime beginTime);

    /**
     * 计算下次时间用于自动往系统内存中添加实际表名
     *
     * @param currentTime
     * @return
     */
    protected abstract LocalDateTime getNextTime(LocalDateTime currentTime);

    /**
     * 数据库表和尾巴的连接符,默认下划线可以为空
     *
     * @return
     */
    protected String getTableSeparator() {
        return "_";
    }

    /**
     * 如何将时间格式化为数据库表名尾巴
     *
     * @param time
     * @return
     */
    protected abstract String formatTail(LocalDateTime time);

    /**
     * 根据时间格式化数据源名称
     * @param time
     * @return
     */
    protected String formatDataSource(LocalDateTime time,String defaultDataSource) {
        return defaultDataSource;
    }

    @Override
    public void configure(ShardingEntityBuilder<T> builder) {
        EntityMetadata entityMetadata = builder.getEntityMetadata();
        EasyQueryOption easyQueryOption = builder.getEasyQueryOption();
        String defaultDataSourceName = easyQueryOption.getDefaultDataSourceName();
        String tableName = entityMetadata.getTableName();
        LocalDateTime setBeginTime = getBeginTime();

        LocalDateTime beginTime = getBeginTimeToStart(setBeginTime);
        LocalDateTime endTime = getEndTime();
        if (beginTime.isAfter(endTime)) {
            throw new IllegalArgumentException("begin time:" + beginTime + " is after end time:" + endTime);
        }
        String tableSeparator = getTableSeparator();

        LinkedHashMap<String, Collection<String>> initTables = new LinkedHashMap<>();
        //应该是不在endTime后可以等于endTime
        while (!beginTime.isAfter(endTime)) {
            String dataSource = formatDataSource(beginTime, defaultDataSourceName);
            String tail = formatTail(beginTime);
            Collection<String> actualTableNames = initTables.computeIfAbsent(dataSource, key -> new ArrayList<>());
            actualTableNames.add(tableName + tableSeparator + tail);
            beginTime = getNextTime(beginTime);
        }

        //初始化实际表
        builder.actualTableNameInit(initTables);
        configure0(builder);
    }

    /**
     * <blockquote><pre>
     *     {@code
     * builder.paginationReverse(0.5,100)
     *                 .ascSequenceConfigure(new TableNameStringComparator())
     *                 .addPropertyDefaultUseDesc(BhzShardingTransportEntity::getEventTime)
     *                 .defaultAffectedMethod(false, ExecuteMethodEnum.LIST,ExecuteMethodEnum.ANY,ExecuteMethodEnum.COUNT,ExecuteMethodEnum.FIRST)
     *                 .useMaxShardingQueryLimit(5,ExecuteMethodEnum.LIST,ExecuteMethodEnum.ANY,ExecuteMethodEnum.FIRST);
     *     }
     * </pre></blockquote>
     *
     * @param builder
     */
    public abstract void configure0(ShardingEntityBuilder<T> builder);

    @Override
    public String jobName() {
        return EasyClassUtil.getSimpleName(getClass());
    }

    @Override
    public void execute(ServiceProvider serviceProvider) {
        EntityMetadataManager entityMetadataManager = serviceProvider.getService(EntityMetadataManager.class);
        EasyQueryOption easyQueryOption = serviceProvider.getService(EasyQueryOption.class);
        String defaultDataSourceName = easyQueryOption.getDefaultDataSourceName();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
        String tableName = entityMetadata.getTableName();
        LocalDateTime after5minutes = LocalDateTime.now().plusMinutes(5);
        String dataSource = formatDataSource(after5minutes, defaultDataSourceName);
        String tail = formatTail(after5minutes);
        String actualTableName = tableName + getTableSeparator() + tail;
        EasyDynamicUtil.addShardingEntity(entityMetadataManager, entityClass, dataSource, actualTableName);
    }
}
