package com.easy.query.test.func;

import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/7/22 22:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class IFNULLColumnFunction implements ColumnFunction {
    private final Object value;

    public IFNULLColumnFunction(Object value){
        if(value==null){
            throw new IllegalArgumentException("IFNULLColumnFunction value is null");
        }
        this.value = value;
    }
    @Override
    public String getFuncColumn(String column) {
        if(value instanceof  String){
            String valueString = value.toString();
            if(EasyStringUtil.isBlank(valueString)){
                return String.format("IFNULL(%s,'')", column);
            }
            return String.format("IFNULL(%s,'%s')", column, valueString);
        }
        return String.format("IFNULL(%s,%s)", column, value.toString());
    }

    @Override
    public AggregationType getAggregationType() {
        return AggregationType.UNKNOWN;
    }
}
