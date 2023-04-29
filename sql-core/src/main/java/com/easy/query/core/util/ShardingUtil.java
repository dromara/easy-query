package com.easy.query.core.util;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.segment.AggregationColumnSegment;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.OrderColumnSegment;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.merge.segment.EntityPropertyGroup;
import com.easy.query.core.sharding.merge.segment.EntityPropertyOrder;
import com.easy.query.core.sharding.merge.segment.PropertyGroup;
import com.easy.query.core.sharding.merge.segment.PropertyOrder;
import com.easy.query.core.sharding.route.RoutePredicateDiscover;
import com.easy.query.core.sharding.route.RoutePredicateExpression;
import com.easy.query.core.sharding.rule.RouteRuleFilter;

import java.util.List;
import java.util.Objects;
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
        if (x instanceof UUID && y instanceof UUID) {
            return compareUUID0((UUID) x, (UUID) y);
        }
        return x.compareTo(y);
    }

    private static int compareUUID0(UUID x, UUID y) {
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


    public static PropertyOrder findFirstPropertyOrderNotNull(List<SqlSegment> selectColumns, OrderColumnSegment orderColumnSegment, EasyQuerySqlExpression easyQuerySqlExpression) {
        int tableIndex = orderColumnSegment.getTable().getIndex();
        String propertyName = orderColumnSegment.getPropertyName();
        boolean asc = orderColumnSegment.isAsc();
        int selectIndex = -1;
        for (SqlSegment selectColumn : selectColumns) {
            selectIndex++;
            if (selectColumn instanceof ColumnSegmentImpl) {
                ColumnSegmentImpl selectColumnSegment = (ColumnSegmentImpl) selectColumn;
                String selectPropertyName = selectColumnSegment.getPropertyName();
                if (selectColumnSegment.getTable().getIndex() == tableIndex && Objects.equals(selectPropertyName, propertyName)) {
                    EasyTableSqlExpression table = easyQuerySqlExpression.getTable(tableIndex);
                    return new EntityPropertyOrder(table, propertyName, selectIndex, asc);
                }
            }
        }
        throw new EasyQueryInvalidOperationException("sharding query order: [" + propertyName + "] not found in select projects");
    }

    /**
     * group 如果不存在select中返回-1
     * @param selectColumns
     * @param columnSegment
     * @param easyQuerySqlExpression
     * @return
     */
    public static PropertyGroup findFirstPropertyGroupNotNull(List<SqlSegment> selectColumns, ColumnSegmentImpl columnSegment, EasyQuerySqlExpression easyQuerySqlExpression) {
        int tableIndex = columnSegment.getTable().getIndex();
        String propertyName = columnSegment.getPropertyName();
        int selectIndex = -1;
        for (SqlSegment selectColumn : selectColumns) {
            selectIndex++;
            if (!(selectColumn instanceof AggregationColumnSegment)) {
                ColumnSegmentImpl selectColumnSegment = (ColumnSegmentImpl) selectColumn;
                String selectPropertyName = selectColumnSegment.getPropertyName();
                if (selectColumnSegment.getTable().getIndex() == tableIndex && Objects.equals(selectPropertyName, propertyName)) {
                    EasyTableSqlExpression table = easyQuerySqlExpression.getTable(tableIndex);
                    return new EntityPropertyGroup(table, propertyName, selectIndex);
                }
            }
        }
        EasyTableSqlExpression table = easyQuerySqlExpression.getTable(tableIndex);
        return new EntityPropertyGroup(table, propertyName, -1);
    }


    public static  boolean isGroupByAndOrderByStartsWith(List<SqlSegment> groupBy, List<SqlSegment> orderBy){

        if(ArrayUtil.isEmpty(groupBy)||ArrayUtil.isNotEmpty(orderBy)){
            return false;
        }
        int minSize = Math.min(groupBy.size(),orderBy.size());
        for (int i = 0; i < minSize; i++) {
            SqlSegment groupSqlSegment = groupBy.get(i);
            if(!(groupSqlSegment instanceof ColumnSegment)){
                return false;
            }
            SqlSegment orderSqlSegment = orderBy.get(i);
            if(!(orderSqlSegment instanceof ColumnSegment)){
                return false;
            }
            ColumnSegment groupColumnSegment = (ColumnSegment) groupSqlSegment;
            ColumnSegment orderColumnSegment = (ColumnSegment) orderSqlSegment;
            if(groupColumnSegment.getTable().getIndex()!=orderColumnSegment.getTable().getIndex()){
                return false;
            }
            if(!Objects.equals(groupColumnSegment.getPropertyName(),orderColumnSegment.getPropertyName())){
                return false;
            }
        }
        return true;
    }
}
