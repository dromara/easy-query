package com.easy.query.core.util;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.basic.jdbc.executor.internal.common.Grouping;
import com.easy.query.core.basic.jdbc.executor.internal.common.GroupingImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xuejiaming
 * @FileName: EasyUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/4 13:12
 */
public class EasyUtil {


    private static final int FLAG_SERIALIZABLE = 1;

    private EasyUtil() {
    }

    public static <T, K> Stream<Grouping<K, T>> groupBy(Stream<T> stream, Function<T, K> keyExtractor) {
        Map<K, List<T>> map = stream.collect(Collectors.groupingBy(keyExtractor));
        return map.entrySet().stream().map(e -> new GroupingImpl<>(e.getKey(), e.getValue().stream()));
    }
    public static <T, K> Map<K, List<T>> groupByToMap(Stream<T> stream, Function<T, K> keyExtractor) {
        return stream.collect(Collectors.groupingBy(keyExtractor));
    }
//    public static <T, K,V> List<V> groupBy(List<T> list, Function<T, K> keyExtractor,Function<Grouping<K,T>,V> selector) {
//        Map<K, List<T>> map = list.stream().collect(Collectors.groupingBy(keyExtractor));
//        return map.entrySet().stream().map(e -> selector.apply(new Grouping<>(e.getKey(), e.getValue()))).collect(Collectors.toList());
//    }
    public static EntityTableExpressionBuilder getPredicateTableByOffset(EntityQueryExpressionBuilder sqlEntityExpression, int offsetForward) {
        List<EntityTableExpressionBuilder> tables = sqlEntityExpression.getTables();
        if (tables.isEmpty()) {
            throw new EasyQueryException("cant get current join table");
        }
        int i = getNextTableIndex(sqlEntityExpression) - 1 - offsetForward;
        return tables.get(i);
    }

    public static EntityTableExpressionBuilder getCurrentPredicateTable(EntityQueryExpressionBuilder sqlEntityExpression) {
        return getPredicateTableByOffset(sqlEntityExpression, 0);
    }

    public static EntityTableExpressionBuilder getPreviewPredicateTable(EntityQueryExpressionBuilder sqlEntityExpression) {
        return getPredicateTableByOffset(sqlEntityExpression, 1);
    }

    public static int getNextTableIndex(EntityQueryExpressionBuilder sqlEntityExpression) {
        return sqlEntityExpression.getTables().size();
    }

    public static String getAnonymousPropertyName(SQLEntityAliasSegment sqlEntityProject, TableAvailable anonymousTable) {
        String alias = sqlEntityProject.getAlias();
        if (EasyStringUtil.isBlank(alias)) {
            return sqlEntityProject.getPropertyName();
        }
        return anonymousTable.getEntityMetadata().getPropertyNameOrNull(alias,alias);
    }

}

