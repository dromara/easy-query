package com.easy.query.core.util;

import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLRawParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
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

import java.util.List;

/**
 * @author xuejiaming
 * @FileName: SQLUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/9 22:40
 */
public class EasySQLUtil {
    private EasySQLUtil() {
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
            } else if (sqlParameter instanceof PropertySQLParameter) {
                String propertyName = sqlParameter.getPropertyNameOrNull();
                String param = "[unknown](propertyName:" + propertyName + ")";
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
}
