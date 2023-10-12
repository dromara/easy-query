package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/10/12 13:27
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractSQLFunction implements SQLFunction {
    protected String alias;
    protected String propertyAlias;

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public void setPropertyAlias(String propertyAlias) {
        this.propertyAlias = propertyAlias;
    }

    @Override
    public void consume(SQLNativeChainExpressionContext context) {
        context.keepStyle();
        consume0(context);
        if(EasyStringUtil.isNotBlank(alias)){
            context.setAlias(alias);
        }else if(EasyStringUtil.isNotBlank(propertyAlias)){
            context.setPropertyAlias(propertyAlias);
        }
    }

    protected abstract void consume0(SQLNativeChainExpressionContext context);
}
