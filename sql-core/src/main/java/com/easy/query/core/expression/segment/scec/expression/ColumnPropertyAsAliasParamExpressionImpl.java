package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.visitor.TableVisitor;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/9/12 19:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnPropertyAsAliasParamExpressionImpl implements ColumnPropertyAsAliasParamExpression{
    private final String alias;
    public ColumnPropertyAsAliasParamExpressionImpl(String alias) {
        this.alias=alias;
    }
    @Override
    public String toSQL(QueryRuntimeContext runtimeContext) {
        return EasySQLExpressionUtil.getQuoteName(runtimeContext, alias);
    }

    @Override
    public void accept(TableVisitor visitor) {

    }
}
