package com.easy.query.core.api.dynamic.executor.search.match;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * 表匹配器
 *
 * @author bkbits
 */
public interface EasyTableMatch {
    static EasyTableMatch of(Class<?> tableClass) {
        return new EasyTableClassMatch(tableClass);
    }

    static EasyTableMatch of(String tableAlias) {
        return new EasyTableAliasMatch(tableAlias);
    }

    static EasyTableMatch of(int tableIndex) {
        return new EasyTableIndexMatch(tableIndex);
    }

    static EasyTableMatch of(Class<?> tableClass, String tableAlias, int tableIndex) {
        if (tableAlias != null && !(tableAlias = tableAlias.trim()).isEmpty()) { //别名优先级最高
            return of(tableAlias);
        }
        else if (tableClass != null && !tableClass.equals(Object.class)) { //表类优先级第二
            return of(tableClass);
        }
        else if (tableIndex >= 0) { //表索引优先级最低
            return of(tableIndex);
        }

        return null;
    }

    boolean match(TableAvailable table, int tableIndex);

    default TableAvailable getTable(EntityQueryExpressionBuilder builder) {
        int index = 0;
        for (EntityTableExpressionBuilder tableBuilder : builder.getTables()) {
            if (match(tableBuilder.getEntityTable(), index)) {
                return tableBuilder.getEntityTable();
            }
            ++index;
        }
        return null;
    }
}
