package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLUtil;

import java.util.Collection;

/**
 * create time 2023/10/13 08:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnCollectionMultiParamExpressionImpl implements ColumnMultiParamExpression{
    private final Collection<?> values;

    public ColumnCollectionMultiParamExpressionImpl(Collection<?> values){
        if(EasyCollectionUtil.isEmpty(values)){
            throw new IllegalArgumentException("ColumnMultiParamExpression values is empty");
        }
        this.values = values;
    }

    @Override
    public int getParamSize() {
        return values.size();
    }

    @Override
    public void addParams(ToSQLContext toSQLContext) {
        for (Object value : values) {
            EasySQLUtil.addParameter(toSQLContext,new EasyConstSQLParameter(null,null,value));
        }
    }
}
