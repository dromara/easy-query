package com.easy.query.core.util;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.jdbc.parameter.BeanSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLRawParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.ToSQLResult;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.NavigateMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xuejiaming
 * @FileName: SQLUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/9 22:40
 */
public class EasySQLUtil {
    private EasySQLUtil() {
    }

    public static String toQueryableKey(ClientQueryable<?> clientQueryable) {
        ToSQLResult sqlResult = clientQueryable.toSQLResult();
        String sql = sqlResult.getSQL();
        //后续SQLParameter改成实现hashCode和equals
        String parameterString = EasySQLUtil.sqlParameterToString(sqlResult.getSqlContext().getParameters());
        return String.format("%s:%s", sql, parameterString);
    }


    public static String getLikeParameter(Object val, SQLLikeEnum sqlLike) {
//        if(val instanceof SQLRawParameter){
//            SQLRawParameter sqlRawParameter = (SQLRawParameter) val;
//            sqlRawParameter.setSqlLike(sqlLike);
//            return sqlRawParameter;
//        }
        switch (sqlLike) {
            case LIKE_PERCENT_RIGHT:
                return val + "%";
            case LIKE_PERCENT_LEFT:
                return "%" + val;
            default:
                return "%" + val + "%";
        }
    }

    public static Object getLikeRawParameter(Object val, SQLLikeEnum sqlLike) {
        return new SQLRawParameter(val, sqlLike);
    }

    public static String sqlParameterToString(List<SQLParameter> sqlParameters) {
        if (sqlParameters == null) {
            return EasyStringUtil.EMPTY;
        }
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (SQLParameter sqlParameter : sqlParameters) {
            if (sqlParameter instanceof ConstSQLParameter) {
                Object param = sqlParameter.getValue();
                if (i++ != 0) {
                    builder.append(",");
                }
                builder.append(param);
                builder.append("(");
                builder.append(param == null ? "null" : EasyClassUtil.getInstanceSimpleName(param));
                builder.append(")");
            } else if (sqlParameter instanceof BeanSQLParameter) {
                String propertyName = sqlParameter.getPropertyNameOrNull();
                BeanSQLParameter beanSQLParameter = (BeanSQLParameter) sqlParameter;
                boolean hasBean = beanSQLParameter.hasBean();
                Object value = hasBean ? beanSQLParameter.getValue() : "unknown";
                String param = "[" + (value) + "](propertyName:" + propertyName + ")";
                if (i++ != 0) {
                    builder.append(",");
                }
                builder.append(param);
            }
        }
        return builder.toString();
    }

    public static String sqlParameterToMyBatisString(List<SQLParameter> sqlParameters) {
        if (sqlParameters == null) {
            return EasyStringUtil.EMPTY;
        }
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (SQLParameter sqlParameter : sqlParameters) {
            if (sqlParameter instanceof ConstSQLParameter) {
                Object param = sqlParameter.getValue();
                if (i++ != 0) {
                    builder.append(", ");
                }
                builder.append(param);
                builder.append("(");
                builder.append(param == null ? "null" : EasyClassUtil.getInstanceSimpleName(param));
                builder.append(")");
            } else if (sqlParameter instanceof PropertySQLParameter) {
                String propertyName = sqlParameter.getPropertyNameOrNull();
                String param = "[unknown](propertyName:" + propertyName + ")";
                if (i++ != 0) {
                    builder.append(", ");
                }
                builder.append(param);
            }
        }
        return builder.toString();
    }


    public static void addParameter(ToSQLContext toSQLContext, SQLParameter sqlParameter) {
        if (toSQLContext != null) {
            toSQLContext.addParameter(sqlParameter);
        }
    }


    /**
     * <blockquote><pre>
     * {@code
     * List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
     *                 .orderBy(bank_card -> {
     *                     EasySQLUtil.dynamicOrderBy(bank_card.getEntitySQLContext().getOrderSelector(), bank_card.getTable(), "user.age", true, OrderByModeEnum.NULLS_LAST, true);
     *                 }).toList();
     * }
     * </pre></blockquote>
     *
     * @param orderSelector 排序选择器
     * @param table         主表
     * @param multiProperty 支持user.address.age相对于主表
     * @param asc           是否正序
     * @param nullsModeEnum null怎么排序
     * @param strictMode    是否严格模式,严格模式下属性不在表中将会报错
     * @throws EasyQueryOrderByInvalidOperationException strictMode为true,column不在表中
     * @throws EasyQueryInvalidOperationException        strictMode为true,navigate不在表中
     */
    public static void dynamicOrderBy(OrderSelector orderSelector, TableAvailable table, String multiProperty, boolean asc, OrderByModeEnum nullsModeEnum, boolean strictMode) {
        EasyRelationalUtil.TableOrRelationTable tableOrRelationalTable = EasyRelationalUtil.getTableOrRelationalTable(orderSelector.getEntityQueryExpressionBuilder(), table, multiProperty, strictMode);
        if (tableOrRelationalTable.table != null) {
            dynamicColumn0(orderSelector, tableOrRelationalTable.table, tableOrRelationalTable.property, asc, nullsModeEnum);
        }
    }

    private static void dynamicColumn0(OrderSelector orderSelector, TableAvailable entityTable, String property, boolean asc, OrderByModeEnum nullsModeEnum) {

        orderSelector.setAsc(asc);
        if (nullsModeEnum != null) {
            SQLFunc fx = orderSelector.getRuntimeContext().fx();
            SQLFunction orderByNullsModeFunction = fx.orderByNullsMode(entityTable, property, asc, nullsModeEnum);
            orderSelector.func(entityTable, orderByNullsModeFunction, false);
        } else {
            orderSelector.column(entityTable, property);
        }
    }


    private static ColumnMetadata checkColumn(TableAvailable entityTable, String property, boolean strictMode) {
        ColumnMetadata columnMetadata = entityTable.getEntityMetadata().getColumnOrNull(property);
        if (columnMetadata == null) {
            if (strictMode) {
                throw new EasyQueryOrderByInvalidOperationException(property, EasyClassUtil.getSimpleName(entityTable.getEntityClass()) + " not found [" + property + "] in entity class");
            }
        }
        return columnMetadata;
    }


    private static final Pattern stringFormatPattern = Pattern.compile("\\{(\\d+)}|([^{]+)");

    public static List<Object> parseFormat(String format, Object... args) {
        List<Object> result = new ArrayList<>();
        Matcher matcher = stringFormatPattern.matcher(format);

//        int maxIndex = 0;
        while (matcher.find()) {
            String placeholder = matcher.group(1);
            String text = matcher.group(2);
            if (text != null) {
                result.add(text);
            } else if (placeholder != null) {
                int currentIndex = Integer.parseInt(placeholder);
                if (currentIndex >= args.length) {
                    throw new EasyQueryInvalidOperationException(String.format("Mismatch: provided %d arguments, but the format string expects a different number.", args.length));
                }
                result.add(args[currentIndex]);
//                maxIndex = Math.max(maxIndex, currentIndex);
            }
        }
//        if (args.length != (maxIndex + 1)) {
//            throw new EasyQueryInvalidOperationException(String.format("Invalid concat format: expected %d arguments, but got %d.", maxIndex + 1, args.length));
//        }
        return result;
    }
//    public static List<Object> parseFormat(String formatString, Object... params) {
//        List<Object> result = new ArrayList<>();
//        List<String> staticParts = new ArrayList<>();
//        int start = 0;
//        int paramCount = 0;
//
//        while (true) {
//            int index = formatString.indexOf("%s", start);
//            if (index == -1) {
//                // 添加最后一个静态部分
//                staticParts.add(formatString.substring(start));
//                break;
//            }
//            // 添加当前静态部分
//            staticParts.add(formatString.substring(start, index));
//            start = index + 2; // 跳过%s
//            paramCount++;
//        }
//
//        // 验证参数数量是否匹配
//        if (params.length != paramCount) {
//            throw new IllegalArgumentException("参数数量与占位符数量不匹配");
//        }
//
//        // 交替合并静态部分和参数
//        for (int i = 0; i < staticParts.size(); i++) {
//            String part = staticParts.get(i);
//            result.add(part);
//            if (i < params.length) {
//                result.add(params[i]);
//            }
//        }
//
//        return result;
//    }
}
