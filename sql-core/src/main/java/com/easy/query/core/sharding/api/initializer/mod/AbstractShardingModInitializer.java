package com.easy.query.core.sharding.api.initializer.mod;

import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.initializer.EntityShardingInitializer;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;
import com.easy.query.core.util.EasyStringUtil;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * create time 2023/5/19 22:22
 * 取模的分片初始化器
 * getMode 返回 3
 * getTailLength返回 2
 * getPaddingWord返回 0
 * getTableSeparator返回 _
 * 那么生成的表名为 t_abc_00,t_abc_01,t_abc_02
 *
 * @author xuejiaming
 */
public abstract class AbstractShardingModInitializer<TEntity> implements EntityShardingInitializer<TEntity> {
    protected abstract int mod();

    protected abstract int tailLength();

    protected char paddingWord() {
        return '0';
    }
    protected String tableSeparator() {
        return "_";
    }

    @Override
    public void configure(ShardingEntityBuilder<TEntity> builder) {
        EntityMetadata entityMetadata = builder.getEntityMetadata();
        String tableName = entityMetadata.getTableName();
        EasyQueryOption easyQueryOption = builder.getEasyQueryOption();
        List<String> actualTableNames = IntStream.range(0, mod()).mapToObj(o ->{
                    String tail = EasyStringUtil.leftPad(String.valueOf(o), tailLength(), paddingWord());
                    return tableName+tableSeparator()+tail;
                })
                .collect(Collectors.toList());
        Map<String, Collection<String>> initTables = new LinkedHashMap<String, Collection<String>>() {{
            put(easyQueryOption.getDefaultDataSourceName(), actualTableNames);
        }};
        builder.actualTableNameInit(initTables);
        configure0(builder);
    }

    public void configure0(ShardingEntityBuilder<TEntity> builder) {
    }
}
