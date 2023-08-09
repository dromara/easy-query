package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/8/9 08:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class ConstValueParamExpressionImpl implements ConstValueParamExpression{
    private final Object constVal;

    public ConstValueParamExpressionImpl(Object constVal){

        this.constVal = constVal;
    }
    @Override
    public String toSQLSegment() {
        if(constVal==null){
            return EasyStringUtil.EMPTY;
        }
        return constVal.toString();
    }
}
