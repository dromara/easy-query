package com.easy.query.core.expression.parser.core.base.scec;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.SQLTableOwner;

/**
 * create time 2023/9/12 22:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAliasNativePropertyExpressionContext extends SQLNativePropertyExpressionChain<SQLAliasNativePropertyExpressionContext>{
    SQLAliasNativePropertyExpressionContext expressionAlias(String property);
    SQLAliasNativePropertyExpressionContext setPropertyAlias(String property);
}
