package com.easy.query.core.util;

import com.easy.query.core.annotation.ExpressionArg;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.extension.conversion.ExpArg;
import com.easy.query.core.basic.extension.conversion.ExpArgTypeEnum;
import com.easy.query.core.basic.jdbc.executor.internal.common.GroupByValue;
import com.easy.query.core.basic.jdbc.executor.internal.common.GroupByValueImpl;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.IncludeAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.IncludePathTreeNode;
import com.easy.query.core.metadata.PathTreeBuilder;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xuejiaming
 * @FileName: EasyUtil.java
 * @Description: 文件说明
 * create time 2023/3/4 13:12
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

        String alias = sqlEntityProject.getAlias();
        if (EasyStringUtil.isNotBlank(alias)) {
            return anonymousTable.getEntityMetadata().getPropertyNameOrNull(alias, null);
        }
        String propertyName = sqlEntityProject.getPropertyName();
        if (propertyName != null) {
            if (sqlEntityProject instanceof FuncColumnSegment) {
                if (EasyStringUtil.isNotBlank(alias)) {
                    return anonymousTable.getEntityMetadata().getPropertyNameOrNull(alias, null);
                }
            }
            ColumnMetadata columnMetadata = anonymousTable.getEntityMetadata().getProperty2ColumnMap().get(propertyName);
            if (columnMetadata != null && !columnMetadata.isValueObject()) {
                propertyName = columnMetadata.getPropertyName();
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

    public static List<ExpArg> getExpArgs(ExpressionArg[] expressionArgs) {
        List<ExpArg> expArgs = new ArrayList<>(expressionArgs.length);
        for (ExpressionArg arg : expressionArgs) {
            if (!Objects.equals(arg.ignoreVal(), arg.prop())) {
                ExpArg expArg = new ExpArg(ExpArgTypeEnum.PROPERTY, arg.prop(), null);
                expArgs.add(expArg);
            } else if (!Objects.equals(arg.ignoreVal(), arg.val())) {
                Object parseVal = parseVal(arg.val(), arg.valType());
                ExpArg expArg = new ExpArg(ExpArgTypeEnum.VALUE, null, parseVal);
                expArgs.add(expArg);
            }
        }
        return expArgs;
    }

    private static Object parseVal(String val, Class<?> valType) {
        if (valType == String.class) {
            return val;
        }
        if (valType == Integer.class || valType == int.class) {
            return Integer.valueOf(val);
        }
        if (valType == Long.class || valType == long.class) {
            return Long.valueOf(val);
        }
        if (valType == BigDecimal.class) {
            return new BigDecimal(val);
        }
        if (valType == Boolean.class) {
            if ("0".equals(val)) {
                return false;
            }
            if ("1".equals(val)) {
                return true;
            }
            return Boolean.valueOf(val);
        }
        if (valType == Byte.class) {
            return Byte.valueOf(val);
        }

        if (valType == Date.class) {
            return Date.valueOf(val);
        }
        if (valType == Double.class || valType == double.class) {
            return Double.valueOf(val);
        }
        if (valType == Float.class || valType == float.class) {
            return Float.valueOf(val);
        }
        if (valType == LocalDateTime.class) {
            return LocalDateTime.parse(val);
        }
        if (valType == LocalDate.class) {
            return LocalDate.parse(val);
        }
        if (valType == LocalTime.class) {
            return LocalTime.parse(val);
        }
        if (valType == Short.class || valType == short.class) {
            return Short.valueOf(val);
        }
        if (valType == UUID.class) {
            return UUID.fromString(val);
        }

        return val;
    }

    public static void includeMany(ClientQueryable<?> clientQueryable, IncludePathTreeNode treeNode) {

        for (IncludePathTreeNode child : treeNode.getChildren()) {
            clientQueryable.include(t -> {
                ClientQueryable<?> with = t.with(child.getName(), null);
                IncludePathTreeNode.IncludeFunction includeFunction = child.getIncludeFunction();
                if (includeFunction != null) {
                    for (Function<ClientQueryable<?>, ClientQueryable<?>> function : includeFunction.functions) {
                        function.apply(with);
                    }
                }
                includeMany(with, child);
                return with;
            });
        }
    }

    public static IncludePathTreeNode getIncludePathTreeRoot(IncludeAvailable includeAvailable) {

        List<IncludeAvailable> includes = includeAvailable.getIncludes();
        IncludePathTreeNode root = new IncludePathTreeNode("EASY-QUERY-INCLUDE-ROOT");
        for (IncludeAvailable include : includes) {
            String[] paths = include.getNavValue().split("\\.");
            PathTreeBuilder.insertPath(root, paths, new IncludePathTreeNode.IncludeFunction(include.getFunctions()));
        }
        return root;
    }
}

