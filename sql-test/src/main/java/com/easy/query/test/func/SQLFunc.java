package com.easy.query.test.func;

import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.util.EasyLambdaUtil;

/**
 * create time 2023/5/21 21:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLFunc {
    public static <T,R> ColumnPropertyFunction ifNULL(Property<T,R> column){
        return new ColumnPropertyFunction() {
            @Override
            public ColumnFunction getColumnFunction() {
                return MySQLFunc.IFNULL_EMPTY;
            }

            @Override
            public String getPropertyName() {
                return EasyLambdaUtil.getPropertyName(column);
            }
        };
    }
    public static <T> ColumnFunction ifNULL(Object value){
        return new MySQLFunc(String.valueOf(value));
    }
    static class MySQLFunc implements ColumnFunction{
        public static final ColumnFunction IFNULL_EMPTY=new MySQLFunc("");
        private final String defaulValue;

        MySQLFunc(String defaulValue){

            this.defaulValue = defaulValue;
        }
        @Override
        public String getFuncColumn(String column) {
            return String.format("IFNULL(%s,'%s')",column,defaulValue);
        }

        @Override
        public AggregationType getAggregationType() {
            return AggregationType.UNKNOWN;
        }
    }
    public static String getColumnFunc1(String column){
        return "ifnull("+column+",'')";
    }
}
