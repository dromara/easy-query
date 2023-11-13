package com.easy.query.springshardingdemo.sharding;

import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.api.route.time.AbstractMonthTableRoute;
import com.easy.query.springshardingdemo.domain.OrderEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * create time 2023/6/26 22:10
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class OrderTableRoute extends AbstractMonthTableRoute<OrderEntity> {
    private final ConcurrentHashMap<String, Object> tails = new ConcurrentHashMap<>();
    private final Object def=new Object();
    private boolean inited = false;

//    @Autowired
    private EasyQuery easyQuery;

    @Override
    protected LocalDateTime convertLocalDateTime(Object shardingValue) {
        return (LocalDateTime) shardingValue;
    }

    @Override
    protected String formatShardingValue(LocalDateTime time) {
        String shardingValue = super.formatShardingValue(time);
//        if (!inited) {
//            initTails();
//        }
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
        List<Map<String, Object>> maps = easyQuery.sqlQueryMap("SHOW TABLES");
        List<String> tables = maps.stream().flatMap(o -> o.values().stream()).map(o -> o.toString())
                .filter(o->o.startsWith("t_order")).collect(Collectors.toList());
        for (String table : tables) {
            entityMetadata.addActualTableWithDataSource("ds0",table);
            tails.put(table.substring(8),def);
        }
    }

    private void shardingTail(String tail){
//        if(tails)
    }
}
