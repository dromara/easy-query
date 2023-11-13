package com.easy.query.springshardingdemo.sharding;

import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.api.route.time.AbstractMonthTableRoute;
import com.easy.query.springshardingdemo.domain.OrderTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * create time 2023/11/13 22:10
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class OrderTestTableRoute extends AbstractMonthTableRoute<OrderTest> {
    private static final Log log = LogFactory.getLog(OrderTestTableRoute.class);
    public static final ConcurrentHashMap<String, Object> tails = new ConcurrentHashMap<>();
    private final Object def = new Object();
    private boolean inited = false;
    @Autowired
    private EasyQuery easyQuery;

    @Override
    protected LocalDateTime convertLocalDateTime(Object shardingValue) {
        return (LocalDateTime) shardingValue;
    }

    @Override
    protected String formatShardingValue(LocalDateTime time) {
        String shardingValue = super.formatShardingValue(time);
        if (!inited) {
            initTails();
        }
        checkShardingTail(shardingValue);
        return shardingValue;
    }

    private synchronized void initTails() {
        if (!inited) {
            initTails0();
            inited = true;
        }
    }

    private void initTails0() {
        EntityMetadata entityMetadata = easyQuery.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(entityClass());
        EasyQueryOption easyQueryOption = easyQuery.getRuntimeContext().getQueryConfiguration().getEasyQueryOption();
        List<Map<String, Object>> maps = easyQuery.sqlQueryMap("SHOW TABLES");
        List<String> tables = maps.stream().flatMap(o -> o.values().stream()).map(o -> o.toString())
                .filter(o -> o.startsWith("order_test")).collect(Collectors.toList());
        for (String table : tables) {
            entityMetadata.addActualTableWithDataSource(easyQueryOption.getDefaultDataSourceName(), table);
            tails.put(table.substring(8), def);
        }
    }

    private void checkShardingTail(String tail) {
        if (!tails.containsKey(tail)) {
            addShardingTail(tail);
        }
    }

    private synchronized void addShardingTail(String tail) {
        if (!tails.containsKey(tail)) {

            try {
                String sql = "create table order_test_" + tail + "\n" +
                        "(\n" +
                        "    id        varchar(128) not null\n" +
                        "        primary key,\n" +
                        "    name       varchar(128)          not null,\n" +
                        "    create_time   datetime not null\n" +
                        ");";
                easyQuery.sqlExecute(sql);
            } catch (Exception ex) {
                log.error("创建表失败:" + ex.getMessage(), ex);
            }

            EasyQueryOption easyQueryOption = easyQuery.getRuntimeContext().getQueryConfiguration().getEasyQueryOption();
            EntityMetadata entityMetadata = easyQuery.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(entityClass());
            entityMetadata.addActualTableWithDataSource(easyQueryOption.getDefaultDataSourceName(), entityMetadata.getTableName() + tableSeparator() + tail);
            tails.put(tail, def);
        }
    }
}
