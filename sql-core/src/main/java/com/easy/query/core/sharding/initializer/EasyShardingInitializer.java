package com.easy.query.core.sharding.initializer;

/**
 * create time 2023/4/24 09:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyShardingInitializer {
    /**
     * 启动时用于返回所有的表名,可以通过持久化之类的形式进行初始化,
     * 后续可以在{@link com.easy.query.core.metadata.EntityMetadata#addActualTableWithDataSource(String, String)}方法里面进行动态添加
     * 实现动态添加表名
     * @param builder 初始化对象元信息
     * @return 返回所有的实际表名不需要携带datasource信息比如分片后数据库有order_1,order_2那么只需要返回[order_1,order_2]
     */
    void configure(ShardingEntityBuilder<?> builder);
}
