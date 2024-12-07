package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.expression.visitor.TableVisitor;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/8/9 08:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class FormatValueParamExpressionImpl implements FormatValueParamExpression {
    private final Object formatVal;

    public FormatValueParamExpressionImpl(Object formatVal){

        this.formatVal = formatVal;
    }
    @Override
    public String toSQLSegment() {
        if(formatVal ==null){
            return EasyStringUtil.EMPTY;
        }
        return formatVal.toString();
    }

    @Override
    public void accept(TableVisitor visitor) {

    }
}
