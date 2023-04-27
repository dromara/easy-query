package com.easy.query.core.util;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.route.RoutePredicateDiscover;
import com.easy.query.core.sharding.route.RoutePredicateExpression;
import com.easy.query.core.sharding.rule.RouteRuleFilter;

import java.util.UUID;

/**
 * create time 2023/4/19 08:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingUtil {

    public static RoutePredicateExpression getRoutePredicateExpression(PrepareParseResult prepareParseResult, EntityMetadata entityMetadata,
                                                                       RouteRuleFilter routeRuleFilter, boolean shardingTableRoute) {
        RoutePredicateDiscover routePredicateDiscover = new RoutePredicateDiscover(prepareParseResult, entityMetadata, routeRuleFilter, shardingTableRoute);
        return routePredicateDiscover.getRouteParseExpression();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static int safeCompare(final Comparable x, final Comparable y, final boolean asc) {
        if (asc) {
            return doSafeCompare(x, y);
        }
        return doSafeCompare(y, x);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static int doSafeCompare(final Comparable x, final Comparable y) {

        if (x == null && y == null) {
            return 0;
        }
        if (x == null) {
            return -1;
        }
        if (y == null) {
            return 1;
        }
        if(x instanceof UUID&&y instanceof UUID){
            return compareUUID0((UUID)x,(UUID)y);
        }
        return x.compareTo(y);
    }
    private static int compareUUID0(UUID x, UUID y){
        byte[] b1 = toByteArray(x);
        byte[] b2 = toByteArray(y);
        for (int i = 0; i < 16; i++) {
            int cmp = Byte.compare(b1[i], b2[i]);
            if (cmp != 0) {
                return cmp;
            }
        }
        return 0;
    }
    private static byte[] toByteArray(UUID uuid) {
        byte[] byteArray = new byte[16];
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        for (int i = 0; i < 8; i++) {
            byteArray[i] = (byte) (msb >>> 8 * (7 - i));
            byteArray[8 + i] = (byte) (lsb >>> 8 * (7 - i));
        }
        return byteArray;
    }
}
