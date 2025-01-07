package com.easy.query.core.util;

import com.easy.query.core.basic.jdbc.executor.internal.common.GroupByValue;
import com.easy.query.core.basic.jdbc.executor.internal.common.GroupByValueImpl;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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

    public static final String NOT_NULL = "query no element in result set.";
    public static final String FIND_NOT_NULL = "find not null query no element in result set.";
    public static final String FIRST_NOT_NULL = "first not null query no element in result set.";
    public static final String SINGLE_NOT_NULL = "single not null query no element in result set.";
    public static final String SINGLE_MORE_THAN = "";//single query at most one element in result set.

    private EasyUtil() {
    }

    public static <T, K> Stream<GroupByValue<K, T>> groupBy(Stream<T> stream, Function<T, K> keyExtractor) {
        Map<K, List<T>> map = stream.collect(Collectors.groupingBy(keyExtractor));
        return map.entrySet().stream().map(e -> new GroupByValueImpl<>(e.getKey(), e.getValue()));
    }

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

//    public static EntityTableExpressionBuilder getPreviewPredicateTable(EntityQueryExpressionBuilder sqlEntityExpression) {
//        return getPredicateTableByOffset(sqlEntityExpression, 1);
//    }

    public static int getNextTableIndex(EntityQueryExpressionBuilder sqlEntityExpression) {
        return sqlEntityExpression.getTables().size();
    }

    public static String getAnonymousPropertyNameByAlias(SQLEntityAliasSegment sqlEntityProject, TableAvailable anonymousTable) {
        String alias = sqlEntityProject.getAlias();
        if (EasyStringUtil.isBlank(alias)) {
            if (sqlEntityProject.getPropertyName() == null) {
                throw new EasyQueryInvalidOperationException("sqlEntityProject propertyName cannot be null");
            }
            alias = sqlEntityProject.getTable().getEntityMetadata().getColumnNotNull(sqlEntityProject.getPropertyName()).getName();
        }
        return anonymousTable.getEntityMetadata().getPropertyNameOrNull(alias, null);
    }

    public static String getAnonymousPropertyNameByProperty(SQLEntityAliasSegment sqlEntityProject, TableAvailable anonymousTable) {
        String propertyName = sqlEntityProject.getPropertyName();
        if (propertyName != null) {
            ColumnMetadata columnMetadata = anonymousTable.getEntityMetadata().getProperty2ColumnMap().get(propertyName);
            if (columnMetadata != null && !columnMetadata.isValueObject()) {
                propertyName = columnMetadata.getPropertyName();
            }
        }
        return propertyName;
    }

    public static String getAnonymousPropertyNameByPropertyFirst(SQLEntityAliasSegment sqlEntityProject, TableAvailable anonymousTable) {
        String propertyName = sqlEntityProject.getPropertyName();
        if (propertyName != null) {
            if(sqlEntityProject instanceof FuncColumnSegment){
                String alias = sqlEntityProject.getAlias();
                if (EasyStringUtil.isNotBlank(alias)) {
                    return anonymousTable.getEntityMetadata().getPropertyNameOrNull(alias, null);
                }
            }
            ColumnMetadata columnMetadata = anonymousTable.getEntityMetadata().getProperty2ColumnMap().get(propertyName);
            if (columnMetadata != null && !columnMetadata.isValueObject()) {
                propertyName = columnMetadata.getPropertyName();
            } else {
                String alias = sqlEntityProject.getAlias();
                if (EasyStringUtil.isNotBlank(alias)) {
                    return anonymousTable.getEntityMetadata().getPropertyNameOrNull(alias, null);
                }
            }
        }
        return propertyName;
    }


    public static LocalDateTime getDayStart(LocalDateTime dateTime) {
        return dateTime.toLocalDate().atStartOfDay();  // 设置时分秒为 0:00:00
    }

    public static LocalDateTime getMonthStart(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();  // 获取日期部分
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);  // 设置为月初第一天
        return firstDayOfMonth.atStartOfDay();  // 设置时分秒为 0:00:00
    }

    public static LocalDateTime getYearStart(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();  // 获取日期部分
        LocalDate firstDayOfYear = date.withDayOfYear(1);  // 设置为年初第一天
        return firstDayOfYear.atStartOfDay();  // 设置时分秒为 0:00:00
    }

    public static LocalDateTime getWeekStart(LocalDateTime dateTime) {
        LocalDateTime weekStart = dateTime.with(DayOfWeek.MONDAY)  // 设置为周一
                .toLocalDate()
                .atStartOfDay();  // 设置时分秒为 0:00:00
        return weekStart;
    }

    public static LocalDateTime getWeekEnd(LocalDateTime dateTime) {
        LocalDateTime weekEnd = dateTime.with(DayOfWeek.SUNDAY)  // 设置为周日
                .toLocalDate()
                .atStartOfDay();  // 设置时分秒为 0:00:00
        return weekEnd;
    }

    /**
     * 获取传入时间的当前所属季度的开始时间
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime getQuarterStart(LocalDateTime dateTime) {
        int quarter = (dateTime.getMonthValue() - 1) / 3 + 1;
        int year = dateTime.getYear();
        Month firstMonthOfQuarter = Month.of((quarter - 1) * 3 + 1);
        return LocalDate.of(year, firstMonthOfQuarter, 1).atStartOfDay();
    }

}

