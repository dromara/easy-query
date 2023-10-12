package com.easy.query.api.proxy.func;

import com.easy.query.api.proxy.func.concat.DefaultProxyColumnConcatSelector;
import com.easy.query.api.proxy.func.concat.ProxyColumnConcatSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.concat.ConcatExpression;
import com.easy.query.core.func.concat.DefaultColumnConcatSelector;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.util.EasyArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/10/12 17:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLProxyFunc {
    SQLFunc getSQLFunc();

    default <TProxy,T> SQLFunction ifNull(SQLColumn<TProxy, T> sqlColumn, Object def) {
        return getSQLFunc().ifNull(new SimpleSQLTableOwner(sqlColumn.getTable()), sqlColumn.value(), def);
    }

    /**
     * 获取绝对值
     *
     * @param sqlColumn
     * @return
     */
    default <TProxy,T> SQLFunction abs(SQLColumn<TProxy, T> sqlColumn) {
        return getSQLFunc().abs(new SimpleSQLTableOwner(sqlColumn.getTable()), sqlColumn.value());
    }

    /**
     * 获取绝对值
     *
     * @param sqlColumn
     * @return
     */
    default <TProxy,T> SQLFunction round(SQLColumn<TProxy, T> sqlColumn, int scale) {
        return getSQLFunc().round(new SimpleSQLTableOwner(sqlColumn.getTable()), sqlColumn.value(), scale);
    }

    default <TProxy,T> SQLFunction dateTimeJavaFormat(SQLColumn<TProxy, T> sqlColumn, String javaFormat) {
        return getSQLFunc().dateTimeJavaFormat(new SimpleSQLTableOwner(sqlColumn.getTable()), sqlColumn.value(), javaFormat);
    }

    default <TProxy,T> SQLFunction dateTimeSQLFormat(SQLColumn<TProxy, T> sqlColumn, String format) {
        return getSQLFunc().dateTimeSQLFormat(new SimpleSQLTableOwner(sqlColumn.getTable()), sqlColumn.value(), format);
    }

    default SQLFunction concat(SQLColumn<?, ?> sqlColumn1, SQLColumn<?, ?> sqlColumn2,SQLColumn<?, ?>... sqlColumns) {
        return concat(s -> {
            s.column(sqlColumn1)
                    .column(sqlColumn2);
            if(EasyArrayUtil.isNotEmpty(sqlColumns)){
                for (SQLColumn<?, ?> sqlColumn : sqlColumns) {
                    s.column(sqlColumn);
                }
            }
        });
    }

    default SQLFunction concat(SQLExpression1<ProxyColumnConcatSelector> sqlExpression) {
        List<ConcatExpression> concatExpressions = new ArrayList<>();
        sqlExpression.apply(new DefaultProxyColumnConcatSelector(new DefaultColumnConcatSelector(concatExpressions)));
        return concat(concatExpressions);
    }

    default SQLFunction concat(List<ConcatExpression> concatExpressions) {
        return getSQLFunc().concat(concatExpressions);
    }

    default SQLFunction join(String separator, SQLColumn<?, ?> sqlColumn1, SQLColumn<?, ?> sqlColumn2,SQLColumn<?, ?>... sqlColumns) {
        return join(separator, s -> {
            s.column(sqlColumn1)
                    .column(sqlColumn2);
            if(EasyArrayUtil.isNotEmpty(sqlColumns)){
                for (SQLColumn<?, ?> sqlColumn : sqlColumns) {
                    s.column(sqlColumn);
                }
            }
        });
    }

    default <T> SQLFunction join(String separator, SQLExpression1<ProxyColumnConcatSelector> sqlExpression) {
        List<ConcatExpression> concatExpressions = new ArrayList<>();
        sqlExpression.apply(new DefaultProxyColumnConcatSelector(new DefaultColumnConcatSelector(concatExpressions)));
        return join(separator, concatExpressions);
    }

    default SQLFunction join(String separator, List<ConcatExpression> concatExpressions){
        return getSQLFunc().join(separator,concatExpressions);
    }
}
