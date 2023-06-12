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
import java.util.Map;

/**
 * create time 2023/5/19 14:03
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractShardingTimeInitializer<T> implements EntityShardingInitializer<T>, TimeJob {
    public AbstractShardingTimeInitializer(){
        Type superClass = this.getClass().getGenericSuperclass();
        if (superClass instanceof Class) {
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        } else {
            this.entityClass = (Class<?>) ((ParameterizedType)superClass).getActualTypeArguments()[0];
        }
    }
    protected final Class<?> entityClass;
    protected abstract LocalDateTime getBeginTime();
    protected  LocalDateTime getEndTime(){
        return LocalDateTime.now();
    }
    protected abstract LocalDateTime getBeginTimeToStart(LocalDateTime beginTime);
    protected abstract LocalDateTime getNextTime(LocalDateTime currentTime);
    protected String getTableSeparator() {
        return "_";
    }
    protected abstract String formatTail(LocalDateTime time);
    @Override
    public void configure(ShardingEntityBuilder<T> builder) {
        EntityMetadata entityMetadata = builder.getEntityMetadata();
        EasyQueryOption easyQueryOption = builder.getEasyQueryOption();
        String tableName = entityMetadata.getTableName();
        LocalDateTime setBeginTime = getBeginTime();

        LocalDateTime beginTime = getBeginTimeToStart(setBeginTime);
        LocalDateTime endTime = getEndTime();
        if(beginTime.isAfter(endTime)){
            throw new IllegalArgumentException("begin time:"+beginTime+" is after end time:"+endTime);
        }
        String tableSeparator = getTableSeparator();

        ArrayList<String> actualTableNames = new ArrayList<>();
        //应该是不在endTime后可以等于endTime
        while(!beginTime.isAfter(endTime)){
            String tail = formatTail(beginTime);
            actualTableNames.add(tableName + tableSeparator + tail);
            beginTime = getNextTime(beginTime);
        }
        Map<String, Collection<String>> initTables = new LinkedHashMap<String, Collection<String>>() {{
            put(easyQueryOption.getDefaultDataSourceName(), actualTableNames);
        }};
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
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
        String tableName = entityMetadata.getTableName();
        LocalDateTime after5minutes = LocalDateTime.now().plusMinutes(5);
        String tail = formatTail(after5minutes);
        String actualTableName = tableName + getTableSeparator() + tail;
        for (String dataSource : entityMetadata.getDataSources()) {
            EasyDynamicUtil.addShardingEntity(entityMetadataManager,entityClass,dataSource,actualTableName);
        }
    }
}
