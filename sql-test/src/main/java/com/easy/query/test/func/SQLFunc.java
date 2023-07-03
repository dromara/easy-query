package com.easy.query.test.func;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;

/**
 * create time 2023/5/21 21:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLFunc {
//
//    public static <T1,TR> CaseWhenBuilder<T1,TR> caseWhenBuilder(SQLColumnAsSelector<T1, TR> sqlColumnAsSelector){
//        return new CaseWhenBuilder<>(sqlColumnAsSelector);
//    }
    public static <T, R> ColumnPropertyFunction ifNULL(Property<T, R> column) {
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        return new MyColumnPropertyFunction(propertyName, MySQLFunc.IFNULL_EMPTY);
    }

    public static <T, R> ColumnPropertyFunction ifNULLOrDefault(Property<T, R> column, R def) {
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        if (def instanceof String) {
            return new MyColumnPropertyFunction(propertyName, new MySQLFunc("'" + def + "'"));
        }
        return new MyColumnPropertyFunction(propertyName, new MySQLFunc(String.valueOf(def)));
    }

    static class MySQLFunc implements ColumnFunction {
        public static final ColumnFunction IFNULL_EMPTY = new MySQLFunc("''");
        private final String defaulValue;

        MySQLFunc(String defaulValue) {

            this.defaulValue = defaulValue;
        }

        @Override
        public String getFuncColumn(String column) {
            return String.format("IFNULL(%s,%s)", column, defaulValue);
        }

        @Override
        public AggregationType getAggregationType() {
            return AggregationType.UNKNOWN;
        }
    }

    public static String getColumnFunc1(String column) {
        return "ifnull(" + column + ",'')";
    }
}
